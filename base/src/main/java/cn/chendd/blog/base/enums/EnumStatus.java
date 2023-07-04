package cn.chendd.blog.base.enums;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表数据可用状态枚举
 *
 * @author chendd
 * @date 2019/9/19 11:00
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumStatus implements IEnum<String> {

    /**
     *
     */
    ENABLE("启用"),
    DISABLE("禁用"),
    ;

    private String text;

    EnumStatus(String text){
        this.text = text;
    }

    @JSONField
    public String getText() {
        return text;
    }

    @Override
    @JSONField
    public String getValue() {
        return this.name();
    }
}
