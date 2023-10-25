package cn.chendd.blog.admin.system.sitemap.mapper;

import cn.chendd.blog.admin.system.sitemap.dto.SitemapArticleDto;

import java.util.List;

/**
 * 网站地图Mapper接口定义
 *
 * @author chendd
 * @date 2023/10/25 16:42
 */
public interface SitemapMapper {

    /**
     * 获取网站地图的文章数据
     * @return 列表数据
     */
    List<SitemapArticleDto> getSitemap();
}
