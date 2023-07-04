package cn.chendd.blog.admin.blog.client.article;

import cn.chendd.blog.admin.blog.article.enums.EnumArticlePraises;
import cn.chendd.blog.admin.blog.article.model.ArticlePraises;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.ArticlePraisesService;
import cn.chendd.blog.admin.blog.article.service.IArticleContentService;
import cn.chendd.blog.admin.blog.article.vo.ArticleContentResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.page.Query;
import cn.chendd.blog.client.article.po.ArticlePraisesParam;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章交互接口
 *
 * @author chendd
 * @date 2020/11/14 11:10
 */
@Api(value = "博客文章管理V1" , tags = "博客文章管理V1")
@ApiSort(20)
@RequestMapping(value = "/blog/article" , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
public class ArticleClientController extends BaseController {

    @Resource
    private IArticleContentService articleContentService;
    @Resource
    private ArticleManageService articleManageService;
    @Resource
    private ArticlePraisesService articlePraisesService;

    @GetMapping(value = "/page/{pageNumber}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "博客分页查询", notes = "按页码查询指定页数据")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public List<ArticleContentResult> queryArticleLimit(@ApiParam("页码") @PathVariable Integer pageNumber) {
        //按发布时间查询最新N条数据
        List<ArticleContentResult> dataList = articleManageService.queryArticleLimit(pageNumber);
        return dataList;
    }

    @PostMapping(value = "/type/{articleType}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "博客分类查询分页", notes = "按分类ID查询指定类型的数据")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<ArticleContentResult> queryArticleTypePage(@ApiParam("页码") @PathVariable Long articleType , @ApiParam("分页参数") Query query) {
        //按发布时间查询最新N条数据
        Page<ArticleContentResult> pageFinder = articleManageService.queryArticleTypePage(articleType , query.getPageNumber() , query.getPageSize());
        return pageFinder;
    }

    @GetMapping("/{articleId}")
    @ApiOperation(value = "文章内容查看",notes = "根据文章ID查询文章数据包含文章标签、上一篇/下一篇等各种信息")
    @ApiOperationSupport(order = 30)
    public ArticleSingleContentResult getArticleContent(@ApiParam("文章ID") @PathVariable Long articleId) {
        ArticleSingleContentResult view = articleContentService.getArticleContent(articleId);
        return view;
    }

    @GetMapping("/visits/{articleId}")
    @ApiOperation(value = "文章访问次数",notes = "查询文章访问次数")
    @ApiOperationSupport(order = 30)
    public Integer queryArticleVisitsNumber(@ApiParam("文章ID") @PathVariable Long articleId) {
        return articleManageService.queryAndModifArticleVisitsNumber(articleId);
    }

    @PutMapping(value = "/praises")
    @ApiOperation(value = "文章点赞",notes = "保存文章点赞")
    @ApiOperationSupport(order = 70)
    public Long praises(@ApiParam("文章点赞对象") @RequestBody ArticlePraisesParam param) {
        ArticlePraises praises = new ArticlePraises();
        BeanUtils.copyProperties(param , praises);
        praises.setDataType(EnumArticlePraises.valueOf(param.getDataType()));
        Long result = articlePraisesService.saveArticlePraises(praises);
        return result;
    }

    @GetMapping(value = "/praises/{articleId}" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "文章点赞",notes = "查询文章点赞明细")
    @ApiOperationSupport(order = 70)
    @ResponseBody
    public List<ArticlePraisesResult> praises(@ApiParam("文章ID") @PathVariable Long articleId) {
        List<ArticlePraisesResult> result = articlePraisesService.queryArticlePraisesResult(articleId);
        return result;
    }

}
