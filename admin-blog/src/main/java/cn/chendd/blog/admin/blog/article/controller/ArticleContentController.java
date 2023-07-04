package cn.chendd.blog.admin.blog.article.controller;

import cn.chendd.blog.admin.blog.article.model.ArticleContent;
import cn.chendd.blog.admin.blog.article.service.IArticleContentService;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.utils.Http;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 博客管理-文章内容管理Controller接口定义
 * @author chendd
 * @date 2020/07/31 21:21
 */
@Api(value = "博客管理-文章内容管理" , tags = "博客管理-文章内容管理")
@ApiSort(20)
@RequestMapping(value = "/blog/content" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ArticleContentController extends BaseController {

    @Resource
    private IArticleContentService articleContentService;

    @GetMapping("/{articleId}")
    @ApiOperation(value = "文章内容编辑页面",notes = "跳转至文章内容编辑页面")
    @ApiOperationSupport(order = 10)
    public String articleContent(@ApiParam("文章ID") @PathVariable Long articleId) {
        ArticleContent content = articleContentService.getById(articleId);
        super.addAttribute("content" , content).addAttribute("articleId" , articleId);
        return "/blog/article/articleContent";
    }

    @PutMapping("/{articleId}")
    @ApiOperation(value = "文章内容编辑页面",notes = "跳转至文章内容编辑页面")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult articleContent(@ApiParam("文章ID") @PathVariable Long articleId , @ApiParam("文章内容") @RequestBody ArticleContent content) {
        content.setSimpleContent(Http.getHtmlInnerText(content.getEditorContent()));
        return articleContentService.saveArticleContent(content);
    }

}
