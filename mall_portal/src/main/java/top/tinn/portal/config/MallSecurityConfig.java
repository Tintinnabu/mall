package top.tinn.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.tinn.config.SecurityConfig;
import top.tinn.model.UmsMember;
import top.tinn.portal.service.UmsMemberService;

/**
 * @Description MallSecurityConfig
 * @Author Tinn
 * @Date 2020/4/10 15:23
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    @Autowired
    private UmsMemberService memberService;

    @Bean
    public UserDetailsService userDetailsService(){
        return username->memberService.loadUserByUsername(username);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
