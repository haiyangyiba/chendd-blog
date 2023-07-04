package cn.chendd.toolkit.quartz.controller;

import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.quartz.enums.EnumQuartzOperation;
import cn.chendd.toolkit.quartz.po.QuartzManageParam;
import cn.chendd.toolkit.quartz.po.QuartzParam;
import cn.chendd.toolkit.quartz.service.QuartzManageService;
import io.swagger.annotations.*;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Quartz管理Controller
 *
 * @author chendd
 * @date 2020/7/4 22:39
 */
@Api(value = "定时任务管理" , tags = "定时任务管理")
@ApiSort(70)
@RequestMapping(value = "/toolkit/quartz" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class QuartzManageController {

    @Resource
    private QuartzManageService quartzManageService;

    @GetMapping
    @ApiOperation(value = "定时任务管理主页面",notes = "跳转至定时任务管理主页面")
    @ApiOperationSupport(order = 10)
    public String quartzManage() {
        return "/toolkit/quartz/quartzManage";
    }

    @PostMapping
    @ApiOperation(value = "定时任务管理查询列表",notes = "定时任务管理查询列表")
    @ResponseBody
    @ApiOperationSupport(order = 20)
    public BaseResult queryQuartzList(@ApiParam QuartzManageParam param) {
        //不确定使用原生mybatis对于枚举类的支持
        BaseResult baseResult = quartzManageService.queryQuartzList(param);
        return baseResult;
    }

    @GetMapping("/quartz")
    @ApiOperation(value = "定时任务新增前",notes = "跳转至定时任务新增页面")
    @ApiOperationSupport(order = 30)
    public String quartz() {
        return "/toolkit/quartz/quartz";
    }

    @PutMapping
    @ApiOperation(value = "保存定时任务",notes = "保存定时任务")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public BaseResult quartz(@ApiParam("数据对象") @RequestBody QuartzParam param) throws SchedulerException {
        this.validatorQuartzParam(param);
        BaseResult baseResult = quartzManageService.saveQuartz(param);
        return baseResult;
    }

    @GetMapping("/{jobGroup}/{jobName}")
    @ApiOperation(value = "定时任务修改前",notes = "跳转至定时任务修改页面")
    @ApiOperationSupport(order = 50)
    public String quartz(@ApiParam("任务组名") @PathVariable String jobGroup ,
                         @ApiParam("任务名称") @PathVariable String jobName , HttpServletRequest request) throws SchedulerException, UnsupportedEncodingException {
        jobGroup = URLDecoder.decode(jobGroup , CharsetNames.UTF_8);
        jobName = URLDecoder.decode(jobName , CharsetNames.UTF_8);
        BaseResult result = quartzManageService.getQuartz(jobGroup , jobName);
        if(result instanceof SuccessResult) {
            request.setAttribute("quartz", result.getData());
        }
        return "/toolkit/quartz/quartz";
    }

    @DeleteMapping("/{jobGroup}/{jobName}")
    @ApiOperation(value = "定时任务删除",notes = "定时任务删除")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public BaseResult removeQuartz(@ApiParam("任务组名") @PathVariable String jobGroup ,
                                   @ApiParam("任务名称") @PathVariable String jobName) throws SchedulerException {
        BaseResult baseResult = quartzManageService.removeQuartz(jobGroup , jobName);
        return baseResult;
    }

    @PostMapping("/{operation}")
    @ApiOperation(value = "定时任务功能操作",notes = "定时任务功能操作")
    @ApiOperationSupport(order = 70)
    @ResponseBody
    public BaseResult operationQuartz(@ApiParam("功能操作") @PathVariable EnumQuartzOperation operation , @ApiParam("任务组名") String jobGroup , @ApiParam("任务名称") String jobName) throws SchedulerException {
        BaseResult baseResult = quartzManageService.operationQuartz(operation , jobGroup , jobName);
        return baseResult;
    }

    /**
     * 验证参数
     */
    private void validatorQuartzParam(QuartzParam param) {
        if(StringUtils.isBlank(param.getJobGroup())) {
            throw new ValidateDataException("任务组名不能为空！");
        }
        if(StringUtils.isBlank(param.getJobName())) {
            throw new ValidateDataException("任务名称不能为空！");
        }
        if(StringUtils.isBlank(param.getJobDescription())) {
            throw new ValidateDataException("任务描述不能为空！");
        }
        if(StringUtils.isBlank(param.getJobClassName())) {
            throw new ValidateDataException("任务实现类不能为空！");
        }
        if(StringUtils.isBlank(param.getCronExpression())) {
            throw new ValidateDataException("任务表达式不能为空！");
        }
        if(StringUtils.isBlank(param.getTriggerDescription())) {
            throw new ValidateDataException("表达式说明不能为空！");
        }
    }

}
