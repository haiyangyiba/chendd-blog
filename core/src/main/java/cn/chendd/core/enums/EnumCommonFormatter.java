package cn.chendd.core.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 枚举转换，采用 枚举实例作为变量
 *
 * @author chendd
 * @date 2020/7/15 16:51
 */
public class EnumCommonFormatter implements ConverterFactory<String, Enum> {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> classType) {

        return (Converter<String, T>) new StringConvertEnum(classType);
    }

    /**
     * 根据 name 获取某个枚举实例
     * @param classType 枚举类class
     * @param name 枚举实例名称
     * @param <T> 具体枚举类
     * @return 枚举项
     */
    static <T extends Enum> T getEnumItem(Class<T> classType, String name) {
        for (T item : classType.getEnumConstants()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 枚举转换
     */
    public static class StringConvertEnum implements Converter<String , Enum> {

        private Class<? extends Enum> classType;

        public StringConvertEnum(Class<? extends Enum> classType) {
            this.classType = classType;
        }

        @Override
        public Enum convert(String name) {
            if(StringUtils.isEmpty(name)) {
                return null;
            }
            return EnumCommonFormatter.getEnumItem(classType , name);
        }
    }

}
