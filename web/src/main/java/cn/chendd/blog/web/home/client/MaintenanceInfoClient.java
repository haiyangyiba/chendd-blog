package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.blog.web.home.client.vo.TagManageResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * 测试接口方法
 *
 * @author chendd
 * @date 2021/4/26 13:06
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = "utf-8" , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface MaintenanceInfoClient {

    /**
     * 获取所有数据接口
     * @return 数据集合
     */
    @Get(url = "/v1/system/maintenanceInfo.html")
    BaseResult<MaintenanceInfoVo> queryMaintenanceInfo();
}
