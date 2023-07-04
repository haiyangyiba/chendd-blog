package cn.chendd.blog.admin.system.sysmenu;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * ApiController
 *
 * @author chendd
 * @date 2019/11/21 23:09
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Api
@RequestMapping
@RestController
public @interface ApiController {

    String[] path() default "";

    String[] tags() default "";

}
