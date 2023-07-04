package cn.chendd.blog.admin.system.client.sysinfo;

import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.report.service.ReportChartsService;
import cn.chendd.blog.admin.system.report.vo.HomepageChartsResult;
import cn.chendd.blog.admin.system.report.vo.NameValueResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.spring.component.SystemParamComponent;
import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.toolkit.dbproperty.commonents.StartupApplicationListener;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 各项统计信息Controller
 *
 * @author chendd
 * @date 2021/11/6 18:19
 */
@Api(value = "系统统计管理" , tags = "系统统计管理")
@ApiSort(10)
@RequestMapping(value = "/system/maintenanceInfo" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@ApiVersion("1")
public class MaintenanceInfoClientController extends BaseController {

    @Resource
    private ReportChartsService reportChartsService;
    @Resource
    private SystemParamComponent systemParam;
    @Resource
    private SysInfoContentService sysInfoContentService;

    /**
     * 获取系统内置的统计信息对象
     * @return 统计信息
     */
    @SneakyThrows
    @GetMapping
    @ResponseBody
    @ApiOperation(value = "获取统计对象" , notes = "获取统计对象" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public MaintenanceInfoVo queryMainternanceInfo() {
        HomepageChartsResult chartsResult = this.reportChartsService.queryHomepageCharts();
        MaintenanceInfoVo info = this.constructorMainternanceInfo(chartsResult);
        this.fillArticles(info);
        return info;
    }

    /**
     * 填充文章信息：点赞最多、评论最多
     * @param result 数据对象
     */
    private void fillArticles(MaintenanceInfoVo result) {
        result.setPariseList(this.sysInfoContentService.queryArticleForParise());
        result.setCommentList(this.sysInfoContentService.queryArticleForComment());

    }

    /**
     * 构造
     * @param chartsResult 图表统计对象
     */
    private MaintenanceInfoVo constructorMainternanceInfo(HomepageChartsResult chartsResult) throws ParseException {
        MaintenanceInfoVo info = new MaintenanceInfoVo();
        //设置数量参数
        List<NameValueResult> chartAmountList = chartsResult.getAmountNumbers();
        chartAmountList.forEach(item -> {
            String name = item.getName();
            Integer value = Integer.parseInt(item.getValue());
            if ("用户数量".equals(name)) {
                info.setUsers(value);
            } else if ("文章数量".equals(name)) {
                info.setArticles(value);
            } else if ("评论数量".equals(name)) {
                info.setComments(value);
            } else if ("点赞数量".equals(name)) {
                info.setPraises(value);
            }
        });
        //按年份统计
        List<NameValueResult> chartYearList = chartsResult.getArticleYears();
        List<String> yearList = Lists.newArrayList();
        for (NameValueResult result : chartYearList) {
            yearList.add(String.format("%s年%s篇" , result.getName() , result.getValue()));
        }
        //文章按年份发布的数量
        info.setYears(yearList);
        //推荐标签
        List<NameValueResult> chartTagName = chartsResult.getTagNames();
        List<String> tagList = Lists.newArrayList();
        for (NameValueResult result : chartTagName) {
            tagList.add(result.getName());
        }
        info.setTags(tagList);
        //建站时间
        String createSiteTimeValue = systemParam.getCreateSiteTime();
        Date createSiteTime = DateUtils.parseDate(createSiteTimeValue, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
        LocalDate beginDate = createSiteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        info.setCreateSiteTime((int) ChronoUnit.DAYS.between(beginDate , LocalDate.now()));
        //服务器启动时的系统时间
        info.setLastUpdateTime(StartupApplicationListener.getStartupServerTime());
        return info;
    }

}
