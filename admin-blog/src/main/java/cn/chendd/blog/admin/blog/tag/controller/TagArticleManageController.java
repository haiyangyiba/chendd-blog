package cn.chendd.blog.admin.blog.tag.controller;

import cn.chendd.blog.admin.blog.tag.service.ITagArticleManageServie;
import cn.chendd.blog.admin.blog.tag.vo.TagArticleResult;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.page.Query;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标签管理Controller
 *
 * @author chendd
 * @date 2020/7/15 10:37
 */
@Api(value = "博客标签管理" , tags = "博客标签管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(11)
@RequestMapping(value = "/blog/tagarticle")
@Controller
public class TagArticleManageController extends BaseController {

    @Resource
    private ITagArticleManageServie tagArticleManageServie;

    @GetMapping("/{tagId}")
    @ApiOperation(value = "跳转标签文章管理页面",notes = "跳转至标签文章管理页面")
    @ApiOperationSupport(order = 70)
    public String queryTagArticlePage(@PathVariable("tagId") @ApiParam("标签id") Long tagId) {
        super.addAttribute("tagId" , tagId);
        return "/blog/article/tagsArticleManage";
    }

    @PostMapping("/{tagId}")
    @ApiOperation(value = "标签文章管理列表查询",notes = "根据标签查询对应的文章列表分页")
    @ApiOperationSupport(order = 80)
    @ResponseBody
    public Page<TagArticleResult> queryTagsArticlePage(
            @ApiParam("分页参数") Query query,
            @PathVariable("tagId") @ApiParam("标签id") Long tagId , @ApiParam("文章标题") String title) {
        Page<TagArticleResult> pageFinder = tagArticleManageServie.queryTagsArticlePage(query , tagId , title);
        return pageFinder;
    }

    @GetMapping("/{tagId}/{articleId}")
    @ApiOperation(value = "标签文章管理列表查询",notes = "根据标签查询对应的文章列表分页")
    @ApiOperationSupport(order = 80)
    @ResponseBody
    public BaseResult saveTagsArticle(@PathVariable @ApiParam("标签id") Long tagId , @PathVariable @ApiParam("文章id") Long articleId) {
        BaseResult result = tagArticleManageServie.saveTagsArticle(tagId , articleId);
        return result;
    }

}
