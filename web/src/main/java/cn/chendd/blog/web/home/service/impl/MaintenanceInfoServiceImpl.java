package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.blog.web.home.client.MaintenanceInfoClient;
import cn.chendd.blog.web.home.service.MaintenanceInfoService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统各类参数信息Service接口实现
 *
 * @author chendd
 * @date 2021/11/6 16:33
 */
@Service
public class MaintenanceInfoServiceImpl implements MaintenanceInfoService {

    @Resource
    private MaintenanceInfoClient maintenanceInfoClient;

    @Override
    public MaintenanceInfoVo queryMaintenanceInfo() {
        BaseResult<MaintenanceInfoVo> baseResult = maintenanceInfoClient.queryMaintenanceInfo();
        return baseResult.getData();
    }
}
