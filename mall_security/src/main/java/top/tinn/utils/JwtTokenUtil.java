package top.tinn.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT token的格式：header.payload.signature
 * header中用于存放签名的生成算法
 * {"alg": "HS512"}
 * Copy to clipboardErrorCopied
 * payload中用于存放用户名、token的生成时间和过期时间
 * {"sub":"admin","created":1489079981393,"exp":1489684781}
 * Copy to clipboardErrorCopied
 * signature为以header和payload生成的签名，一旦header和payload被篡改，验证将失败
 * //secret为加密算法的密钥
 * String signature = HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * generateToken(UserDetails userDetails) :用于根据登录用户信息生成token
 * getUserNameFromToken(String token)：从token中获取登录用户的信息
 * validateToken(String token, UserDetails userDetails)：判断token是否还有效
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try {
            claims=Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.info("JWT验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims=getClaimsFromToken(token);
            username=claims.getSubject();
        }catch (Exception e){
            username=null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username=getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate=getExpireDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpireDate(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshToken(String oldToken) {
        if (StringUtils.isEmpty(oldToken)) return null;
        String token=oldToken.substring(tokenHead.length());
        if (StringUtils.isEmpty(token)) return null;
        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if (claims==null) return null;
        //如果token已经过期，不支持刷新
        if (isTokenExpired(token)) return null;
        if (tokenRefreshJustBefore(token,30*60)) return token;
        else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }

    }

    /**
     *  判断token在指定时间内是否刚刚刷新过
     * @param token
     * @param time (seconds)
     * @return
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims=getClaimsFromToken(token);
        Date created=claims.get(CLAIM_KEY_CREATED,Date.class);
        Date refreshDate=new Date();
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created,time))) return true;
        return false;
    }

}
