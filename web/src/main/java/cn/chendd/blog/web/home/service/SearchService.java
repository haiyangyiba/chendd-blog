package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.search.SearchVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;

/**
 * 关键字查询Service接口定义
 *
 * @author chendd
 * @date 2022/2/17 20:53
 */
public interface SearchService {

    /**
     * 关键字查询分页
     * @param keyWords 关键字
     * @param pageNumber 分页编号
     * @return 分页数据对象
     */
    Page<SearchVo> querySearchPage(String keyWords , Long pageNumber);
}
