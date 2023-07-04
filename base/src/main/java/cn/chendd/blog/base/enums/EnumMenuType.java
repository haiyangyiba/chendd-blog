package cn.chendd.blog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author chendd
 * @date 2019/10/27 20:01
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumMenuType implements IEnum<String> {

    BUTTON("按钮"),
    MENU("菜单"),
    ;

    private String text;

    EnumMenuType(String text){
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }

}
