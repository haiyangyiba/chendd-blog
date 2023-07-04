package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.user.vo.UserInfoResult;

/**
 * 用户信息Service接口定义
 *
 * @author chendd
 * @date 2022/4/5 17:51
 */
public interface UserInfoService {

    /**
     * 根据用户Id查询用户基本信息
     * @param userId 用户Id
     * @return 数据对象
     */
    UserInfoResult queryUserInfo(Long userId);

    void updateUserEmail(Long userId, String email);
}
