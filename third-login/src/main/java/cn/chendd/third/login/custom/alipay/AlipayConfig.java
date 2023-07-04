package cn.chendd.third.login.custom.alipay;

import lombok.Data;

/**
 * 支付宝接入登录参数配置
 *
 * @author chendd
 * @date 2021/5/20 13:42
 */
@Data
public class AlipayConfig {

    private String appId;

    private String redirectURI;

    private String authorizeURL;

    private String authAddress;

    private String appPrivateKey;

    private String alipayPublicKey;

}
