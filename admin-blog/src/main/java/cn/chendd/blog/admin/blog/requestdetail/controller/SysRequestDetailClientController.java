package cn.chendd.blog.admin.blog.requestdetail.controller;

import cn.chendd.blog.admin.blog.requestdetail.service.SysRequestDetailService;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestMaxDay;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.client.access.AccessDataBo;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.Http;
import io.swagger.annotations.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统访问明细Controller
 *
 * @author chendd
 * @date 2020/7/27 19:43
 */
@Api(value = "访问管理" , tags = "访问管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(70)
@RequestMapping(value = "/blog/access")
@Controller
@ApiVersion("1")
public class SysRequestDetailClientController extends BaseController {

    @Resource
    private SysRequestDetailService sysRequestDetailService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "保存访问明细" , notes = "批量保存访问明细数据")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public void saveRequestAccess(@RequestBody List<RequestItem> dataList) {
        this.sysRequestDetailService.saveRequestAccess(dataList , Http.getBasePath(request));
    }

    @GetMapping
    @ApiModelProperty(value = "查询访问量" , notes = "查询昨日和今日的系统访问数量（今日总数、今日外链等全量数据）")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public AccessDataBo queryRequestAccess() {
        //查询昨日请求量，按一天缓存
        Date date = new Date();
        String yesterday = DateFormat.formatDate(DateUtils.addDays(date, -1));
        String today = DateFormat.formatDate(date);
        //昨日访问
        Integer yesterdayCount = this.sysRequestDetailService.queryRequestAccessYestday(yesterday);
        //今日访问
        Integer todayCount = this.sysRequestDetailService.queryRequestAccessToday(today);
        //今日外链
        Integer todayOutLinkCount = this.sysRequestDetailService.queryRequestAccessOutLinkDate(today);
        //单日最高、单日最高日期
        SysRequestMaxDay max = this.sysRequestDetailService.queryRequestAccessMaxDay();
        //总访问量
        Integer total = this.sysRequestDetailService.queryRequestAccessTotal();
        AccessDataBo bo = new AccessDataBo();
        bo.setYesterday(yesterdayCount);
        bo.setToday(todayCount);
        bo.setTodayOutLink(todayOutLinkCount);
        bo.setMaxCount(max.getMaxCount());
        bo.setMaxDay(max.getMaxDay());
        bo.setTotal(total);
        return bo;
    }

}
