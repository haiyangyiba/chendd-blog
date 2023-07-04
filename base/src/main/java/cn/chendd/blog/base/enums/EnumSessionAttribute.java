package cn.chendd.blog.base.enums;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * Session属性名称
 *
 * @author chendd
 * @date 2020/7/15 23:10
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumSessionAttribute implements IEnum<String>, ISessionAttbitute {

    //仅打开浏览器，不存在session中的数据
    none("浏览器", "default") {
        @Override
        public String getStringValue(Object value) {
            return "session中无数据";
        }
    },
    systemCurrentUser("登录用户", "green") {
        @Override
        public String getStringValue(Object value) {
            JSONObject json = (JSONObject) value;
            return this.getText() + "：" + json.getJSONObject("account").getString("username");
        }
    },
    systemValidateCode("验证码", "blue") {
        @Override
        public String getStringValue(Object value) {
            return this.getText() + "：" + value;
        }
    },
    ;

    private String text;//显示文本
    private String color;//显示颜色

    EnumSessionAttribute(String text, String color) {
        this.text = text;
        this.color = color;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}

interface ISessionAttbitute {

    /**
     * @param value session中的参数值
     * @return 获取对应数据的String类型值
     */
    String getStringValue(Object value);

}