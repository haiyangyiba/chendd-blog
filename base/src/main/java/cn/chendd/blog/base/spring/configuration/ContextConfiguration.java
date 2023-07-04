package cn.chendd.blog.base.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 系统配置文件配置导入
 *
 * @author chendd
 * @date 2020/1/7 22:22
 */
@Configuration
@ImportResource(locations= {"classpath:config/context/spring-*.xml"})
public class ContextConfiguration {


}
