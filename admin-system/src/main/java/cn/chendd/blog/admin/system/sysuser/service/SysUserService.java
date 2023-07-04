package cn.chendd.blog.admin.system.sysuser.service;

import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.admin.system.sysuser.model.SysUser;
import cn.chendd.blog.admin.system.sysuser.po.SysUserManageParam;
import cn.chendd.blog.admin.system.sysuser.po.SysUserSaveParam;
import cn.chendd.blog.admin.system.sysuser.vo.SysUserManageResult;
import cn.chendd.blog.admin.system.sysuser.vo.UserInfoViewResult;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户Service接口定义
 * @author chendd
 * @date 2019/11/24 16:34
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 根据用户账户ID获取对应的用户信息
     * @param accountId 账户ID
     * @return 用户对象
     */
    SysUser querySysUserByAccountId(Long accountId);

    /**
     *
     * 系统用户查询分页
     */
    Page<SysUserManageResult> querySysUserPage(SysUserManageParam userParam);

    /**
     * 根据用户名查询用户账户对象
     * @param username 用户名
     * @return 用户对象或空
     */
    SysAccount querySysUserByUsername(String username);

    /**
     * 保存用户信息
     * @param param 用户参数
     * @return 数据保存状态
     */
    SuccessResult<String> saveUser(SysUserSaveParam param);

    /**
     * 查看用户详细
     * @param userId 用户ID
     * @return 用户详细信息
     */
    UserInfoViewResult getUserInfoViewById(Long userId);

    /**
     * 修改用户信息
     * @param param 用户信息参数
     * @return 数据保存状态
     */
    BaseResult editUser(SysUserSaveParam param);

    /**
     * 修改用户数据状态
     * @param status 状态值
     * @param userId 用户ID
     * @return 影响数据条数
     */
    Integer editUserStatus(String status, Long userId);

    /**
     * 重置用户密码
     * @param userId 用户ID
     * @param password 密码
     */
    void resetPassword(Long userId, String password);

    /**
     * 存储第三方用户登录信息
     * @param userResult 用户信息对象
     * @return 第三方用户信息转换为系统内用户信息
     */
    SysUserResult thirdUserStore(ThirdUserResult userResult);

    /**
     * 获取用户创建数据
     * @param limit 前N条数据
     * @return 列表数据
     */
    List<UserNewestDto> queryUserNewestList(int limit);

    /**
     * 根据用户Id查询用户基本数据
     * @param userId 用户Id
     * @return 用户数据
     */
    UserInfoResult queryUserInfo(Long userId);

    /**
     * 根据用户Id修改Email
     * @param userId 用户Id
     * @param email Email
     */
    void updateEmailByUserId(String userId, String email);
}
