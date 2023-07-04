package cn.chendd.third.login.controller;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;

/**
 * 第三方登录 - Sina微博登录
 *
 * @author chendd
 * @date 2019/12/21 21:55
 */
@Api(tags = "Sina微博登录")
@ApiSort(50)
@RequestMapping(value = "/third-login")
@RestController
@ApiVersion("1")
public class LoginSinaController extends ThirdLoginController {

    @GetMapping(value = "/sina" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Sina微博登录",notes = CALLBACK_NOTES)
    @ApiOperationSupport(order = 10)
    public String login(
            @ApiParam(value = "朋友站点标识")
            @RequestParam(required = false)
            String friend) throws Exception {
        String link = super.getLink(friend);
        Oauth oauth = new Oauth();
        String authorize = oauth.authorize("code", link);
        return authorize;
    }

    @GetMapping(value = "/sinaCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Sina微博登录回调",notes = "第三方登录 - Sina微博登录回调，被动发起请求")
    @ApiOperationSupport(order = 20)
    public ThirdUserResult sinaCallback(
            @ApiParam(value = "weibo回调标识" , required = true) @RequestParam String code ,
            @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect) throws Exception {
        Oauth oauth = new Oauth();
        AccessToken accessToken = oauth.getAccessTokenByCode(code);
        if(accessToken == null || StringUtils.isEmpty(accessToken.getAccessToken())) {
            throw new ValidateDataException("新浪微博授权失败，授权Token信息为空！");
        }
        String uid = accessToken.getUid();
        if(StringUtils.isEmpty(uid)) {
            throw new ValidateDataException("新浪微博授权失败，用户信息为空！");
        }
        Users users = new Users();
        //设置Token
        users.setToken(accessToken.getAccessToken());
        User user = users.showUserById(uid);
        String name = user.getName();
        //头像，默认180像素
        String avatarLarge = user.getAvatarLarge();
        //将构造用户来源为第三方的SysUser对象，并存储至session中
        ThirdUserResult userResult = new ThirdUserResult(uid , name , avatarLarge , EnumUserSource.Sina_weibo , redirect);
        super.checkRedirect(redirect , userResult);
        return userResult;
    }

}
