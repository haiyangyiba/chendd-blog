package cn.chendd.blog.admin.blog.tag.service.impl;

import cn.chendd.blog.admin.blog.tag.mapper.TagArticleManagerMapper;
import cn.chendd.blog.admin.blog.tag.mapper.TagManageMapper;
import cn.chendd.blog.admin.blog.tag.model.Tag;
import cn.chendd.blog.admin.blog.tag.model.TagArticle;
import cn.chendd.blog.admin.blog.tag.po.TagManageParam;
import cn.chendd.blog.admin.blog.tag.po.TagParam;
import cn.chendd.blog.admin.blog.tag.service.TagManageService;
import cn.chendd.blog.admin.blog.tag.vo.TagManageResult;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.enums.EnumWhether;
import cn.chendd.blog.client.article.tag.TagArticleDto;
import cn.chendd.core.exceptions.TipsException;
import cn.chendd.core.utils.RandomUtil;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签管理Service实现
 *
 * @author chendd
 * @date 2020/7/15 10:53
 */
@Service
public class TagManageServiceImpl extends ServiceImpl<TagManageMapper , Tag> implements TagManageService {

    @Resource
    private TagManageMapper tagManageMapper;
    @Resource
    private TagArticleManagerMapper tagArticleManagerMapper;

    @Override
    @Log(description = "标签管理-查询" , scope = LogScopeEnum.ALL)
    public Page<TagManageResult> queryTagManagePage(TagManageParam param) {
        Page page = new Page(param.getPageNumber() , param.getPageSize());
        return tagManageMapper.queryTagManagePage(page, param);
    }

    @Override
    @Log(description = "'标签管理：' + (#param.id == null ? '新增' : '修改')" , scope = LogScopeEnum.ALL)
    @CacheEvict(key = "'queryStrongTagsList'" , allEntries = false , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public Long saveTag(TagParam param) {
        Tag tag = param.convert(new Tag());
        super.saveOrUpdate(tag);
        return tag.getId();
    }

    @Override
    @Log(description = "标签管理-删除" , scope = LogScopeEnum.ALL)
    @CacheEvict(key = "'queryStrongTagsList'" , allEntries = false, cacheNames = CacheNameConstant.NOT_EXPIRED)
    public void removeTag(Long id) {
        QueryWrapper<TagArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tagId", id);
        Integer count = this.tagArticleManagerMapper.selectCount(queryWrapper);
        if(count > 0) {
            throw new TipsException("当前Tag关联有 %d 篇文章，不允许删除" , count);
        }
        super.removeById(id);
    }

    @Override
    @Cacheable(key = "'queryStrongTagsList'" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public List<TagManageResult> queryStrongTagsList() {
        Page<Object> page = new Page<>(1 , 100);
        TagManageParam param = new TagManageParam();
        param.setStrong(EnumWhether.yes);
        Page<TagManageResult> tagManageResultPage = this.tagManageMapper.queryTagManagePage(page, param);
        List<TagManageResult> dataList = tagManageResultPage.getRecords();
        for (TagManageResult result : dataList) {
            String color = RandomUtil.getRandomColor();
            String fontSize = RandomUtil.getRandomFontSize();
            String style = String.format("color:%s;font-size:%s;" , color , fontSize);
            result.setStyle(style);
        }
        return dataList;
    }

    @Override
    @Cacheable(key = "'queryStrongTagsListByName_' + #tag" , cacheNames = CacheNameConstant.TAG_CACHE)
    public List<TagArticleDto> queryStrongTagsListByName(String tag) {
        List<TagArticleDto> dataList = this.tagArticleManagerMapper.queryStrongTagsListByName(tag);
        return dataList;
    }
}
