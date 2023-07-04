package cn.chendd.toolkit.dbproperty.annotions;


import java.lang.annotation.*;

/**
 * 自定义注解，用于从数据库中读取参数配置
 * @author chendd
 * @date 2019/09/12 23:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface DbValue {

    /**
     * @return 参数名称
     */
    String value() default "";

    /**
     * @return 参数前缀
     */
    String prefix() default "";

    /**
     * @return 参数后缀
     */
    String suffix() default "";

    /**
     * @return 参数组名
     */
    String group() default "";

}
