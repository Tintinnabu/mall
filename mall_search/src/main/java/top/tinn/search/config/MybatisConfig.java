package top.tinn.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description MybatisConfig
 * @Author Tinn
 * @Date 2020/4/10 10:37
 */

@MapperScan({"top.tinn.mapper","top.tinn.search.dao"})
@Configuration
public class MybatisConfig {
}
