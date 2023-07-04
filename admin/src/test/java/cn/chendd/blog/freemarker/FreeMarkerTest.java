package cn.chendd.blog.freemarker;

import cn.chendd.BaseBootstrapTest;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * FreeMarkerTest
 *
 * @author chendd
 * @date 2022/4/12 9:21
 */
public class FreeMarkerTest extends BaseBootstrapTest {

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void hello() throws Exception {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("hello.html");
        System.out.println(template.toString());
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("name" , "chendd");
        System.out.println(FreeMarkerTemplateUtils.processTemplateIntoString(template, map));

    }

}
