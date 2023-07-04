package cn.chendd.blog.web.captcha.commpent;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 接入腾讯验证码接口参数配置
 *
 * @author chendd
 * @date 2021/2/23 20:52
 */
@Component
@DbValueConfiguration
@Data
public class TencentCaptchaConfig {

    @DbValue(group = "腾讯滑动验证码" , value = "appId")
    private String appId;
    @DbValue(group = "腾讯滑动验证码" , value = "secret")
    private String secret;
    @DbValue(group = "腾讯滑动验证码" , value = "httpUrl")
    private String httpUrl;

}
