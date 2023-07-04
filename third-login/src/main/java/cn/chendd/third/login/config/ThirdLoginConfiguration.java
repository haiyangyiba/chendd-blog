package cn.chendd.third.login.config;

import cn.chendd.third.login.custom.alipay.AlipayConfig;
import cn.chendd.third.login.custom.github.GithubConfig;
import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 新浪微博的参数配置类
 *
 * @author chendd
 * @date 2019/12/28 20:13
 */
@Data
@DbValueConfiguration
@Component
public class ThirdLoginConfiguration {

    /**
     * weibo4j登录参数配置
     */
    @DbValue(group = "third.login.weibo4j")
    private Map<String , String> weibo4jConfig;

    /**
     * baidu登录参数配置
     */
    @DbValue(group = "third.login.baidu")
    private Map<String , String> baiduConfig;

    /**
     * gitee登录参数配置
     */
    @DbValue(group = "third.login.gitee")
    private Map<String , String> giteeConfig;

    /**
     * Github参数配置
     */
    @DbValue(group = "third.login.github")
    private GithubConfig githubConfig;

    /**
     * 支付宝登录参数配置
     */
    @DbValue(group = "third.login.alipay")
    private AlipayConfig alipayConfig;
    /**
     * 腾讯QQ登录参数配置
     */
    @DbValue(group = "third.login.tencentQQ")
    private Map<String , String> tencentQQConfig;

    /**
     * 我的友链好友登录接入
     */
    @DbValue(group = "third.login.friends")
    private Map<String , String> friends;


}
