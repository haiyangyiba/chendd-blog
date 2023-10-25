package cn.chendd.blog.admin.system.client.sitemap;

import cn.chendd.blog.admin.system.sitemap.service.SitemapService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 网站地图Controller定义
 *
 * @author chendd
 * @date 2023/10/25 16:33
 */
@Api(value = "网站地图" , tags = "网站地图")
@ApiSort(10)
@RequestMapping(value = {"/system/Sitemap" , "/system/sitemap"} , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
public class SitemapClientController extends BaseController {

    @Resource
    private SitemapService sitemapService;

    @GetMapping
    @ApiOperation(value = "查询页面标识",notes = "根据页面标识查询自定义页面内容")
    @ApiOperationSupport(order = 10)
    public String getSitemap() {
        return this.sitemapService.getSitemap();
    }

}
