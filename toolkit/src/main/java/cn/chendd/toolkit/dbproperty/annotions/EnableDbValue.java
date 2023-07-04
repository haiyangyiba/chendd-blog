package cn.chendd.toolkit.dbproperty.annotions;

import java.lang.annotation.*;

/**
 * 自定义注解，用于标记某个bean组件中开启了数据库参数注入的功能标识
 *
 * @author chendd
 * @date 2019/09/12 23:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@Deprecated
public @interface EnableDbValue {

    /**
     * 指定参数获取的来源，来源于哪个参数配置对象
     * @return 参数匹配实体类
     */
    Class<?> value();

}
