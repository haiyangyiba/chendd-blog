package cn.chendd.blog.toolkit;


import cn.chendd.blog.Bootstrap;
import cn.chendd.third.login.config.ThirdLoginConfiguration;
import cn.chendd.third.login.custom.github.GithubConfig;
import cn.chendd.toolkit.dbproperty.commonents.SysDbValueMapping;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 测试Get方法Aop
 *
 * @author chendd
 * @date 2020/7/14 20:56
 */
public class GetTest extends Bootstrap {

    @Resource
    private SysDbValueMapping mapping;

    @Resource
    private ThirdLoginConfiguration loginConfig;

    @Test
    public void test() {
//        String serverName = mapping.getHttpServerName();
//        System.out.println("serverName值：" + serverName);
//        Map<String, String> giteeConfig = loginConfig.getGiteeConfig();
//        System.out.println("giteeConfig值：" + giteeConfig);
        GithubConfig githubConfig = loginConfig.getGithubConfig();
        System.out.println("githubConfig的值：" + githubConfig);
        System.out.println(githubConfig.getAuthorizeURL() + "---url");
        System.out.println(githubConfig.getRedirectUri() + "---uri");
    }

}
