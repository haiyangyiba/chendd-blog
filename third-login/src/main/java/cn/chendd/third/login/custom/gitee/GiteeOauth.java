package cn.chendd.third.login.custom.gitee;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 接入gitee登录实现
 *
 * @author chendd
 * @date 2019/12/29 20:59
 */
@Slf4j
public class GiteeOauth {

    private StringBuilder urlBuilder;

    public GiteeOauth(String authorizeURL , String clientId , String redirectURI , String link) {
        urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(authorizeURL)
                    .append("/oauth/authorize?client_id=").append(clientId)
                    .append("&redirect_uri=").append(URLEncoder.encode(redirectURI , Charsets.UTF_8.name()))
                    .append("&response_type=code");
            if(StringUtils.isNotBlank(link)){
                urlBuilder.append("&state=").append(link);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("构造gitee登录出现错误：" , e);
        }
    }

    public GiteeOauth(String authorizeURL, String code , String clientId , String redirectURI , String clientSecret) {
        urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(authorizeURL)
                    .append("/oauth/token?grant_type=authorization_code&code=").append(code)
                    .append("&client_id=").append(clientId)
                    .append("&redirect_uri=").append(URLEncoder.encode(redirectURI , Charsets.UTF_8.name()))
                    .append("&client_secret=").append(clientSecret);
        } catch (UnsupportedEncodingException e) {
            log.error("构造gitee登录出现错误：" , e);
        }
    }

    public String getAuthorize() {
        return urlBuilder.toString();
    }

    public String getToken(){
        return urlBuilder.toString();
    }

}
