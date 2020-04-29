package top.tinn.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import top.tinn.utils.JwtTokenUtil;

@SpringBootApplication
public class MallPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPortalApplication.class, args);
    }

    @Bean
    public JwtTokenUtil getJwtTokenUtil(){
        return new JwtTokenUtil();
    }
}
