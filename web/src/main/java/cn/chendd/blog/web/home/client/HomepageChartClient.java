package cn.chendd.blog.web.home.client;

import cn.chendd.blog.web.home.client.vo.ReportChartHomepage;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 首页图表client交互接口
 * @author chendd
 * @date 2021/10/16 20:01
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface HomepageChartClient {

    /**
     * 首页报表交互
     * @return 报表数据
     */
    @Get(url = "/v1/report/homepage.html")
    BaseResult<ReportChartHomepage> queryHomepageCharts();

}
