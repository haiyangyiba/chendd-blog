package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.*;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 用户信息交互Client
 *
 * @author chendd
 * @date 2021/2/12 1:58
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface UserInfoClient {

    /**
     * 根据用户Id获取用户基本信息
     * @param userId 用户Id
     * @return 数据对象
     */
    @Get(url = "/v1/system/user/${userId}.html")
    BaseResult<UserInfoResult> queryUserInfo(@DataVariable("userId") Long userId);

    /**
     * 根据用户Id修改Email
     * @param userId 用户Id
     * @param email Email
     */
    @Put(url = "/v1/system/user/${userId}.html" , contentType = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateUserEmail(@DataVariable("userId") Long userId, @Body String email);
}
