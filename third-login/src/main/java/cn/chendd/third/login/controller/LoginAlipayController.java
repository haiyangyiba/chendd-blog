package cn.chendd.third.login.controller;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.third.login.custom.alipay.AlipayConfig;
import cn.chendd.third.login.custom.alipay.AlipayOauth;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 第三方登录 - Sina微博登录
 *
 * @author chendd
 * @date 2019/12/21 21:55
 */
@Api(tags = "Alipay账号登录")
@ApiSort(20)
@RequestMapping(value = "/third-login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiVersion("1")
@RestController
@Slf4j
public class LoginAlipayController extends ThirdLoginController {

    @GetMapping("/alipay")
    @ApiOperation(value = "Alipay账号登录",notes = CALLBACK_NOTES)
    @ApiOperationSupport(order = 10)
    public String login(@ApiParam(value = "朋友站点标识") @RequestParam(required = false) String friend) {
        String link = super.getLink(friend);
        AlipayOauth alipayOauth = new AlipayOauth(super.getAlipayConfig() , link);
        return alipayOauth.getAuthorize();
    }

    @GetMapping(value = "/alipayCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Alipay登录回调",notes = "第三方登录 - 支付宝登录回调，被动发起请求")
    @ApiOperationSupport(order = 20)
    protected ThirdUserResult alipayCallback(
                                    @ApiParam(value = "alipay回调标识" , required = true) @RequestParam("auth_code") String code ,
                                    @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect)
            throws RuntimeException, IOException {
        AlipayConfig config = super.getAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(config.getAuthAddress(), config.getAppId(), config.getAppPrivateKey() , "json",
                Charsets.UTF_8.name(), config.getAlipayPublicKey(), "RSA2");
        //获取Token
        AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
        oauthTokenRequest.setCode(code);
        oauthTokenRequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse oauthTokenResponse;
        try {
            oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
        } catch (AlipayApiException e) {
            log.error("AlipayApiException", e);
            throw new ValidateDataException(String.format("[支付宝登录]请求获取AccessToken错误，参考：%s" , e.getErrMsg()));
        }
        String accessToken = oauthTokenResponse.getAccessToken();
        if(StringUtils.isEmpty(accessToken)) {
            throw new ValidateDataException("接入支付宝登录认证出现错误，未获取到accessToken");
        }
        //获取用户信息
        AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse userInfoResponse = null;
        try {
            userInfoResponse = alipayClient.execute(userInfoRequest , accessToken);
        } catch (AlipayApiException e) {
            log.error("AlipayApiException", e);
            throw new ValidateDataException(String.format("[支付宝登录]发送获取用户信息接口异常，参考：%s" , e.getErrMsg()));
        }
        if(userInfoResponse.isSuccess()) {
            //将构造用户来源为第三方的SysUser对象，并存储至session中
            ThirdUserResult userResult = new ThirdUserResult(
                    String.valueOf(userInfoResponse.getUserId()) , userInfoResponse.getNickName() , userInfoResponse.getAvatar() , EnumUserSource.Alipay , redirect);
            super.checkRedirect(redirect , userResult);
            return userResult;
        } else {
            throw new ValidateDataException("[支付宝登录]未获取到用户信息");
        }

    }

}
