package cn.chendd.blog.web.home.client;

import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.*;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 第三方登录client
 *
 * @author chendd
 * @date 2021/2/9 21:42
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface UserLoginClient {

    /**
     * 获取第三方登录的地址
     * @param key 第三方登录应用标识
     * @param friend 朋友站点
     * @return 登录地址
     */
    @Get(url = "/v1/third-login/${key}.html?friend=${friend}")
    BaseResult<String> getLoginUrl(@DataVariable("key") String key, @DataVariable("friend") String friend);

    /**
     * 存储第三方登录用户信息
     * @param userResult 用户信息对象
     * @return 系统内用户对象结构
     */
    @Put(url = "/v1/system/user/third.html" , dataType = "json", contentType = MediaType.APPLICATION_JSON_VALUE , logEnabled = true)
    BaseResult<SysUserResult> thirdUserStore(@Body ThirdUserResult userResult);

}
