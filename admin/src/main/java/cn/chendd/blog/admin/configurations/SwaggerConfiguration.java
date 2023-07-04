package cn.chendd.blog.admin.configurations;

import cn.chendd.blog.admin.api.ApiRestResutController;
import cn.chendd.third.login.controller.LoginSinaController;
import cn.chendd.toolkit.dbproperty.controller.SysDbValueController;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 * @author chendd
 * @date 2019/9/16 13:16
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration {

    /**
     * 初始化框架搭建功能
     *
     * @return springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createTestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName("功能测试")
                .select()
                .apis(RequestHandlerSelectors.basePackage(ApiRestResutController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
        return docket;
    }

    /**
     * 数据库参数解析功能
     *
     * @return springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createDbValueApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName("数据库参数解析功能")
                .select()
                .apis(RequestHandlerSelectors.basePackage(SysDbValueController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
        return docket;
    }
//
//    /**
//     * 系统管理
//     *
//     * @return springfox.documentation.spring.web.plugins.Docket
//     */
//    @Bean
//    public Docket createSystemManageApi() {
//        String packageName = SysRoleController.class.getPackage().getName();
//        packageName = packageName.substring(0 , packageName.lastIndexOf("."));
//        packageName = packageName.substring(0 , packageName.lastIndexOf("."));
//        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
//                .groupName("系统管理").select().apis(RequestHandlerSelectors.basePackage(packageName)).paths(PathSelectors.any())
//                .build().apiInfo(this.getApiInfo());
//        return docket;
//    }
//
//    /**
//     * 系统管理
//     *
//     * @return springfox.documentation.spring.web.plugins.Docket
//     */
//    @Bean
//    public Docket createBlogManageApi() {
//        String packageName = FriendsLink.class.getPackage().getName();
//        packageName = packageName.substring(0 , packageName.lastIndexOf("."));
//        packageName = packageName.substring(0 , packageName.lastIndexOf("."));
//        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
//                .groupName("博客管理").select().apis(RequestHandlerSelectors.basePackage(packageName)).paths(PathSelectors.any())
//                .build().apiInfo(this.getApiInfo());
//        return docket;
//    }

    /**
     * 第三方登录
     *
     * @return springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createThirdLoginApi() {
        String packageName = LoginSinaController.class.getPackage().getName();
        packageName = packageName.substring(0 , packageName.lastIndexOf("."));
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName("第三方登录").select().apis(RequestHandlerSelectors.basePackage(packageName)).paths(PathSelectors.any())
                .build().apiInfo(this.getApiInfo());
        return docket;
    }

    @Bean
    public Docket createClientApi() {
        String[] basePackages = {"cn.chendd.blog.admin.client"};
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName("接口交互").select();
        for (String basePackage : basePackages) {
            builder.apis(RequestHandlerSelectors.basePackage(basePackage));
        }
        return builder.paths(PathSelectors.any()).build().apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("chendd-blog")
                .description("chendd博客系统")
                .version("1.0.0")
                .contact(new Contact("chendd", "https://www.chendd.cn", "88911006@qq.com"))
                .build();
        return apiInfo;
    }

}
