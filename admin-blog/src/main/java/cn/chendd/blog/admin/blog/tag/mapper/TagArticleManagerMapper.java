package cn.chendd.blog.admin.blog.tag.mapper;

import cn.chendd.blog.admin.blog.tag.model.TagArticle;
import cn.chendd.blog.admin.blog.tag.vo.TagArticleResult;
import cn.chendd.blog.client.article.tag.TagArticleDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chendd
 * @date 2020/10/18 1:01
 */
public interface TagArticleManagerMapper extends BaseMapper<TagArticle> {

    /**
     * 根据标签查询文章结果集
     * @param page 分页参数
     * @param tagId 标签ID
     * @param title 标题
     * @return 列表分页
     */
    Page<TagArticleResult> queryTagsArticlePage(Page<TagArticleResult> page, @Param("tagId") Long tagId, @Param("title") String title);

    /**
     * 根据标签名称查询文章列表
     * @param tag 标签名称
     * @return 列表数据
     */
    List<TagArticleDto> queryStrongTagsListByName(@Param("tag") String tag);
}
