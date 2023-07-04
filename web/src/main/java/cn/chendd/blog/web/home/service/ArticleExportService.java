package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;

/**
 * 文章导出Service接口定义
 *
 * @author chendd
 * @date 2022/4/2 19:22
 */
public interface ArticleExportService {

    /**
     * 根据文章id导出pdf
     * @param id id数据
     * @return 文章原始内容
     */
    ArticleSingleContentResult getArticleContent(Long id);
}
