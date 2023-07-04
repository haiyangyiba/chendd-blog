package cn.chendd.blog.web.home.service.impl.impl;

import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.web.home.service.ArticleExportService;
import cn.chendd.blog.web.home.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章导出Service接口实现
 *
 * @author chendd
 * @date 2022/4/2 19:22
 */
@Service
public class ArticleExportServiceImpl implements ArticleExportService {

    @Resource
    private ArticleService articleService;

    @Override
    public ArticleSingleContentResult getArticleContent(Long id) {
        ArticleSingleContentResult articleContent = this.articleService.getArticleContent(id);
        return articleContent;

    }
}
