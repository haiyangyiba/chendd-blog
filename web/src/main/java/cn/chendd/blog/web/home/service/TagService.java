package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.article.tag.TagArticleDto;

import java.util.List;

/**
 * 标签管理Service接口定义
 *
 * @author chendd
 * @date 2022/2/7 16:00
 */
public interface TagService {

    /**
     * 根据标签名称获取对应所有文章
     * @param tag 标签名称
     * @return 列表数据
     */
    List<TagArticleDto> getTagArticles(String tag);
}
