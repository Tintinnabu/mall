package top.tinn.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.tinn.componet.DynamicSecurityService;

import top.tinn.model.UmsResource;
import top.tinn.service.UmsService.UmsAdminService;
import top.tinn.service.UmsService.UmsResourceService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MallSecurityConfig.class);
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    //加载用户的后台资源规则
    @Bean
    public UserDetailsService userDetailsService(){
        return username-> adminService.loadUserByUsername(username);
    }


    //动态加载所有的后台资源规则
    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute>map=new ConcurrentHashMap<>();
                //全部资源列表
                List<UmsResource> resourceList=resourceService.listAll();
                for (UmsResource resource:resourceList){
                    map.put(resource.getUrl(),new org.springframework.security.access.SecurityConfig(resource.getId()+":"+resource.getName()));
                }
                LOGGER.info("load all {} resources",resourceList.size());
                return map;
            }
        };
    }
}
