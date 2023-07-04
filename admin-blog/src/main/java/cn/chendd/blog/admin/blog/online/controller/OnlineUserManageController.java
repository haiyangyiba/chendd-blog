package cn.chendd.blog.admin.blog.online.controller;

import cn.chendd.blog.admin.blog.online.po.OnlineManageParam;
import cn.chendd.blog.admin.blog.online.service.IOnlineManageService;
import cn.chendd.blog.admin.blog.online.vo.OnlineManageResult;
import cn.chendd.blog.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 在线人数管理
 *
 * @author chendd
 * @date 2020/7/15 21:32
 */
@Api(value = "在线人数管理" , tags = "在线人数管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(12)
@RequestMapping(value = "/blog/online/user")
@Controller
public class OnlineUserManageController extends BaseController {

    @Resource
    private IOnlineManageService onlineManageService;

    @GetMapping
    @ApiOperation(value = "在线人数主页面",notes = "跳转至在线人数管理主页面")
    @ApiOperationSupport(order = 10)
    public String tagManage() {
        return "/blog/online/onlineUserManage";
    }

    @PostMapping
    @ApiOperation(value = "在线人数查询",notes = "在线人数查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<OnlineManageResult> queryTagsPage(@ApiParam("标签查询") OnlineManageParam param) throws UnsupportedEncodingException {

        Page<OnlineManageResult> pageFinder = onlineManageService.queryOnlineManagePage(param);
        List<OnlineManageResult> list = pageFinder.getRecords();
        return pageFinder;
    }

}
