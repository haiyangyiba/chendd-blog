package weibo4j.util;

import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.third.login.config.ThirdLoginConfiguration;

import java.util.Map;

/**
 * 重写Weibo4j的配置文件参数读取实现
 *
 * @author chendd
 * @date 2019/12/28 20:08
 */
public class WeiboConfig {

    /**
     * 从数据库中获取Weibo4j登录的参数配置
     * @return
     */
    private static Map<String , String> getConfig(){
        ThirdLoginConfiguration thirdLoginConfiguration = SpringBeanFactory.getBean(ThirdLoginConfiguration.class);
        Map<String , String> config = thirdLoginConfiguration.getWeibo4jConfig();
        return config;
    }

    /**
     * 根据参数key获取参数信息
     */
    public static String getValue(String key) {
        return getConfig().get(key);
    }

    /**
     * 根据参数key更新参数信息
     */
    public static void updateProperties(String key, String value) {
        getConfig().put(key, value);
    }

}
