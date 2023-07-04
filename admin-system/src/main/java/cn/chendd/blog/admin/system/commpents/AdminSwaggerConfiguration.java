package cn.chendd.blog.admin.system.commpents;

import cn.chendd.blog.base.swagger.BaseSwagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * admin模块的swagger配置
 *
 * @author chendd
 * @date 2021/4/29 15:21
 */
@Configuration
public class AdminSwaggerConfiguration implements BaseSwagger {

    @Bean
    public Docket adminSwaggerInstance() {
        return this.newInstance("系统管理");
    }

}
