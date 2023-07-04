package cn.chendd.blog.admin.blog.comment.controller;

import cn.chendd.blog.admin.blog.comment.model.SysComment;
import cn.chendd.blog.admin.blog.comment.po.SysCommentManageParam;
import cn.chendd.blog.admin.blog.comment.service.SysCommentService;
import cn.chendd.blog.admin.blog.comment.vo.SysCommentManageResult;
import cn.chendd.blog.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统评论管理
 * @author chendd
 * @date 2021/04/12 21:28
 */
@Controller
@RequestMapping("/blog/comment")
@Api(value = "留言管理" , tags = "博客留言管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(40)
public class SysCommentController extends BaseController {

    @Resource
    private SysCommentService sysCommentService;

    @GetMapping()
    @ApiOperation(value = "留言管理主页面",notes = "留言管理主页面")
    @ApiOperationSupport(order = 10)
    public String sysCommentManage() {
        return "/blog/comment/sysCommentManage";
    }

    @PostMapping()
    @ApiOperation(value = "查询评论分页-按条件",notes = "按条件查询所有评论数据分页")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<SysCommentManageResult> querySysCommentManagePage(@ApiParam("查询条件") SysCommentManageParam param) {
        Page<SysCommentManageResult> page = sysCommentService.querySysCommentManagePage(param);
        return page;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "留言内容查看页面",notes = "查看留言内容")
    @ApiOperationSupport(order = 30)
    public String viewComment(@ApiParam("数据ID") @PathVariable Long id) {
        SysComment comment = this.sysCommentService.getCommentById(id);
        super.request.setAttribute("comment" , comment);
        return "/blog/comment/sysCommentView";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "留言内容查数据删除",notes = "根据数据ID删除某条数据")
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public Boolean removeComment(@ApiParam("数据ID") @PathVariable Long id) {
        return this.sysCommentService.removeCommentById(id);
    }

}
