package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.access.AccessDataBo;
import cn.chendd.blog.web.home.client.AccessDataClient;
import cn.chendd.blog.web.home.service.AccessDataService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 访问数据Service接口实现
 *
 * @author chendd
 * @date 2023/2/27 15:45
 */
@Service
public class AccessDataServiceImpl implements AccessDataService {

    @Resource
    private AccessDataClient accessDataClient;

    @Override
    public BaseResult<AccessDataBo> getAccessData() {
        return accessDataClient.getAccessData();
    }
}
