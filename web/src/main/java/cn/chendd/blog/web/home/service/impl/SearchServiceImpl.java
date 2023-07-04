package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.search.SearchVo;
import cn.chendd.blog.web.home.client.SearchClient;
import cn.chendd.blog.web.home.service.SearchService;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 关键字查询Service接口实现
 *
 * @author chendd
 * @date 2022/2/17 20:54
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private SearchClient searchClient;

    @Override
    public Page<SearchVo> querySearchPage(String keyWords , Long pageNumber) {
        BaseResult<Page<SearchVo>> result = searchClient.querySearchPage(keyWords, pageNumber);
        return result.getData();
    }
}
