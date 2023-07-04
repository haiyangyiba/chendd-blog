package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.web.home.client.UserInfoClient;
import cn.chendd.blog.web.home.service.UserInfoService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息Service接口实现
 * @author chendd
 * @date 2022/4/5 17:52
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoClient client;

    @Override
    public UserInfoResult queryUserInfo(Long userId) {
        BaseResult<UserInfoResult> baseResult = this.client.queryUserInfo(userId);
        return baseResult.getData();
    }

    @Override
    public void updateUserEmail(Long userId, String email) {
        this.client.updateUserEmail(userId , email);
    }
}
