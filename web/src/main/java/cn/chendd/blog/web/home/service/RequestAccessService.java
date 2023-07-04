package cn.chendd.blog.web.home.service;

import cn.chendd.blog.base.filters.detail.RequestItem;

import java.util.List;

/**
 * 站外访问统计Service
 *
 * @author chendd
 * @date 2022/9/11 18:28
 */
public interface RequestAccessService {

    /**
     * 保存请求数据
     * @param dataList 集合
     */
    void saveRequestAccess(List<RequestItem> dataList);
}
