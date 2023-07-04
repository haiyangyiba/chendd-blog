package cn.chendd.blog.web.captcha.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码接口验证发送请求对象
 *
 * @author chendd
 * @date 2021/2/23 20:36
 */
@Getter
@Setter
public class CaptchaParam {

    @ApiModelProperty("接口请求url地址")
    private String httpUrl;
    @ApiModelProperty("appId")
    private String appId;
    @ApiModelProperty("secret")
    private String secret;
    @ApiModelProperty("ticket")
    private String ticket;
    @ApiModelProperty("随机标识")
    private String randstr;
    @ApiModelProperty("客户端IP")
    private String userIp;

}
