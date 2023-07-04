package cn.chendd.blog.admin.blog.article.controller;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import cn.chendd.blog.admin.blog.article.enums.EnumArticleProperty;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.po.ArticleManageParam;
import cn.chendd.blog.admin.blog.article.po.ArticleParam;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.IArticlePropertyService;
import cn.chendd.blog.admin.blog.article.vo.ArticleManageResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleResult;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 博客管理-文章管理Controller接口定义
 * @author chendd
 * @date 2020/07/31 21:21
 */
@Api(value = "博客管理-文章管理" , tags = "博客管理-文章管理")
@ApiSort(10)
@RequestMapping(value = "/blog/article" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ArticleManageController extends BaseController {

    @Resource
    private ArticleManageService articleManageService;

    @GetMapping
    @ApiOperation(value = "文章管理主页面",notes = "跳转至文章管理主页面")
    @ApiOperationSupport(order = 10)
    public String articleManage() {
        super.addAttribute("states" , EnumArticleProperty.values());
        return "/blog/article/articleManage";
    }

    @PostMapping
    @ApiOperation(value = "文章管理列表查询",notes = "文章管理列表查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<ArticleManageResult> articleManage(ArticleManageParam param) {
        Page<ArticleManageResult> pageFinder = articleManageService.queryArticleManagePage(param);
        return pageFinder;
    }

    @GetMapping("/article")
    @ApiOperation(value = "博客文章新增页面",notes = "跳转至博客文章新增页面")
    @ApiOperationSupport(order = 30)
    public String article() {
        super.addAttribute("ascriptions" , ArticleAscriptionEnum.values());
        return "/blog/article/article";
    }

    @PutMapping
    @ApiOperation(value = "博客文章保存",notes = "博客文章保存")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public Article article(@ApiParam("文章保存参数对象") @RequestBody ArticleParam param) {
        if(param == null) {
            throw new ValidateDataException("非法的参数请求！");
        }
        if(StringUtils.isBlank(param.getTitle())) {
            throw new ValidateDataException("请输入文章标题！");
        }
        if(StringUtils.isBlank(param.getType())) {
            throw new ValidateDataException("请选择文章类型！");
        }
        if (StringUtils.isBlank(param.getSortOrder())) {
            throw new ValidateDataException("请输入文章排序！");
        }
        SysUserResult user = super.getCurrentUser(SysUserResult.class);
        Article article = articleManageService.saveArticle(param.convert(new Article()) , user);
        return article;
    }

    @DeleteMapping
    @ApiOperation(value = "博客文章删除",notes = "博客文章删除")
    @ApiOperationSupport(order = 50)
    public BaseResult article(@ApiParam("文章ID") @PathVariable Long id) {
        BaseResult result = articleManageService.removeArticle(id);
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "博客文章修改页面",notes = "跳转至博客文章修改页面")
    @ApiOperationSupport(order = 60)
    public String editor(@ApiParam("文章ID") @PathVariable Long id) {
        ArticleResult article = articleManageService.getArticle(id);
        super.addAttribute("article" , article);
        return this.article();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "博客文章删除",notes = "根据ID删除数据（逻辑删除）")
    @ApiOperationSupport(order = 70)
    @ResponseBody
    public BaseResult remove(@ApiParam("文章ID") @PathVariable Long id) {
        BaseResult result = articleManageService.removeArticle(id);
        return result;
    }

}
