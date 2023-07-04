package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.client.system.SystemInfoResult;
import cn.chendd.blog.web.home.client.vo.TagManageResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
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
public interface InfoContentClient {

    /**
     * 获取所有数据接口
     * @return 数据集合
     */
    @Get(url = "/v1/system/infocontent/project/${key}.html")
    BaseResult<SysInfoProjectResult> getProjectInfo(@DataVariable("key") String key);

    /**
     * 获取系统信息：环境及硬件
     * @return 信息信息
     */
    @Get(url = "/v1/system/status.html" , dataType = "json")
    BaseResult<SystemInfoResult> getSystemInfo();

}
