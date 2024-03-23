package cn.chendd.blog.web.captcha.client;

import cn.chendd.blog.web.captcha.po.CaptchaParam;
import cn.chendd.blog.web.captcha.vo.CaptchaResult;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Request;

/**
 * 腾讯滑动式验证码接口请求客户端
 *
 * @author chendd
 * @date 2021/2/23 20:20
 */

@Deprecated
public interface TencentCaptchaClient {

    /**
     * 腾讯滑动验证码服务端验证接口
     * @param param 请求参数
     * @return 接口原始结构响应
     */
    @Request(url = "${param.httpUrl}" , dataType = "json" ,
             data = {"aid=${param.appId}" , "AppSecretKey=${param.secret}" , "Ticket=${param.ticket}" , "Randstr=${param.randstr}" , "UserIP=${param.userIp}"})
    CaptchaResult validate(@DataVariable("param") CaptchaParam param);
}
