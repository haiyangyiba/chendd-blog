package cn.chendd.blog.admin.blog.requestdetail.controller;

import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDateDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.service.SysRequestDetailService;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestDetailResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.core.utils.Http;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class SysRequestDetailController extends BaseController {

    @Resource
    private SysRequestDetailService sysRequestDetailService;

    @GetMapping()
    @ApiOperation(value = "访问明细管理" , notes = "访问明细管理")
    @ApiOperationSupport(order = 10)
    public String accessManage() {
        return "/blog/access/accessManage";
    }

    @PostMapping()
    @ApiOperation(value = "访问日期汇总查询分页" , notes = "访问日期汇总查询分页")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public IPage<SysRequestDetailResult> accessManage(SysRequestDetailParam param) {
        IPage<SysRequestDetailResult> page = sysRequestDetailService.queryAccessManagePage(param);
        return page;
    }

    @GetMapping(value = "/request/{date}")
    @ApiOperation(value = "访问日期明细" , notes = "访问日期明细")
    @ApiOperationSupport(order = 30)
    public String requestManage(@ApiParam("日期") @PathVariable String date) {
        super.request.setAttribute("date" , date);
        return "/blog/access/requestManage";
    }

    @PostMapping(value = "/request/{date}")
    @ApiOperation(value = "访问日期明细查询分页" , notes = "访问日期明细查询分页")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public IPage<SysRequestDetail> requestManage(@ApiParam(name = "param" , value = "查询条件") SysRequestDateDetailParam param) {
        IPage<SysRequestDetail> page = sysRequestDetailService.queryAccessRequestManagePage(param);
        return page;
    }

}
