package cn.chendd.third.login.custom.alipay;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 接入alipay登录实现
 *
 * @author chendd
 * @date 2021/5/20 14:29
 */
public class AlipayOauth {

    private StringBuilder urlBuilder;

    /**
     * 构造Authorize地质
     * @param alipayConfig 参数配置
     * @param link 友链回调地址
     */
    public AlipayOauth(AlipayConfig alipayConfig , String link) {
        urlBuilder = new StringBuilder();
        try {
            urlBuilder
                    .append(alipayConfig.getAuthorizeURL())
                    .append("?app_id=")
                    .append(alipayConfig.getAppId())
                    .append("&scope=auth_user&redirect_uri=")
                    .append(URLEncoder.encode(alipayConfig.getRedirectURI(), Charsets.UTF_8.name()));
            if(StringUtils.isNotBlank(link)){
                urlBuilder.append("&state=").append(link);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAuthorize() {
        return urlBuilder.toString();
    }

}
