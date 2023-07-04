package cn.chendd.toolkit.quartz.controller;

import cn.chendd.core.result.BaseResult;
import cn.chendd.toolkit.quartz.po.QuartzJobExecuteParam;
import cn.chendd.toolkit.quartz.service.QuartzJobExecuteService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 定时任务执行记录
 *
 * @author chendd
 * @date 2020/7/9 20:55
 */
@Api(value = "定时任务执行明细" , tags = "定时任务执行明细")
@ApiSort(80)
@RequestMapping(value = "/toolkit/quartzJobExecute" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class QuartzJobExecuteController {

    @Resource
    private QuartzJobExecuteService quartzExecuteService;

    @GetMapping
    @ApiOperation(value = "定时任务执行明细主页",notes = "跳转至定时定时任务执行明细页面")
    @ApiOperationSupport(order = 10)
    public String quartzExecuteManage() {
        return "/toolkit/quartz/quartzJobExecuteManage";
    }

    @PostMapping
    @ApiOperation(value = "定时任务执行明细查询列表",notes = "定时任务执行明细查询列表")
    @ResponseBody
    @ApiOperationSupport(order = 20)
    public BaseResult queryQuartzJobExecuteList(@ApiParam QuartzJobExecuteParam param) {
        //不确定使用原生mybatis对于枚举类的支持
        BaseResult baseResult = quartzExecuteService.queryQuartzJobExecuteList(param);
        return baseResult;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查看任务执行结果",notes = "查看任务执行结果")
    @ApiOperationSupport(order = 30)
    public String viewQuartzResult(@ApiParam("id") @PathVariable Long id , HttpServletRequest request) {
        String result = quartzExecuteService.getResultById(id);
        request.setAttribute("result" , result);
        return "/toolkit/quartz/view";
    }

}
