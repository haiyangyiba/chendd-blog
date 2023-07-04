package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.web.home.service.UserLoginService;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.third.login.controller.LoginBaiduController;
import com.alibaba.fastjson.JSONObject;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import io.swagger.annotations.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

import static cn.chendd.core.common.constant.Constant.SYSTEM_CURRENT_USER;

/**
 * 前台参数值交互Controller
 *
 * @author chendd
 * @date 2021/2/9 21:51
 */
@Api(value = "第三方登录" , tags = "web端第三方登录")
@ApiSort(20)
@Controller
@RequestMapping("/third-login")
public class WebThirdLoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WebThirdLoginController.class);

    @Resource
    private UserLoginService userLoginService;

    @GetMapping("/{key}")
    @ApiOperation(value = "根据key获取对应第三方登录地址" , notes = "登录地址获取")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public BaseResult<String> getLoginUrl(@ApiParam("key") @PathVariable String key , @ApiParam("friend") String friend) throws QQConnectException {
        if (StringUtils.containsIgnoreCase(EnumUserSource.Tencent_qq.getValue() , key)) {
            String redrectUrl = new Oauth().getAuthorizeURL(request);
            return new SuccessResult<>(redrectUrl);
        } else if (StringUtils.equalsAnyIgnoreCase(EnumUserSource.Baidu.getValue() , key)) {
            LoginBaiduController baidu = SpringBeanFactory.getBean(LoginBaiduController.class);
            //对于request、response这种特殊对象调用方法时进行赋值
            String redrectUrl = baidu.login(friend , request , response);
            return new SuccessResult<>(redrectUrl);
        }
        BaseResult<String> result = userLoginService.getLoginUrl(key , friend);
        return result;
    }

    @GetMapping("/loginCallback")
    @ApiOperation(value = "处理第三方登录响应" , notes = "第三方登录结果回调")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public void handleLogin(@ApiParam("用户信息") @RequestParam(name = "userResult" , required = false) String userResult) throws IOException {
        /*Boolean isLogin = super.getCurrentUser();
        if (isLogin || StringUtils.isEmpty(userResult)) {
            logger.info("用户已经登录或回调用户信息为空，{}！" , StringUtils.isEmpty(userResult));
            response.sendRedirect("/");
            return;
        }*/
        ThirdUserResult thirdUserResult;
        try {
            thirdUserResult = JSONObject.parseObject(URLDecoder.decode(userResult , Charsets.UTF_8.name()) , ThirdUserResult.class);
            //存储当前第三方用户信息至本地用户表，并赋予默认角色
        } catch (Exception e) {
            logger.error("第三方登录结果回调出现错误：" , e);
            throw e;
        }
        SysUserResult userEntity = userLoginService.thirdUserStore(thirdUserResult);
        session.setAttribute(SYSTEM_CURRENT_USER, JSONObject.toJSON(userEntity));
        session.setMaxInactiveInterval(60 * 60);
        response.sendRedirect("/");
    }

    /**
     * 用户登录后的响应
     * @param request request
     * @param response response
     * @param session session
     * @param userResult userResult
     * @throws IOException Exception
     */
    public void handleLogin(HttpServletRequest request, HttpServletResponse response , HttpSession session , String userResult) throws IOException {
        super.request = request;
        super.response = response;
        this.session = session;
        this.handleLogin(userResult);
    }
}
