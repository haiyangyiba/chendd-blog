package cn.chendd.blog.admin.system.pool.service;

import cn.chendd.blog.admin.system.pool.bo.DataSourceBo;

/**
 * 数据库管理Service接口定义
 *
 * @author chendd
 * @date 2021/11/20 21:00
 */
public interface DataSourceService {

    /**
     * 数据应参数业务对象
     * @return 获取数据应参数配置
     */
    DataSourceBo getDataSourceConfig();
}
