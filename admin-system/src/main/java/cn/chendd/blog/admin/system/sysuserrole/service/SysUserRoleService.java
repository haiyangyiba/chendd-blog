package cn.chendd.blog.admin.system.sysuserrole.service;

import cn.chendd.blog.admin.system.sysuserrole.model.SysUserRole;
import cn.chendd.blog.admin.system.sysuserrole.vo.UserRoleResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chendd
 * @date 2020/6/25 20:47
 */
public interface SysUserRoleService extends IService<SysUserRole> {


    /**
     * 查询所有角色列表（按用户区分是否选中）
     * @param userId 用户ID
     * @return 数据列表
     */
    List<UserRoleResult> queryRolesByUserId(Long userId);

    /**
     * 批量保存用户与角色信息
     * @param userId 用户ID
     * @param roleIds 批量角色ID
     */
    void saveBatch(Long userId, List<Long> roleIds);

    /**
     * 保存默认的用户角色
     * @param userId 用户ID
     */
    void saveDefaultUserRole(Long userId);
}
