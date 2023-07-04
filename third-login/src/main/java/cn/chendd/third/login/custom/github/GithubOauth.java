package cn.chendd.third.login.custom.github;

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
public class GithubOauth {

    private StringBuilder urlBuilder;

    public GithubOauth(GithubConfig config, String link) {
        urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(config.getAuthorizeURL())
                    .append("/authorize")
                    .append("?client_id=").append(config.getClientId())
                    .append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri() , Charsets.UTF_8.name()));
            if(StringUtils.isNotBlank(link)){
                urlBuilder.append("&state=").append(link);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("构造github登录出现错误：" , e);
        }
    }

    public String getAuthorize() {
        return urlBuilder.toString();
    }
}
