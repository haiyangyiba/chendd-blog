package cn.chendd.blog.admin.configurations;

import cn.chendd.blog.admin.system.sysmenu.ApiController;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试插件类
 *
 * @author chendd
 * @date 2019/11/20 23:53
 */
@Component
@Order(value = SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1)
public class ReloadTagsPlugin implements OperationBuilderPlugin {

    public ReloadTagsPlugin(){
    }

    @Autowired
    private DocumentationPluginsManager pluginsManager;

    @Override
    public void apply(OperationContext context) {
//        context.operationBuilder().tags();
        OperationBuilder operationBuilder = context.operationBuilder();
        System.out.println(operationBuilder.getClass());
        //Set<String> tags = operationBuilder.build().getTags();

        Optional<ApiController> controllerAnnotation = context.findControllerAnnotation(ApiController.class);
        if(controllerAnnotation.isPresent() == false){
            return;
        }
        ApiController apiController = controllerAnnotation.get();
        Set<String> temps = new HashSet<>();
        /*System.out.println(tags);
        for (String tag : tags) {
            if("响应异常类封装".equals(tag)){
                temps.add("chendd-demo");
            }
            System.out.println("已经替换...");
            operationBuilder.tags(temps);
        }*/
        /*temps.add(apiController.tags()[0]);
        operationBuilder.tags(temps);
        System.out.println("ReloadTagsPlugin.apply");
        System.out.println(context.getDocumentationContext());*/
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
