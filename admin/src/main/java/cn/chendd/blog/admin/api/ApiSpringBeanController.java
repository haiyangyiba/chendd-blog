package cn.chendd.blog.admin.api;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.spring.SpringBeanFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.list.TreeList;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.TreeSet;

/**
 * Api测试SpringBean
 *
 * @author chendd
 * @date 2022/4/15 22:13
 */
@RestController
@RequestMapping("/api/spring")
@Api(value = "测试 SpringBean 接口" , tags = {"测试 SpringBean 接口"})
public class ApiSpringBeanController extends BaseController {

    @GetMapping("/beans")
    @ApiOperation(value = "测试获取 beans 结果集" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public TreeSet getBeans() {
        String[] names = SpringBeanFactory.getApplicationContext().getBeanDefinitionNames();
        TreeSet<String> set = new TreeSet();
        for (String name : names) {
            String item = name + "------" + SpringBeanFactory.getBean(name);
            set.add(item);
        }
        return set;
    }

}
