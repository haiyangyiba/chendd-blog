package cn.chendd.toolkit.dbproperty.annotions;


import java.lang.annotation.*;

/**
 * 自定义注解，用于标记某个类被注入参数注解所解析识别
 * @author chendd
 * @date 2019/09/12 23:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface DbValueConfiguration {

}
