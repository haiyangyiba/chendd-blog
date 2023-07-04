package cn.chendd.blog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;

/**
 * admin主题枚举
 *
 * @author chendd
 * @date 2020/8/22 20:28
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumAdminTheme implements IEnum {

    /**
     * 默认
     */
    light("light" , "默认白"),
    /**
     * 黑色主题
     */
    dark("dark" , "经典黑"),
    ;

    private String type , text;

    EnumAdminTheme(String type , String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public Serializable getValue() {
        return this.name();
    }
}
