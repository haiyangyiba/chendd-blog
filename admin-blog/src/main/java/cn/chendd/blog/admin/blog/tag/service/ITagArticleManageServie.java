package cn.chendd.blog.admin.blog.tag.service;

import cn.chendd.blog.admin.blog.tag.model.TagArticle;
import cn.chendd.blog.admin.blog.tag.vo.TagArticleResult;
import cn.chendd.blog.base.page.Query;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 标签文章管理关系管理
 * @author chendd
 * @date 2020/10/18 0:57
 */
public interface ITagArticleManageServie extends IService<TagArticle> {

    /**
     * 根据标签查询文章结果集
     * @param query 分页参数
     * @param tagId 标签ID
     * @param title 标题
     * @return 列表分页
     */
    Page<TagArticleResult> queryTagsArticlePage(Query query , Long tagId, String title);

    /**
     * 保存标签对应文章数据
     * @param tagId 标签id
     * @param articleId 文章id
     * @return 保存结果
     */
    BaseResult saveTagsArticle(Long tagId, Long articleId);

}
