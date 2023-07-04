package cn.chendd.blog.admin.system.info.controller;

import cn.chendd.blog.admin.system.info.service.SystemInfoService;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 系统信息管理Controller
 *
 * @author chendd
 * @date 2020/5/31 9:12
 */
@Api(value = "操作系统信息" , tags = "操作系统信息管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(1)
@RequestMapping(value = "/system/info")
@Controller
public class SystemInfoController extends BaseController {

    @Resource
    private SystemInfoService systemInfoService;

    @GetMapping("/systemInfoManage")
    @ApiOperation(value = "操作系统信息-全量",notes = "获取本机操作系统硬件信息")
    @ApiOperationSupport(order = 10)
    public String getSystemInfo() {
        super.model.addAttribute("result" , systemInfoService.getSystemInfoResult());
        return "/system/info/systemInfoManage";
    }

}
