package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.custompage.CustomPageBo;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * 测试接口方法
 *
 * @author chendd
 * @date 2022/2/11 13:06
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = "utf-8" , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface CustomPageClient {

    /**
     * 根据页面标识查询自定义页面内容
     * @param page 页面标识
     * @return 数据对象
     */
    @Get(url = "/v1/system/page/${page}.html" , dataType = "json")
    BaseResult<Map<String , Object>> getCustomPage(@DataVariable("page") String page);
}
