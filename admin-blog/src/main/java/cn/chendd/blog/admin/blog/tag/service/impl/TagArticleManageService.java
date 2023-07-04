package cn.chendd.blog.admin.blog.tag.service.impl;

import cn.chendd.blog.admin.blog.tag.mapper.TagArticleManagerMapper;
import cn.chendd.blog.admin.blog.tag.model.TagArticle;
import cn.chendd.blog.admin.blog.tag.service.ITagArticleManageServie;
import cn.chendd.blog.admin.blog.tag.vo.TagArticleResult;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.page.Query;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签文章关联管理
 *
 * @author chendd
 * @date 2020/10/18 0:59
 */
@Service
public class TagArticleManageService extends ServiceImpl<TagArticleManagerMapper , TagArticle> implements ITagArticleManageServie {

    @Resource
    private TagArticleManagerMapper tagArticleManagerMapper;

    @Override
    @Log(description = "查询标签关联文章列表")
    public Page<TagArticleResult> queryTagsArticlePage(Query query , Long tagId, String title) {
        Page<TagArticleResult> page = new Page<>(query.getPageNumber() , query.getPageSize());
        Page<TagArticleResult> pageFinder = this.tagArticleManagerMapper.queryTagsArticlePage(page , tagId , title);
        return pageFinder;
    }

    @Override
    @Log(description = "设置标签关联文章" , scope = LogScopeEnum.ALL)
    @CacheEvict(allEntries = true, cacheNames = CacheNameConstant.TAG_CACHE)
    public BaseResult saveTagsArticle(Long tagId, Long articleId) {
        QueryWrapper<TagArticle> queryWrapper = new QueryWrapper<TagArticle>().eq("tagId", tagId).eq("articleId", articleId);
        Integer count = this.tagArticleManagerMapper.selectCount(queryWrapper);
        //当count == 0 表示新增标签与文章数据，否则为删除
        if(count == 0) {
            TagArticle tagsArticle = new TagArticle();
            tagsArticle.setTagId(tagId);
            tagsArticle.setArticleId(articleId);
            this.tagArticleManagerMapper.insert(tagsArticle);
            return new SuccessResult<>("标签文章关联成功!");
        } else {
            this.tagArticleManagerMapper.delete(queryWrapper);
            return new SuccessResult<>("已取消标签文章的关联!");
        }
    }

}
