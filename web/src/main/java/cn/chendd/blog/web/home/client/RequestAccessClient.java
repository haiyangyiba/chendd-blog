package cn.chendd.blog.web.home.client;

import cn.chendd.blog.base.filters.detail.RequestItem;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Put;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * 站外访问请求统计
 * @author chendd
 * @date 2022/9/11 18:30
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface RequestAccessClient {

    /**
     * 保存站外访问请求
     * @param dataList 集合
     */
    @Put(url = "/v1/blog/access.html" , dataType = "json" , contentType = MediaType.APPLICATION_JSON_VALUE)
    void saveRequestAccess(@Body List<RequestItem> dataList);

}
