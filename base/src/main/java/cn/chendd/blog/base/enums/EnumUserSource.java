package cn.chendd.blog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 用户来源枚举
 *
 * @author chendd
 * @date 2019/11/24 17:43
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumUserSource implements IEnum<String> {

    /**
     * 系统注册
     */
    System("系统注册"),
    Baidu("百度"),
    Tencent_qq("QQ"),
    Alipay("支付宝"),
    Sina_weibo("微博"),
    Gitee("Gitee"),
    Github("Github"),
    ;

    private String text;

    EnumUserSource(String text){
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }

}
