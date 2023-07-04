package com.qq.connect.utils;

import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.third.login.config.ThirdLoginConfiguration;

import java.util.Map;

/**
 * 第三方登录QQ的参数配置
 *
 * @author chendd
 * @date 2022/3/13 8:39
 */
public class QQConnectConfig {

    /**
     * 从数据库中获取腾讯QQ登录的参数配置
     * @return 参数对象
     */
    private static Map<String , String> getConfig(){
        ThirdLoginConfiguration thirdLoginConfiguration = SpringBeanFactory.getBean(ThirdLoginConfiguration.class);
        Map<String , String> config = thirdLoginConfiguration.getTencentQQConfig();
        return config;
    }

    /**
     * 获取某个参数值
     * @param key 参数名称
     * @return 参数值
     */
    public static String getValue(String key) {
        return getConfig().get(key);
    }

    /**
     * 根据参数名更新某个参数值
     * @param key 参数名
     * @param value 参数值
     */
    public static void updateProperties(String key, String value) {
        getConfig().put(key, value);
    }

}
