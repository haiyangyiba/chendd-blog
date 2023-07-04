package cn.chendd.blog.base.thymeleaf.enums;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import cn.chendd.blog.base.thymeleaf.format.impl.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 格式化枚举类型
 *
 * @author chendd
 * @date 2020/5/31 22:37
 */
@Getter
public enum EnumFormatType {

    /**
     * 千分位金额
     */
    amount(AmountFormat.class),
    /**
     * 磁盘容量大小
     */
    ram(RamFormat.class),
    /**
     * 百分比
     */
    percentage(PercentageFormat.class),
    /**
     * 时间范围
     */
    datetime(DatetimeFormat.class),
    /**
     * 未找到
     */
    undefined(UndefinedFormat.class)
    ;

    private Class<? extends IFormatType> clazz;

    EnumFormatType(Class<? extends IFormatType> clazz) {
        this.clazz = clazz;
    }

    public static EnumFormatType getInstance(String name) {
        EnumFormatType types[] = EnumFormatType.values();
        EnumFormatType formatType = null;
        for (EnumFormatType type : types) {
            String currentName = type.name();
            if(StringUtils.equals(currentName , name)){
                formatType = type;
                break;
            }
        }
        return formatType;
    }

}
