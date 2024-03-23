package cn.chendd.blog.web.captcha.service;

import cn.chendd.blog.web.captcha.po.CaptchaParam;
import cn.chendd.blog.web.captcha.vo.CaptchaResult;

/**
 * 腾讯滑动验证码接口定义
 *
 * @author chendd
 * @date 2021/2/23 20:21
 */
@Deprecated
public interface CaptchaService {

    /**
     * 发送接口请求验证结果
     * @param param 验证码请求参数
     * @return 是否成功
     */
    CaptchaResult validate(CaptchaParam param);

}
