package cn.chendd.third.login.custom.github;

import lombok.Data;

/**
 * Github的登录接入参数
 *
 * @author chendd
 * @date 2020/1/4 22:41
 */
@Data
public class GithubConfig {

    private String authorizeURL;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    private String userApiURL;

}
