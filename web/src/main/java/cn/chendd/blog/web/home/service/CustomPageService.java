package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.custompage.CustomPageBo;

import java.util.Map;

/**
 * 自定义内容页面Service接口定义
 *
 * @author chendd
 * @date 2022/2/11 22:33
 */
public interface CustomPageService {

    /**
     * 根据页面标识获取自定义页面内容
     * @param page 页面标识
     * @return 页面对象
     */
    Map<String , Object> getCustomPage(String page);

}
