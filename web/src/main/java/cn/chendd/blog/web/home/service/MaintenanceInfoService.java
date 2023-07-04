package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;

/**
 * 系统各类参数信息Service接口定义
 * @author chendd
 * @date 2021/11/6 16:31
 */
public interface MaintenanceInfoService {

    /**
     * 获取系统各类参数信息
     * @return 参数信息
     */
    MaintenanceInfoVo queryMaintenanceInfo();

}
