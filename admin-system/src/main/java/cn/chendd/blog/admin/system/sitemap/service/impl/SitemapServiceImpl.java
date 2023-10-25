package cn.chendd.blog.admin.system.sitemap.service.impl;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.sitemap.dto.SitemapArticleDto;
import cn.chendd.blog.admin.system.sitemap.mapper.SitemapMapper;
import cn.chendd.blog.admin.system.sitemap.service.SitemapService;
import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 网站地图接口实现
 *
 * @author chendd
 * @date 2023/10/25 16:38
 */
@Service
public class SitemapServiceImpl implements SitemapService {

    @Resource
    private SitemapMapper sitemapMapper;
    @Resource
    private SysInfoContentService sysInfoContentService;

    @Override
    public String getSitemap() {
        final List<SysInfoContent> infoContentList = this.sysInfoContentService.querySystemInfo("Sitemap");
        final List<SitemapArticleDto> resultList = Lists.newArrayList();
        if (! infoContentList.isEmpty()) {
            JSONArray jsonArray = infoContentList.get(0).getEditorContentJSONArray();
            if (jsonArray != null) {
                resultList.addAll(JSONArray.parseArray(jsonArray.toJSONString(), SitemapArticleDto.class));
            }
        }
        List<SitemapArticleDto> dataList = this.sitemapMapper.getSitemap();
        resultList.addAll(dataList);
        return this.convertToXml(resultList);
    }

    /**
     * 将数据转换为 xml 格式的 Sitexml
     * @param resultList 列表数据
     * @return xml 数据
     */
    private String convertToXml(List<SitemapArticleDto> resultList) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset>");
        for (SitemapArticleDto item : resultList) {
            xml.append("<url>");
            xml.append("<loc>").append(item.getLoc()).append("</loc>");
            xml.append("<lastmod>").append(item.getLastmod()).append("</lastmod>");
            xml.append("<changefreq>").append(item.getChangefreq()).append("</changefreq>");
            xml.append("<priority>").append(item.getPriority()).append("</priority>");
            xml.append("</url>\r\n");
        }
        xml.append("</urlset>");
        return xml.toString();
    }
}
