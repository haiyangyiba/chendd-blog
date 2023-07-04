package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.page.Query;
import cn.chendd.blog.client.article.po.ArticlePraisesParam;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.web.home.client.vo.ArticleContentResult;
import cn.chendd.blog.web.home.service.ArticleService;
import cn.chendd.blog.web.home.service.IndexService;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * BaseController
 *
 * @author chendd
 * @date 2020/9/4 21:12
 */
@Api(value = "文章管理" , tags = "文章管理")
@ApiSort(10)
@Controller
@RequestMapping("/blog/article")
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;
    @Resource
    private IndexService indexService;

    @GetMapping("/{articleId}")
    public String index(@PathVariable Long articleId) {
        ArticleSingleContentResult article = articleService.getArticleContent(articleId);
        super.addAttribute("article" , article);
        return "/article/article";
    }

    @GetMapping("/visits/{articleId}")
    @ResponseBody
    public BaseResult<Integer> visitsNumber(@PathVariable Long articleId) {
        return articleService.getArticleVisitsNumber(articleId);
    }

    @PutMapping("/praises")
    @ResponseBody
    public BaseResult<Long> savePraises(@RequestBody ArticlePraisesParam param) {
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        param.setCreateUserId(userResult.getUser().getUserId());
        param.setCreateUserName(userResult.getUser().getRealName());
        return articleService.savePraises(param);
    }

    @GetMapping("/praises/{articleId}")
    @ResponseBody
    public BaseResult<List<ArticlePraisesResult>> queryPraises(@PathVariable Long articleId) {

        return articleService.queryPraises(articleId);
    }

    @PostMapping("/{pageNumber}")
    @ResponseBody
    public List<ArticleContentResult> query(@PathVariable Integer pageNumber) {
        //默认查询第一页博客数据
        List<ArticleContentResult> articleList = this.indexService.queryArticleContents(pageNumber);
        return articleList;
    }

    @GetMapping("/type/{articleType}")
    public String queryArticleTypePage(@PathVariable("articleType") Long articleType , Query query) {
        Page<ArticleContentResult> pageFinder = this.articleService.queryArticleTypePage(articleType, query);
        super.addAttribute("pageFinder" , pageFinder);
        return "/article/articles";
    }

}
