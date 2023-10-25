package cn.chendd.blog.admin.system.sitemap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 网站地图--文章
 *
 * @author chendd
 * @date 2023/10/25 16:43
 */
@Data
public class SitemapArticleDto {

    /**
     * location url地址
     */
    private String loc;
    /**
     * 最后更新时间：yyyy-MM-dd
     */
    private String lastmod;
    /**
     * 更改频率
     */
    private String changefreq;
    /**
     * 权重 0 - 1，如：0.5
     */
    private String priority;

}
