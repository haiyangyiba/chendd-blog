package cn.chendd.third.login.controller;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.core.enums.EnumResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.Close;
import cn.chendd.third.login.custom.gitee.AccessTokenResult;
import cn.chendd.third.login.custom.gitee.AccessTokenUserInfoResult;
import cn.chendd.third.login.custom.gitee.GiteeOauth;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三方登录 - 接入Gitee码云登录
 *
 * @author chendd
 * @date 2019/12/21 21:55
 */
@Api(tags = "Gitee账号登录")
@ApiSort(40)
@RequestMapping(value = "/third-login")
@Controller
@Slf4j
@ApiVersion("1")
public class LoginGiteeController extends ThirdLoginController{

    @GetMapping(value = "/gitee" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gitee登录",notes = CALLBACK_NOTES)
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public String login(
            @ApiParam(value = "朋友站点标识")
            @RequestParam(required = false)
                    String friend) throws ValidateDataException {
        String link = super.getLink(friend);
        String clientId = super.getGiteeConfig().get("client_id");
        String redirectURI = super.getGiteeConfig().get("redirect_uri");
        String authorizeURL = super.getGiteeConfig().get("authorizeURL");
        GiteeOauth oauth = new GiteeOauth(authorizeURL , clientId , redirectURI , link);
        String authorize = oauth.getAuthorize();
        return authorize;
    }

    @GetMapping(value = "/giteeCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gitee登录回调",notes = "第三方登录 - Gitee登录回调，被动发起请求")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public ThirdUserResult giteeCallback(
            @ApiParam(value = "gitee回调标识" , required = true) @RequestParam String code ,
            @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect) throws Exception {
        //获取用户AccessToken
        BaseResult baseTokenResult = this.getAccessToken(code);
        String tokenResult = baseTokenResult.getResult();
        if(! EnumResult.success.name().equals(tokenResult)){
            throw new ValidateDataException(baseTokenResult.getMessage());
        }
        //获取用户
        AccessTokenResult tokenResultData = (AccessTokenResult) baseTokenResult.getData();
        BaseResult baseTokenUserResult = this.getAccessTokentUserInfo(tokenResultData.getAccessToken());
        String tokenUserResult = baseTokenUserResult.getResult();
        if(! EnumResult.success.name().equals(tokenUserResult)){
            throw new ValidateDataException(baseTokenUserResult.getMessage());
        }
        AccessTokenUserInfoResult userInfoResult = (AccessTokenUserInfoResult) baseTokenUserResult.getData();
        ThirdUserResult result = new ThirdUserResult(String.valueOf(userInfoResult.getId()) ,
                StringUtils.isBlank(userInfoResult.getName()) ? userInfoResult.getLogin() : userInfoResult.getName() ,
                userInfoResult.getAvatarUrl() , EnumUserSource.Gitee , redirect);
        super.checkRedirect(redirect , result);
        return result;
    }

    /**
     * 根据用户授权code获取授权token
     */
    private BaseResult getAccessToken(String code) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type" , "authorization_code"));
        params.add(new BasicNameValuePair("code" , code));
        params.add(new BasicNameValuePair("client_id" , super.getGiteeConfig().get("client_id")));
        params.add(new BasicNameValuePair("redirect_uri" ,
                super.getGiteeConfig().get("redirect_uri")));
        params.add(new BasicNameValuePair("client_secret" , super.getGiteeConfig().get("client_secret")));
        CloseableHttpResponse response = null;
        try {
            String url = super.getGiteeConfig().get("authorizeURL") + "/oauth/token";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            try {
                return new SuccessResult<AccessTokenResult>(JSONObject.parseObject(result , AccessTokenResult.class));
            } catch (Exception e) {
                return new ErrorResult(result);
            }
        } catch (Exception e) {
            log.error("获取token请求异常" , e);
            return new ErrorResult(String.format("获取token请求异常，参考：%s" , e.getMessage()));
        } finally {
            Close.close(response , httpClient);
        }
    }

    /**
     * 根据授权token获取对应的用户详细信息
     */
    private BaseResult getAccessTokentUserInfo(String accessToken) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = super.getGiteeConfig().get("authorizeURL") + "/api/v5/user?access_token=" + accessToken;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
        return super.consumesResponse(httpClient , httpGet , AccessTokenUserInfoResult.class);
    }

}
