package cn.chendd.blog.web.captcha.service.impl;

import cn.chendd.blog.web.captcha.client.TencentCaptchaClient;
import cn.chendd.blog.web.captcha.commpent.TencentCaptchaConfig;
import cn.chendd.blog.web.captcha.po.CaptchaParam;
import cn.chendd.blog.web.captcha.service.CaptchaService;
import cn.chendd.blog.web.captcha.vo.CaptchaResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 腾讯滑动验证码接口实现
 *
 * @author chendd
 * @date 2021/2/23 20:23
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private TencentCaptchaConfig config;
    @Resource
    private TencentCaptchaClient captchaClient;

    @Override
    public CaptchaResult validate(CaptchaParam param) {
        BeanUtils.copyProperties(config , param);
        CaptchaResult result = captchaClient.validate(param);
        return result;
    }
}
