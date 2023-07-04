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
import cn.chendd.third.login.custom.github.AccessTokenResult;
import cn.chendd.third.login.custom.github.AccessTokenUserInfoResult;
import cn.chendd.third.login.custom.github.GithubConfig;
import cn.chendd.third.login.custom.github.GithubOauth;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三方登录 - Github登录
 *
 * @author chendd
 * @date 2020/01/04 22:15
 */
@Api(tags = "Github账号登录")
@ApiSort(50)
@RequestMapping(value = "/third-login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
@Slf4j
public class LoginGithubController extends ThirdLoginController {

    @GetMapping(value = "/github" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "github登录",notes = CALLBACK_NOTES)
    @ApiOperationSupport(order = 10)
    public String login(
            @ApiParam(value = "朋友站点标识")
            @RequestParam(required = false)
                    String friend) {
        String link = super.getLink(friend);
        GithubConfig config = super.getGithubConfig();
        GithubOauth oauth = new GithubOauth(config , link);
        return oauth.getAuthorize();
    }

    @GetMapping(value = "/githubCallback" , consumes = MediaType.ALL_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "github登录回调",notes = "第三方登录 - Github登录回调，被动发起请求")
    @ApiOperationSupport(order = 20)
    public ThirdUserResult githubCallback(
            @ApiParam(value = "github回调标识" , required = true) @RequestParam String code ,
            @ApiParam("朋友站点标识") @RequestParam(value = "state" , required = false) String redirect) throws Exception {
        //获取用户AccessToken
        BaseResult baseTokenResult = this.getAccessToken(code, redirect);
        String tokenResult = baseTokenResult.getResult();
        if(! EnumResult.success.name().equals(tokenResult)){
            throw new ValidateDataException(baseTokenResult.getMessage());
        }
        AccessTokenResult tokenResultData = (AccessTokenResult) baseTokenResult.getData();
        BaseResult baseTokenUserResult = this.geAccessTokentUserInfo(tokenResultData.getAccessToken());
        String tokenUserResult = baseTokenUserResult.getResult();
        if(! EnumResult.success.name().equals(tokenUserResult)){
            throw new ValidateDataException(baseTokenUserResult.getMessage());
        }
        AccessTokenUserInfoResult userInfoResult = (AccessTokenUserInfoResult) baseTokenUserResult.getData();
        ThirdUserResult result = new ThirdUserResult(String.valueOf(userInfoResult.getId()) ,
                StringUtils.isBlank(userInfoResult.getName()) ? userInfoResult.getLogin() : userInfoResult.getName() ,
                userInfoResult.getAvatarUrl() , EnumUserSource.Github , redirect);
        super.checkRedirect(redirect , result);
        return result;
    }

    /**
     * 根据用户授权code获取授权token
     */
    private BaseResult getAccessToken(String code , String redirect) {
        GithubConfig config = super.getGithubConfig();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id" , config.getClientId()));
        params.add(new BasicNameValuePair("client_secret" , config.getClientSecret()));
        params.add(new BasicNameValuePair("code" , code));
        params.add(new BasicNameValuePair("redirect_uri" , config.getRedirectUri()));
        params.add(new BasicNameValuePair("state" , redirect));
        CloseableHttpResponse response = null;
        try {
            String url = config.getAuthorizeURL() + "/access_token";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            if(StringUtils.contains(result , "access_token=") && StringUtils.contains(result ,"token_type=")){
                return new SuccessResult<AccessTokenResult>(new AccessTokenResult(result));
            }
            return new ErrorResult(result);
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
    private BaseResult geAccessTokentUserInfo(String accessToken){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = super.getGithubConfig().getUserApiURL();
        /**
         * 经过验证，url=url?access_token=access_token的地址与从header中传递结果一致
         */
        HttpGet httpGet = new HttpGet(url/* + "?access_token=" + accessToken*/);
        httpGet.setHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
        httpGet.setHeader("Authorization" , "token " + accessToken);
        return super.consumesResponse(httpClient , httpGet , AccessTokenUserInfoResult.class);
    }

}
