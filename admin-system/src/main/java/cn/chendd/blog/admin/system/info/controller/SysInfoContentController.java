package cn.chendd.blog.admin.system.info.controller;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.po.SysInfoContentManageParam;
import cn.chendd.blog.admin.system.info.po.SysInfoContentParam;
import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.info.vo.SysInfoContentManageResult;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统内容信息表Controller接口定义
 * @author chendd
 * @date 2020/08/29 11:46
 */
@Api(value = "系统内容管理" , tags = "系统内容管理")
@ApiSort(10)
@RequestMapping(value = "/system/infocontent" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysInfoContentController extends BaseController {

    @Resource
    private SysInfoContentService sysInfoContentService;

    @GetMapping("/manage")
    @ApiOperation(value = "内容管理主页面",notes = "内容管理主页面")
    @ApiOperationSupport(order = 10)
    public String sysInfoContentManage() {
        return "/system/info/sysInfoContentManage";
    }

    @PostMapping
    @ApiOperation(value = "查询菜单-按条件",notes = "按条件查询所有角色数据数据")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<SysInfoContentManageResult> queryInfoContentList(@ApiParam("系统内容列表查询条件") SysInfoContentManageParam param) {
        List<SysInfoContentManageResult> dataList = sysInfoContentService.queryInfoContentList(param);
        return dataList;
    }

    @GetMapping("/content")
    @ApiOperation(value = "内容管理新增修改页面",notes = "内容管理新增修改页面")
    @ApiOperationSupport(order = 30)
    public String sysInfoContent() {
        return "/system/info/sysInfoContent";
    }

    @PutMapping
    @ApiOperation(value = "内容管理新增修改数据保存",notes = "内容管理新增修改数据保存")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public Integer sysInfoContent(@ApiParam("系统内容保存对象") @RequestBody SysInfoContentParam param) {
        Integer result = sysInfoContentService.saveSysInfoContent(param.convert(new SysInfoContent()));
        return result;
    }

    @GetMapping("/content/{id}")
    @ApiOperation(value = "内容管理ID页面",notes = "内容管理ID页面")
    @ApiOperationSupport(order = 50)
    public String sysInfoContent(@ApiParam("主键ID") @PathVariable Long id) {
        SysInfoContent sysInfoContent = sysInfoContentService.getById(id);
        super.addAttribute("sysInfo" , sysInfoContent);
        return this.sysInfoContent();
    }

    @DeleteMapping("/content/{id}")
    @ApiOperation(value = "内容管理删除",notes = "内容管理根据ID删除数据")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public Integer removeInfoContent(@ApiParam("主键ID") @PathVariable Long id) {
        Integer result = sysInfoContentService.removeInfoContent(id);
        return result;
    }

    @GetMapping("/view/{id}")
    @ApiOperation(value = "内容数据查看",notes = "内容管理明细数据查看")
    @ApiOperationSupport(order = 50)
    public String viewSysInfoContent(@ApiParam("主键ID") @PathVariable Long id) {
        SysInfoContent sysInfoContent = sysInfoContentService.viewSysInfoContent(id);
        super.addAttribute("sysInfo" , sysInfoContent);
        return "/system/info/sysInfoContentView";
    }

}
