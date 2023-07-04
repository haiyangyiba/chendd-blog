package cn.chendd.third.login.custom.gitee;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取AccessToken接口映射对象
 *
 * @author chendd
 * @date 2020/1/1 17:49
 */
@Getter
@Setter
public class AccessTokenResult {

    private String accessToken;
    private Long createdAt;
    private Long expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;

}
