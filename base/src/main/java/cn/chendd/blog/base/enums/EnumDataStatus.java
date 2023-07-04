package cn.chendd.blog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 表数据可用状态枚举
 *
 * @author chendd
 * @date 2019/9/19 11:00
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumDataStatus implements IEnum<String> {

    USABLE("可用"),
    DISABLE("禁用"),
    ;

    private String text;

    EnumDataStatus(String text){
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
