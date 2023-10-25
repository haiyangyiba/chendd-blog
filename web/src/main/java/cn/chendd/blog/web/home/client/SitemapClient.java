package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 网站地图Client
 *
 * @author chendd
 * @date 2023/10/25 16:30
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface SitemapClient {

    /**
     * 获取全量的网站地图xml
     * @return 网站地图文本
     */
    @Get(url = "/v1/system/Sitemap.html")
    BaseResult<String> getSitemap();

}
