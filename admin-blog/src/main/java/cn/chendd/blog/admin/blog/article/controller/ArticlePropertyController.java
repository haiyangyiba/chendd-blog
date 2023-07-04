package cn.chendd.blog.admin.blog.article.controller;

import cn.chendd.blog.admin.blog.article.enums.*;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.IArticlePropertyService;
import cn.chendd.blog.admin.system.cache.service.CacheManageService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.Http;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 博客管理-文章内容管理Controller接口定义
 *
 * @author chendd
 * @date 2020/07/31 21:21
 */
@Api(value = "博客管理-文章属性管理", tags = "博客管理-文章属性管理")
@ApiSort(30)
@RequestMapping(value = "/blog/property", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ArticlePropertyController extends BaseController {

    @Resource
    private IArticlePropertyService articlePropertyService;
    @Resource
    private CacheManageService cacheManageService;
    @Resource
    private ArticleManageService articleManageService;

    @GetMapping("/{property}/{articleId}")
    @ApiOperation(value = "文章内容属性操作", notes = "文章内容属性操作")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public BaseResult propertyArticle(@ApiParam("property") @PathVariable EnumArticleProperty property, @ApiParam("文章ID") @PathVariable Long articleId) {
        //根据文章ID和操作类型具体
        return this.disposeProperty(property, articleId);
    }

    @PostMapping("/cover")
    @ApiOperation(value = "封面图片上传", notes = "封面图片上传")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult uploadArticleCover(@ApiParam("文章ID") @RequestParam Long articleId,
                                     @ApiParam("封面图片文件") @RequestParam MultipartFile file) {
        this.articlePropertyService.uploadArticleCover(articleId , file , Http.getBasePath(request));
        return new SuccessResult<>("封面图片上传成功！");
    }

    @DeleteMapping("/cover/{articleId}")
    @ApiOperation(value = "删除封面图片", notes = "删除封面图片")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult removeArticleCover(@ApiParam("文章ID") @PathVariable Long articleId) {
        this.articlePropertyService.removeArticleCover(articleId);
        return new SuccessResult<>("封面图片删除成功！");
    }

    private BaseResult disposeProperty(EnumArticleProperty property, Long articleId) {
        //当变更文章属性时，触发缓存的变更，查询文章所属的分类，更新该类所有的分页数据
        Article article = articleManageService.getById(articleId);
        cacheManageService.removeCacheElementStartWith(CacheNameConstant.NOT_EXPIRED , "queryArticleTypePage_" + article.getType() + "_");
        //更新首页缓存中文章前N也的分页数据
        cacheManageService.removeCacheElementStartWith(CacheNameConstant.NOT_EXPIRED , "index_queryArticleLimit_");
        switch (property) {
            case state:
                return articlePropertyService.changeProperty(property , articleId , EnumArticleState.publish.name() , "文章发布成功！" , "文章已取消发布！");
            case topping:
                return articlePropertyService.changeProperty(property , articleId , EnumArticleTopping.top.name() , "文章置顶成功！" , "文章已取消置顶！");
            case recommend:
                return articlePropertyService.changeProperty(property , articleId , EnumArticleRecommend.hot.name() , "文章推荐成功！" , "文章已取消推荐！");
            case comment:
                return articlePropertyService.changeProperty(property , articleId , EnumArticleComment.close.name() , "文章已关闭评论！" , "文章已开启评论！");
            default:
                break;
        }
        return null;
    }
}
