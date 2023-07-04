package cn.chendd.blog.admin.system.client.report;

import cn.chendd.blog.admin.system.report.service.ReportChartsService;
import cn.chendd.blog.admin.system.report.vo.HomepageChartsResult;
import cn.chendd.blog.admin.system.report.vo.NameValueResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计图表管理
 *
 * @author chendd
 * @date 2020/11/14 11:21
 */
@Api(value = "首页统计图表管理V1" , tags = "首页统计图表管理V1")
@ApiSort(10)
@RequestMapping(value = "/report" , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
public class ReportChartsClientController extends BaseController {

    @Resource
    private ReportChartsService reportChartsService;

    @GetMapping("/homepage")
    @ApiOperation(value = "web首页图表查询",notes = "web首页图表查询")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public HomepageChartsResult queryHomepageCharts() {
        HomepageChartsResult result = reportChartsService.queryHomepageCharts();
        return result;
    }

}
