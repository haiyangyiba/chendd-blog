package cn.chendd.blog.admin.api;

import cn.chendd.core.spring.SpringBeanFactory;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.http.MediaType;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * Api FreeMarker 启动类排除了FreeMarkerAutoConfiguration，故而该测试类不作为在线测试Api接口
 *
 * @author chendd
 * @date 2022/4/13 11:17
 */
@RestController
@RequestMapping("/api/freemarker")
@Api(value = "测试 FreeMarker 接口" , tags = {"测试 FreeMarker 接口"})
@Deprecated
public class ApiFreemarkerController {

    @GetMapping("/properties")
    @ApiOperation(value = "测试响应 properties 结果集" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public FreeMarkerProperties properties(){
        FreeMarkerProperties properties = SpringBeanFactory.getBean(FreeMarkerProperties.class);
        return properties;
    }

    @GetMapping("/template")
    @ApiOperation(value = "测试响应 template 结果集" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String template(@ApiParam(name="name" , value = "模板名称") @RequestParam String name) throws Exception {
        FreeMarkerConfigurer configurer = SpringBeanFactory.getBean(FreeMarkerConfigurer.class);
        Template template = configurer.getConfiguration().getTemplate(name);
        return template.toString();
    }

    @PutMapping("/template/{name}")
    @ApiOperation(value = "测试响应 template 结果集" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String template(@ApiParam(name="name" , value = "模板名称") @RequestParam String name ,
                           @ApiParam(name="model" , value = "参数集") @RequestBody Map<String , Object> model) throws Exception {
        FreeMarkerConfigurer configurer = SpringBeanFactory.getBean(FreeMarkerConfigurer.class);
        Template template = configurer.getConfiguration().getTemplate(name);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

}
