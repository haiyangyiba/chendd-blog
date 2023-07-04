package cn.chendd.toolkit.dbproperty.enums;

/**
 * 注入值类型的接口定义
 *
 * @author chendd
 * @date 2019/9/14 17:09
 */
public interface IEnumValueType {

    /**
     * 将String类型的值转换为实际field中定义
     * @param value
     * @return
     */
    Object convertValue(String value);

}
