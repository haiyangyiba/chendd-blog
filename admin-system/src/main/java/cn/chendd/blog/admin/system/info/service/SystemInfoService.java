package cn.chendd.blog.admin.system.info.service;

import cn.chendd.blog.client.system.SystemInfoResult;

/**
 * 系统信息 Service 接口定义
 *
 * @author chendd
 * @date 2020/5/30 22:03
 */
public interface SystemInfoService {

    /**
     * 获取系统信息
     * @return 系统信息
     */
    SystemInfoResult getSystemInfoResult();

    /**
     * 设置系统信息
     */
    void setSystemInfoResult();
}
