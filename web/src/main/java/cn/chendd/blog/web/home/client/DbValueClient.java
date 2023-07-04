package cn.chendd.blog.web.home.client;

import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 参数信息client
 *
 * @author chendd
 * @date 2021/2/9 21:42
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface DbValueClient {

    /**
     * 由于toolkit接口未引用base，故此处接口无/v1的接口版本号
     * 根据group 与 key 查询对应参数值
     * @param group 参数组group
     * @param key 参数标识key
     * @return 参数值
     */
    @Get(url = "/toolkit/dbvalue/${group}/${key}")
    BaseResult<String> getDbValueByKey(@DataVariable("group") String group , @DataVariable("key") String key);

}
