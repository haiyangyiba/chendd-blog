package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.blog.web.home.service.MaintenanceInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 文章明细页面的系统各类参数信息
 *
 * @author chendd
 * @date 2020/9/4 21:12
 */
@Controller
@RequestMapping("/blog/maintenanceInfo")
public class MaintenanceController extends BaseController {

    @Resource
    private MaintenanceInfoService maintenanceService;

    @GetMapping
    @ResponseBody
    public MaintenanceInfoVo getMaintenance() {
        //查询统计图表
        MaintenanceInfoVo maintenanceInfo = this.maintenanceService.queryMaintenanceInfo();
        return maintenanceInfo;
    }

}
