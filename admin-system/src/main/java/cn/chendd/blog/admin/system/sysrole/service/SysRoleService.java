package cn.chendd.blog.admin.system.sysrole.service;

import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色管理接口定义
 *
 * @author chendd
 * @date 2019/9/19 14:55
 */
public interface SysRoleService extends IService<SysRole> {

    /**
    * 保存角色对象
    * @param sysRole 角色对象
    * @return cn.chendd.blog.admin.sysrole.model.SysRole
    */
    SysRole saveSysRole(SysRole sysRole);

    /**
     * 批量删除角色数据
     * @param ids 角色列表
     * @return 删除影响结果数量
     */
    Integer removeRoles(List<Long> ids);

    /**
     * 获取所有角色数据
     */
    List<SysRole> queryRoles(SysRole role);

    /**
     * 根据用户ID查询用户所有角色
     * @param userId 用户ID
     * @return 用户所有角色列表
     */
    List<SysUserRoleDTO> getUserRolesByUserId(Long userId);
}
