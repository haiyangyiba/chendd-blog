package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.article.tag.TagArticleDto;
import cn.chendd.blog.web.home.client.TagClient;
import cn.chendd.blog.web.home.service.TagService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签管理Service接口实现
 *
 * @author chendd
 * @date 2022/2/7 16:00
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagClient tagClient;

    @Override
    public List<TagArticleDto> getTagArticles(String tag) {
        BaseResult<List<TagArticleDto>> baseResult = tagClient.getTagArticles(tag);
        return baseResult.getData();
    }
}
