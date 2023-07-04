package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.client.system.SystemInfoResult;

/**
 * 内容管理Service接口定义
 * @author chendd
 * @date 2021/06/20 20:21
 */
public interface InfoContentService {

    /**
     * 根据key获取项目介绍数据对象
     * @param key key标识
     * @return 单条数据
     */
    SysInfoProjectResult getProjectInfo(String key);

    /**
     * 获取系统信息：环境及硬件信息
     * @return 对象
     */
    SystemInfoResult getSystemInfo();
}
