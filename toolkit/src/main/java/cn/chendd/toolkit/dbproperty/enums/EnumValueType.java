package cn.chendd.toolkit.dbproperty.enums;

import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.utils.DateFormat;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 数据值类型范围
 *
 * @author chendd
 * @date 2019/9/12 23:36
 */
public enum EnumValueType implements IEnumValueType {

    INTEGER{
        @Override
        public Object convertValue(String value) {
            return NumberUtils.createInteger(value);
        }
    },
    FLOAT{
        @Override
        public Object convertValue(String value) {
            return NumberUtils.createFloat(value);
        }
    },
    DOUBLE{
        @Override
        public Object convertValue(String value) {
            return NumberUtils.createDouble(value);
        }
    },
    STRING{
        @Override
        public Object convertValue(String value) {
            return value;
        }
    },
    BOOLEAN{
        @Override
        public Object convertValue(String value) {
            return BooleanUtils.toBoolean(value);
        }
    },
    DATE{
        @Override
        public Object convertValue(String value) {
            return DateFormat.parseDate(value , Constant.DATE_TIME_PATTERN);
        }
    }
    ;

    /**
     * 根据值类型获取对应的枚举实例
     * @param fieldType
     * @return 枚举实例
     */
    public static EnumValueType getInstanceByFieldType(String fieldType){
        EnumValueType enumValueType = null;
        EnumValueType types[] = EnumValueType.values();
        for (EnumValueType type : types) {
            if(fieldType.toUpperCase().endsWith(type.name())){
                enumValueType = type;
                break;
            }
        }
        return enumValueType;
    }

    public static Object getInstanceByValueType(Class<?> fieldType , String value){
        if((fieldType == Integer.class || fieldType == Integer.TYPE) && StringUtils.isNotEmpty(value)){
            return Integer.valueOf(value);
        } else if(fieldType == Double.class || fieldType == Double.TYPE && StringUtils.isNotEmpty(value)){
            return Double.valueOf(value);
        }
        return null;
    }

}
