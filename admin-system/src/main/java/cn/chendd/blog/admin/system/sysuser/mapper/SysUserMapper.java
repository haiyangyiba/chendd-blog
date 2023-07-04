package cn.chendd.blog.admin.system.sysuser.mapper;

import cn.chendd.blog.admin.system.sysuser.model.SysUser;
import cn.chendd.blog.admin.system.sysuser.po.SysUserManageParam;
import cn.chendd.blog.admin.system.sysuser.po.SysUserSaveParam;
import cn.chendd.blog.admin.system.sysuser.vo.SysUserManageResult;
import cn.chendd.blog.admin.system.sysuser.vo.UserInfoViewResult;
import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper定义
 *
 * @author chendd
 * @date 2019/11/24 16:43
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUserManageResult> querySysUserPage(Page page , @Param("userParam") SysUserManageParam userParam);

    /**
     * 查看用户详细信息
     * @param userId 用户ID
     * @return 用户详细
     */
    UserInfoViewResult getUserInfoViewById(Long userId);

    /**
     * 修改用户
     * @param param 参数值
     * @return 修改数据条数
     */
    Integer editUser(SysUserSaveParam param);

    /**
     * 修改用户状态
     * @param status 状态值
     * @param userId 用户ID
     * @return 修改数据条数
     */
    Integer editUserStatus(@Param("status") String status, @Param("userId") Long userId);

    /**
     * 查询用户创建的N条数据
     * @param limit 获取条数
     * @return 集合数据
     */
    List<UserNewestDto> queryUserNewestList(@Param("limit") int limit);

    /**
     * 根据用户Id查询用户基本数据
     * @param userId 用户Id
     * @return 数据
     */
    UserInfoResult queryUserInfo(@Param("userId") Long userId);

    /**
     * 根据用户Id修改Email
     * @param userId 用户Id
     * @param email Email
     */
    void updateEmailByUserId(@Param("userId") String userId, @Param("email") String email);
}
