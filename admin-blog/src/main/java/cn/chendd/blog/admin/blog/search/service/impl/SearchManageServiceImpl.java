package cn.chendd.blog.admin.blog.search.service.impl;

import cn.chendd.blog.admin.blog.article.mapper.ArticleManageMapper;
import cn.chendd.blog.admin.blog.article.vo.ArticleManageResult;
import cn.chendd.blog.admin.blog.search.service.SearchManageService;
import cn.chendd.blog.client.search.SearchVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索管理Service接口实现
 *
 * @author chendd
 * @date 2022/2/19 17:06
 */
@Service
public class SearchManageServiceImpl implements SearchManageService {

    @Resource
    private ArticleManageMapper articleManageMapper;

    @Override
    public Page<SearchVo> querySearchPage(List<String> tagList , Long pageNumber) {
        Page<SearchVo> page = new Page<>(pageNumber , 15);
        return articleManageMapper.querySearchPage(tagList , page);
    }
}
