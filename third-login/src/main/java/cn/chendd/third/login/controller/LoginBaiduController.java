package cn.chendd.third.login.controller;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import com.alibaba.fastjson.JSON;
import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录 - Sina微博登录
 *
 * @author chendd
 * @date 2019/12/21 21:55
 */
@Api(tags = "Baidu账号登录")
@ApiSort(30)
@RequestMapping(value = "/third-login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("2")
@Slf4j
public class LoginBaiduController extends ThirdLoginController {

    @GetMapping(value = "/baidu")
    @ApiOperation(value = "Baidu账号登录",notes = CALLBACK_NOTES)
    @ApiOperationSupport(order = 10)
    public String login(@ApiParam(value = "朋友站点标识") @RequestParam(required = false) String friend) throws ValidateDataException {
        String appId = super.getBaiduConfig().get("appId");
        String secretKey = super.getBaiduConfig().get("appSecretKey");
        String callback = super.getBaiduConfig().get("callback");
        String link = super.getLink(friend);
        BaiduStore store = new BaiduCookieStore(appId, request, response);
        Baidu baidu = null;
        try {
            baidu = new Baidu(appId, secretKey, callback, store, request);
            Map<String, String> params = new HashMap<>();
            String state = baidu.getState();
            params.put("state" , state);
            /*params.put("state", link);*/
            String authorizeUrl = baidu.getBaiduOAuth2Service().getAuthorizeUrl(params);
            return authorizeUrl;
        } catch (BaiduOAuthException e) {
            log.error("接入百度登录认证.BaiduOAuthException ", e);
            throw new ValidateDataException("百度登录出现错误：" + e.getMessage());
        } catch (BaiduApiException e) {
            log.error("接入百度登录Api.BaiduApiException ", e);
            throw new ValidateDataException("百度登录出现错误：" + e.getMessage());
        }
    }

    /**
     *
     * @param friend 友联站点标识
     * @param request request请求
     * @param response response响应
     * @return 请求地址
     */
    public String login(String friend , HttpServletRequest request , HttpServletResponse response) {
        super.request = request;
        super.response = response;
        return this.login(friend);
    }

    @GetMapping(value = "/baiduCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Baidu登录回调",notes = "第三方登录 - 百度登录回调，被动发起请求")
    @ApiOperationSupport(order = 20)
    public ThirdUserResult baiduCallback(@ApiParam(value = "baidu回调标识" , required = true) @RequestParam String code ,
                         @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect)
            throws ServletException, IOException {
        String clientId = super.getBaiduConfig().get("appId");
        String clientSecret = super.getBaiduConfig().get("appSecretKey");
        String redirectUri = super.getBaiduConfig().get("callback");
        BaiduStore store = new BaiduCookieStore(clientId, request, response);
        log.info("回调参数集合：" + JSON.toJSONString(super.request.getParameterMap()));
        log.info("code：{}" , code);
        Baidu baidu;
        User loggedInUser;
        try {
            System.out.println(request.getRequestURL().toString());
            baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
            log.info("accessToken：{}，refreshToken：{}，sessionKey：{}，sessionSecret：{}" ,
                    baidu.getAccessToken() , baidu.getRefreshToken() , baidu.getSessionKey() , baidu.getSessionSecret());
            loggedInUser = baidu.getLoggedInUser();
            if (loggedInUser != null) {
                //绑定用户头像地址前缀
                String userImage = super.getBaiduConfig().get("userImage.prefix") + loggedInUser.getPortrait();
                //将构造用户来源为第三方的SysUser对象，并存储至session中
                ThirdUserResult userResult = new ThirdUserResult(
                        String.valueOf(loggedInUser.getUid()) , loggedInUser.getUname() , userImage , EnumUserSource.Baidu , redirect);
                return userResult;
            }
            throw new ValidateDataException("接入百度登录授权出现错误，没有用户信息");
        } catch (BaiduApiException e) {
            log.error("BaiduApiException", e);
            throw new ValidateDataException(String.format("接入百度登录授权出现错误：%s" , e.getErrorMsg()));
        } catch (BaiduOAuthException e) {
            log.error("BaiduOAuthException ", e);
            throw new ValidateDataException(String.format("接入百度登录授权出现错误：%s" , e.getErrorDesp()));
        }

    }

    /**
     * 百度第三方登录回调接收
     * @param code 响应码
     * @param redirect 回调地址
     * @param request request请求
     * @param response response响应
     * @return 结果
     */
    public ThirdUserResult baiduCallback(String code , String redirect , HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        super.request = request;
        super.response = response;
        return this.baiduCallback(code , redirect);
    }

}
