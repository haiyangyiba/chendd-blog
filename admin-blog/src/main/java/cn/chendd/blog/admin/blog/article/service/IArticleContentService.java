package cn.chendd.blog.admin.blog.article.service;

import cn.chendd.blog.admin.blog.article.model.ArticleContent;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章内容Service实现
 *
 * @author chendd
 * @date 2020/8/2 22:08
 */
public interface IArticleContentService extends IService<ArticleContent> {

    /**
     * 保存文章内容信息
     * @param content 内容
     * @return 操作结果
     */
    BaseResult saveArticleContent(ArticleContent content);

    /**
     * 根据文章ID查询文章数据
     * @param articleId 文章ID
     * @return 文章信息对象
     */
    ArticleSingleContentResult getArticleContent(Long articleId);
}
