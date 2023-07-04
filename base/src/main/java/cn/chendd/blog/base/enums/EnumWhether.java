package cn.chendd.blog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 是与否枚举
 *
 * @author chendd
 * @date 2020/7/15 10:10
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumWhether implements IEnum<String> {

    yes("是") ,
    no("否"),
    ;

    private String text;

    EnumWhether(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public EnumWhether fromString(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return EnumUtils.getEnumIgnoreCase(EnumWhether.class , name);
    }

}
