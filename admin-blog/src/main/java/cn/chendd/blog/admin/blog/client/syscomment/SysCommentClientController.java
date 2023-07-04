package cn.chendd.blog.admin.blog.client.syscomment;

import cn.chendd.blog.admin.blog.comment.po.SysCommentParam;
import cn.chendd.blog.admin.blog.comment.service.SysCommentService;
import cn.chendd.blog.admin.system.cache.service.CacheManageService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 留言评论表Controller接口定义
 * @author chendd
 * @date 2021/02/19 20:46
 */
@Api(value = "评论管理V1" , tags = "评论管理V1")
@ApiSort(10)
@RequestMapping(value = "/system/comment" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@ApiVersion("1")
public class SysCommentClientController extends BaseController {

    @Resource
    private SysCommentService sysCommentService;
    @Resource
    private CacheManageService cacheManageService;

    @PutMapping
    @ApiOperation(value = "数据保存" , notes = "保存评论数据，返回保存后的主键ID")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public Long saveSysComment(@ApiParam("评论对象") @RequestBody SysCommentParam param) {
        Long childId = param.getChildId();
        if (childId == null) {
            //当回复的是作者（非某一层的留言回复）时，清空该文章相关的全部分页缓存
            String keyNameStart = "querySysCommentPage_" + param.getTargetId() + "_";
            this.cacheManageService.removeCacheElementStartWith(CacheNameConstant.NOT_EXPIRED, keyNameStart);
            return this.sysCommentService.saveSysComment(param);
        } else {
            //回复的是某一条数据，则需要清空当前页的缓存
            return this.sysCommentService.saveSysCommentForChild(param);
        }
    }

    @GetMapping("/{targetId}/{pageNumber}")
    @ApiOperation(value = "数据查询" , notes = "按数据位置查询评论数据列表")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<CommentQueryResult> querySysComment(@ApiParam("数据对象ID") @PathVariable Long targetId ,
                                                          @ApiParam("分页编号") @PathVariable Long pageNumber) {
        return this.sysCommentService.querySysComment(targetId , pageNumber);
    }

    @GetMapping("/newest")
    @ApiOperation(value = "获取最新留言数据" , notes = "查询最新评论的前N条评论数据")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<CommentNewestDto> queryCommentNewestList() {
        return this.sysCommentService.queryCommentNewestList(6);
    }

}
