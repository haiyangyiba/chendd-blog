package cn.chendd.blog.admin.system.sysrolemenu.service;

import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import cn.chendd.blog.admin.system.sysrolemenu.vo.SysRoleMenuDTO;
import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.common.treeview.stratified.Treeview;

import java.util.List;

/**
 * 系统角色菜单Service接口定义
 * @author chendd
 * @date 2019/10/27 20:40
 */
public interface SysRoleMenuService {

    /**
     * 根据一组角色信息获取角色对应的所有菜单汇总
     * @param roles 角色组
     * @return 所有菜单集合
     */
    List<SysRoleMenuDTO> getUserMenusByRoles(List<SysUserRoleDTO> roles);

    /**
     * 查询指定角色中使用到的菜单数量
     * @param roleIds 角色ID组
     * @return 使用个数
     */
    Integer getMenusByRoles(List<Long> roleIds);

    /**
     * 查询指定菜单中使用到的角色个数
     * @param menuIds 菜单ID组
     * @return 使用个数
     */
    Integer getRolesByMenus(List<Long> menuIds);

    /**
     * 根据角色查询角色相关的菜单
     * @param roleId 角色ID
     */
    List<Treeview> queryMenusByRole(Long roleId);

    /**
     * 根据角色保存所选的菜单信息
     * @param roleId 角色ID
     * @param menuIds 以逗号分割菜单ID
     */
    Integer saveRoleMenu(Long roleId, String menuIds);

    /**
     * 存储当前在线用户
     * @param sysAccount 用户账户
     */
    SysUserResult putUserResult(SysAccount sysAccount);

    /**
     * 用户登录成功后的日志记录
     * @param username 用户信息
     */
    void loginUser(String username);

    /**
     * 用户退出
     * @param username 用户信息
     */
    void logoutUser(String username);
}
