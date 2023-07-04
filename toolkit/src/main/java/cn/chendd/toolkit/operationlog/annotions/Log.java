package cn.chendd.toolkit.operationlog.annotions;

import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * 自定义注解，用于从标记记录Service日志
 * @author chendd
 * @date 2019/09/12 23:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Log {

    /**
     * 日志记录功能名称，标记此功能分类，默认值为：类的简称名.方法名
     * @return 功能名称
     */
    String name() default StringUtils.EMPTY;

    /**
     * @return 操作日志描述
     */
    String description() default "";

    /**
     * @return 日志记录范围，默认全部记录
     */
    LogScopeEnum scope() default LogScopeEnum.Auto;

    /**
     * 当包含记录参数时的排除某些参数
     * @return 排除属性名称数组
     */
    String[] exclude() default {};

}
