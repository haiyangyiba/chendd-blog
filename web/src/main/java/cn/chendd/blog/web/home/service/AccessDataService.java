package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.access.AccessDataBo;
import cn.chendd.core.result.BaseResult;

/**
 * 访问数据Service接口定义
 *
 * @author chendd
 * @date 2023/2/27 15:45
 */
public interface AccessDataService {

    /**
     * 获取系统访问量数据
     * @return 访问量
     */
    BaseResult<AccessDataBo> getAccessData();
}
