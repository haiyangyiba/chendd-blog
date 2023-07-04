package cn.chendd.blog.admin.system.sysuser.service;

import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户账号管理Service接口定义
 * @author chendd
 * @date 2019/11/24 15:53
 */
public interface ISysAccountService extends IService<SysAccount> {

    /**
     * 根据用户名查询用户对象
     * @param username 用户名
     * @return 用户对象
     */
    SysAccount querySysAccountByUsername(String username);

}
