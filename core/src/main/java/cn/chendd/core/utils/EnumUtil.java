package cn.chendd.core.utils;

import com.baomidou.mybatisplus.core.enums.IEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ResolvableType;

import java.util.Objects;

/**
 * 枚举工具类
 *
 * @author chendd
 * @date 2020/8/15 20:26
 */
public class EnumUtil {

    /**
     * 根据枚举实例获取枚举对象
     * @param name 枚举实例名称
     * @param enumClass 枚举class
     * @param <T> 泛型
     * @return 枚举实例
     */
    public static <T extends Enum> T getInstanceByName(String name , Class<T> enumClass) {
        Class<?> clazz = ResolvableType.forClass(enumClass).resolve();
        assert clazz != null;
        //noinspection unchecked
        T[] values = (T[]) clazz.getEnumConstants();
        Enum instance = null;
        for (Enum value : values) {
            if(StringUtils.equalsIgnoreCase(value.name() , name)) {
                instance = value;
                break;
            }
        }
        //noinspection unchecked
        return (T) instance;
    }

    /**
     * 根据枚举code获取对应实例
     * @param <T> 泛型
     * @param code code
     * @param enumClass 枚举类
     * @return 枚举实例
     */
    public static <T extends IEnum> T getInstanceByCode(String code , Class<T> enumClass) {
        Class<?> clazz = ResolvableType.forClass(enumClass).resolve();
        assert clazz != null;
        Enum[] values = (Enum[]) clazz.getEnumConstants();
        IEnum instance = null;
        for (Enum value : values) {
            IEnum implement = (IEnum) value;
            if(Objects.equals(implement.getValue() , code)){
                instance = implement;
            }
        }
        //noinspection unchecked
        return (T) instance;
    }

}
