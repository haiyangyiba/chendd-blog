package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.base.page.Query;
import cn.chendd.blog.client.article.po.ArticlePraisesParam;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.web.home.client.ArticleClient;
import cn.chendd.blog.web.home.client.vo.ArticleContentResult;
import cn.chendd.blog.web.home.service.ArticleService;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客文章Service接口实现
 *
 * @author chendd
 * @date 2020/11/14 21:12
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleClient articleClient;

    @Override
    public ArticleSingleContentResult getArticleContent(Long articleId) {
        BaseResult<ArticleSingleContentResult> result = articleClient.getArticleContent(articleId);
        return result.getData();
    }

    @Override
    public BaseResult<Integer> getArticleVisitsNumber(Long articleId) {
        BaseResult<Integer> result = articleClient.getArticleVisitsNumber(articleId);
        return result;
    }

    @Override
    public BaseResult<Long> savePraises(ArticlePraisesParam param) {
        BaseResult<Long> result = articleClient.saveArticlePraises(param);
        return result;
    }

    @Override
    public BaseResult<List<ArticlePraisesResult>> queryPraises(Long articleId) {
        return articleClient.queryPraises(articleId);
    }

    @Override
    public Page<ArticleContentResult> queryArticleTypePage(Long articleType, Query query) {
        BaseResult<Page<ArticleContentResult>> result = articleClient.queryArticleTypePage(articleType ,
                query.getPageNumber() , query.getPageSize());
        return result.getData();
    }
}
