package cn.chendd.blog.base.swagger;

import org.apache.commons.compress.utils.FileNameUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * BaseSwagger
 *
 * @author chendd
 * @date 2021/4/29 14:09
 */
public interface BaseSwagger {


    default Docket newInstance(String groupName) {
        String packageName = this.getClass().getPackage().getName();
        String parentPackageName = FileNameUtils.getBaseName(packageName);
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(parentPackageName))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
        return docket;
    }

    default Docket newInstance(String groupName , String basePackage) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
        return docket;
    }

    default ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("chendd-blog")
                .description("chendd博客系统")
                .version("1.0.0")
                .termsOfServiceUrl("https://www.chendd.cn")
                .contact(new Contact("chendd", "https://www.chendd.cn", "88911006@qq.com"))
                .build();
        return apiInfo;
    }

}
