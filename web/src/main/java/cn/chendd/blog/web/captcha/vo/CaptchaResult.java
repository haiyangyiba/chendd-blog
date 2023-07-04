package cn.chendd.blog.web.captcha.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 腾讯滑动验证码结果集映射
 * Api接口说明地址：https://007.qq.com/java-access.html?ADTAG=acces.cfg
 * @author chendd
 * @date 2021/2/23 20:30
 */
@Getter
@Setter
public class CaptchaResult {

    @ApiModelProperty("1:验证成功，0:验证失败，100:AppSecretKey参数校验错误[required]")
    private String response;
    @JSONField(name = "evil_level")
    @ApiModelProperty("[0,100]，恶意等级[optional]")
    private Integer evilLevel;
    @JSONField(name = "err_msg")
    @ApiModelProperty("验证错误信息[optional]")
    private String errMsg;

    @ApiModelProperty("验证结果，成功true，失败false")
    @JSONField(serialize = false)
    public Boolean isResult() {
        return StringUtils.equals("1" , response);
    }

}
