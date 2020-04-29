package top.tinn.service.UmsService.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.tinn.utils.JwtTokenUtil;
import top.tinn.dao.UmsAdminPermissionRelationDao;
import top.tinn.dao.UmsAdminRoleRelationDao;
import top.tinn.bo.AdminUserDetails;
import top.tinn.dto.UmsAdminParam;
import top.tinn.dto.UpdateAdminPasswordParam;
import top.tinn.mapper.UmsAdminLoginLogMapper;
import top.tinn.mapper.UmsAdminMapper;
import top.tinn.mapper.UmsAdminPermissionRelationMapper;
import top.tinn.mapper.UmsAdminRoleRelationMapper;
import top.tinn.model.*;
import top.tinn.service.UmsService.UmsAdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminPermissionRelationMapper adminPermissionRelationMapper;
    @Autowired
    private UmsAdminPermissionRelationDao adminPermissionRelationDao;
    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;



    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example=new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList=adminMapper.selectByExample(example);
        if (adminList!=null && adminList.size()>0)
            return adminList.get(0);
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        //查询是否有相同用户名的用户,重名返回null
        UmsAdminExample example=new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdminParam.getUsername());
        List<UmsAdmin> umsAdminList=adminMapper.selectByExample(example);
        if (umsAdminList.size()>0) return null;

        UmsAdmin umsAdmin=new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //将密码进行加密操作
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }



    /**
     * 登录验证 返回token
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token=null;
        try {
            UserDetails userDetails= loadUserByUsername(username);
            if (!passwordEncoder.matches(password,userDetails.getPassword()))throw new BadCredentialsException("密码不正确");
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token=jwtTokenUtil.generateToken(userDetails);
            //登录成功后 更新LoginTime
            UmsAdmin admin=getAdminByUsername(username);
            if (admin.getStatus().equals(0)) {
                LOGGER.info("账号已被禁用!");
                return "LOCKED";
            }
            admin.setLoginTime(new Date());
            adminMapper.updateByPrimaryKey(admin);
            insertLoginLog(username);
        }catch (AuthenticationException e){
            LOGGER.warn("登陆异常:{}",e.getMessage());
        }
        return token;
    }

    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }


    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshToken(oldToken);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsAdminExample example=new UmsAdminExample();
        UmsAdminExample.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(keyword)){
            criteria.andUsernameLike("%"+keyword+"%");
            example.or(example.createCriteria().andNickNameLike("%"+keyword+"%"));
        }
        return adminMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsAdmin admin) {
        admin.setId(id);
        if (admin.getPassword().length()>15) return adminMapper.updateByPrimaryKey(admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStatus(Long id, UmsAdmin admin) {
        admin.setId(id);
        //admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminMapper.updateByPrimaryKey(umsAdmin);
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsAdmin admin=getAdminByUsername(username);
        if (admin!=null){
            List<UmsResource> resourceList=getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public int delete(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        //
        UmsAdmin admin=adminMapper.selectByPrimaryKey(adminId);
        if (admin.getUsername().equals("商品管理员")||admin.getUsername().equals("订单管理员")) return 0;
        else if (admin.getUsername().equals("测试角色")) roleIds.stream().filter(roleId->roleId<5).collect(Collectors.toList());
        int count= roleIds==null? 0:roleIds.size();
        //先删除原来的关系
        UmsAdminRoleRelationExample adminRoleRelationExample=new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)){
            List<UmsAdminRoleRelation> list=new ArrayList<>();
            for (Long roleId:roleIds){
                UmsAdminRoleRelation roleRelation=new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationDao.insertList(list);
        }
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //
        return adminRoleRelationDao.getResourceList(adminId);
    }

    @Override
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        //删除原所有权限关系
        UmsAdminPermissionRelationExample relationExample=new UmsAdminPermissionRelationExample();
        relationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminPermissionRelationMapper.deleteByExample(relationExample);
        //获取用户所有角色权限
        List<UmsPermission> permissionList=adminRoleRelationDao.getRolePermissionList(adminId);
        List<Long> rolePermissionList=permissionList.stream().map(UmsPermission::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(permissionIds)){
            List<UmsAdminPermissionRelation> relationList=new ArrayList<>();
            //筛选出+权限
            List<Long> addPermissionIdList=permissionIds.stream().filter(permissionId->!rolePermissionList
                    .contains(permissionId)).collect(Collectors.toList());
            //筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream().filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            //插入+-权限关系
            relationList.addAll(convert(adminId,1,addPermissionIdList));
            relationList.addAll(convert(adminId,-1,subPermissionIdList));
            return adminPermissionRelationDao.insertList(relationList);
        }
        return 0;
    }

    private Collection<? extends UmsAdminPermissionRelation> convert(Long adminId, Integer type, List<Long> permissionIdList) {
        List<UmsAdminPermissionRelation> relationList = permissionIdList.stream().map(permissionId -> {
            UmsAdminPermissionRelation relation = new UmsAdminPermissionRelation();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }


}
