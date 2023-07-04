package cn.chendd.blog.admin.blog.search.service;

import cn.chendd.blog.client.search.SearchVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 搜索管理Service接口定义
 *
 * @author chendd
 * @date 2022/2/19 17:05
 */
public interface SearchManageService {

    /**
     * 根据关键字查询分页
     * @param tagList 关键字
     * @param pageNumber 页码
     * @return 数据分页
     */
    Page<SearchVo> querySearchPage(List<String> tagList , Long pageNumber);
}
