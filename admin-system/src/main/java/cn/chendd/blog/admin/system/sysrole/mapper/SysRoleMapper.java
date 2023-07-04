package cn.chendd.blog.admin.system.sysrole.mapper;

import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author chendd
 * @date 2019/9/19 15:02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID获取用户角色列表
     * @param userId 用户ID
     * @return 用户角色列表
     */
    List<SysUserRoleDTO> getUserRolesByUserId(Long userId);
}
