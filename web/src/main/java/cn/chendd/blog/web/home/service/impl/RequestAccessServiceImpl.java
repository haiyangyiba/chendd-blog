package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.web.home.client.RequestAccessClient;
import cn.chendd.blog.web.home.service.RequestAccessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站外访问统计Service
 *
 * @author chendd
 * @date 2022/9/11 18:31
 */
@Service
public class RequestAccessServiceImpl implements RequestAccessService {

    @Resource
    private RequestAccessClient requestAccessClient;

    @Override
    public void saveRequestAccess(List<RequestItem> dataList) {
        this.requestAccessClient.saveRequestAccess(dataList);
    }
}
