package cn.chendd.toolkit.operationlog.controller;

import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.po.SysOperationLogParam;
import cn.chendd.toolkit.operationlog.service.SysOperationLogService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统操作日志管理
 *
 * @author chendd
 * @date 2020/7/13 11:45
 */
@Api(value = "操作日志管理" , tags = "操作日志管理")
@ApiSort(80)
@RequestMapping(value = "/toolkit/operationLog" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysOperationController {

    @Resource
    private SysOperationLogService sysOperationLogService;

    @GetMapping
    @ApiOperation(value = "操作日志管理主页面",notes = "跳转至操作日志管理主页面")
    @ApiOperationSupport(order = 10)
    public String quartzManage(HttpServletRequest request) {

        return "/toolkit/operationLog/operationLogManage";
    }

    @PostMapping
    @ApiOperation(value = "操作日志查询列表",notes = "操作日志查询列表")
    @ResponseBody
    @ApiOperationSupport(order = 20)
    public BaseResult querySysOperationPage(@ApiParam SysOperationLogParam param) {
        String date = param.getDate();
        String currentDate = DateFormat.formatDate();
        if(currentDate.compareTo(date) < 0) {
            throw new ValidateDataException(String.format("查询日期 %s 不能大于当前日期！" , date));
        }
        BaseResult baseResult = sysOperationLogService.querySysOperationPage(param);
        return baseResult;
    }

    @GetMapping("/{date}/{operId}")
    @ApiOperation(value = "操作日志管理主页面",notes = "跳转至操作日志管理主页面")
    @ApiOperationSupport(order = 10)
    public String view(@ApiParam("日期") @PathVariable String date ,
                       @ApiParam("操作日志ID") @PathVariable Long operId , HttpServletRequest request) {
        String result = sysOperationLogService.querySysOperationContent(date , operId);
        request.setAttribute("result" , result);
        return "/toolkit/operationLog/view";
    }

}
