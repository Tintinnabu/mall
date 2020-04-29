package top.tinn.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description MyBatisConfig
 * @Author Tinn
 * @Date 2020/4/10 15:28
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"top.tinn.mapper","top.tinn.portal.dao"})
public class MyBatisConfig {
}
