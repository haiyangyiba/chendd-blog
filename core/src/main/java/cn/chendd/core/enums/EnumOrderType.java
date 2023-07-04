package cn.chendd.core.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 排序方式名称
 *
 * @author chendd
 * @date 2020/8/15 19:44
 */
@Getter
public enum EnumOrderType {

    /**
     * 正序
     */
    asc("正序") ,
    /**
     * 倒序
     */
    desc("倒序"),
    ;

    private String type;

    EnumOrderType(String type) {
        this.type = type;
    }

    /**
     * 是否存在指定类型实例
     * @param type 排序方式
     * @return true ？ 存在 ：不存在
     */
    public static boolean hasEnum(String type) {
        EnumOrderType[] values = EnumOrderType.values();
        for (EnumOrderType value : values) {
            if(StringUtils.equalsIgnoreCase(value.name() , type)) {
                return true;
            }
        }
        return false;
    }

}
