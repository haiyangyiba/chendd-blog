package cn.chendd.blog.admin.blog.article.mapper;

import cn.chendd.blog.admin.blog.article.model.ArticleContent;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章内容Mapper接口定义
 *
 * @author chendd
 * @date 2020/8/2 22:11
 */
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {

    /**
     * 根据文章ID查询文章信息含文章内容
     * @param articleId 文章ID
     * @return 数据视图
     */
    ArticleSingleContentResult getArticleContent(@Param("articleId") Long articleId);

    /**
     * 根据文章ID查询文章所属标签
     * @param articleId 文章ID
     * @return 文章标签分类集合
     */
    List<ArticleSingleContentResult.ArticleTag> getArticleTags(@Param("articleId") Long articleId);

    /**
     * 根据文章ID查询文章所属分类下的上一篇和下一篇
     * @param sortOrder 文章排序编号
     * @return 上一篇和下一篇
     */
    List<ArticleSingleContentResult.ArticlePosition> getArticlePosition(@Param("sortOrder") String sortOrder , @Param("type") String type);

    /**
     * 根据文章ID查询感兴趣的文章
     * @param articleId 文章ID
     * @return 感兴趣的文章列表
     */
    List<ArticleSingleContentResult.ArticleDependent> getArticleDependent(@Param("articleId") Long articleId);
}
