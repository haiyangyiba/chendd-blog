package cn.chendd.blog.web.home.service;

import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.result.BaseResult;

/**
 * 用户登录信息Service接口定义
 *
 * @author chendd
 * @date 2021/2/12 23:14
 */
public interface UserLoginService {

    /**
     * 根据key和friend获取登录地址
     * @param key 登录标识
     * @param friend 友链标识
     * @return 登录信息地址
     */
    BaseResult<String> getLoginUrl(String key, String friend);

    /**
     * 根据第三方登录后返回的用户信息对象转换为系统内部用户主体信息
     * @param thirdUserResult 第三方用户信息
     * @return 系统用户信息
     */
    SysUserResult thirdUserStore(ThirdUserResult thirdUserResult);

}
