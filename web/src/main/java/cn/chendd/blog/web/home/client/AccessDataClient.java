package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.access.AccessDataBo;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 博客访问数量Client
 *
 * @author chendd
 * @date 2020/2/26 22:30
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface AccessDataClient {

    /**
     * 获取系统访问量
     * @return 访问量
     */
    @Get(url = "/v1/blog/access.html")
    BaseResult<AccessDataBo> getAccessData();
}
