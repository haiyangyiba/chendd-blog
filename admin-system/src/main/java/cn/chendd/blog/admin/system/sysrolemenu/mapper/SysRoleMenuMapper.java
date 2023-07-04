package cn.chendd.blog.admin.system.sysrolemenu.mapper;

import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import cn.chendd.blog.admin.system.sysrolemenu.model.SysRoleMenu;
import cn.chendd.blog.admin.system.sysrolemenu.vo.SysRoleMenuDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * RoleMenuMapper接口定义
 *
 * @author chendd
 * @date 2019/10/27 20:54
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色集合获取角色对应的所有菜单
     * @param roles 角色集合
     * @return 菜单集合
     */
    List<SysRoleMenuDTO> getUserMenusByRoles(List<SysUserRoleDTO> roles);

    /**
     * 批量保存角色菜单数据
     * @param list 角色菜单数据
     * @return 插入数据条数
     */
    Integer insertBatch(List<SysRoleMenu> list);
}
