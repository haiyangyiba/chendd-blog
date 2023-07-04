package cn.chendd.blog.web.special.baidu;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.web.home.controller.WebThirdLoginController;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.third.login.controller.LoginBaiduController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 百度第三方登录回调
 *
 * @author chendd
 * @date 2022/4/4 22:36
 */
@Controller
@RequestMapping("/third-login")
@Slf4j
public class WebLoginBaiduController extends BaseController {

    @Resource
    private WebThirdLoginController webThirdLoginController;

    @GetMapping(value = "/baiduCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    protected void baiduCallback(@ApiParam(value = "baidu回调标识" , required = true) @RequestParam String code ,
                                            @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect)
            throws ServletException, IOException {
        LoginBaiduController baidu = SpringBeanFactory.getBean(LoginBaiduController.class);
        //对于request、response这种特殊对象调用方法时进行赋值
        ThirdUserResult userResult = baidu.baiduCallback(code, redirect, request, response);
        webThirdLoginController.handleLogin(request , response , session , JSON.toJSONString(userResult));
    }

}
