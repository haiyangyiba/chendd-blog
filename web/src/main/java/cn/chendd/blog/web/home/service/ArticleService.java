package cn.chendd.blog.web.home.service;

import cn.chendd.blog.base.page.Query;
import cn.chendd.blog.client.article.po.ArticlePraisesParam;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.web.home.client.vo.ArticleContentResult;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 博客文章Service接口定义
 *
 * @author chendd
 * @date 2020/11/14 20:57
 */
public interface ArticleService {

    /**
     * 根据文章id查询博客文章数据
     * @param articleId 文章id
     * @return 文章数据对象
     */
    ArticleSingleContentResult getArticleContent(Long articleId);

    /**
     * 根据文章id查询博客文章访问次数
     * @param articleId 文章id
     * @return 文章访问次数
     */
    BaseResult<Integer> getArticleVisitsNumber(Long articleId);

    /**
     * 根据文章id和点赞类型保存点赞数据
     * @param param 文章点赞数据
     * @return 点赞数据
     */
    BaseResult<Long> savePraises(ArticlePraisesParam param);

    /**
     * 根据文章id查询所有点赞数据详情
     * @param articleId 文章id
     * @return 用户点赞详情列表
     */
    BaseResult<List<ArticlePraisesResult>> queryPraises(Long articleId);

    /**
     * 查询文章分类
     * @param articleType 文章分类
     * @param query 分页参数
     * @return 分页数据
     */
    Page<ArticleContentResult> queryArticleTypePage(Long articleType, Query query);
}
