package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.web.home.client.UserLoginClient;
import cn.chendd.blog.web.home.service.UserLoginService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户登录信息Service接口实现
 *
 * @author chendd
 * @date 2021/2/12 23:15
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private UserLoginClient userLoginClient;

    @Override
    public BaseResult<String> getLoginUrl(String key, String friend) {
        return userLoginClient.getLoginUrl(key , friend);
    }

    @Override
    public SysUserResult thirdUserStore(ThirdUserResult thirdUserResult) {
        BaseResult<SysUserResult> baseResult = userLoginClient.thirdUserStore(thirdUserResult);
        return baseResult.getData();
    }
}
