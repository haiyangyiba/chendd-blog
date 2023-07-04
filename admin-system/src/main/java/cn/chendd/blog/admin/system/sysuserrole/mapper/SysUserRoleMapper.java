package cn.chendd.blog.admin.system.sysuserrole.mapper;

import cn.chendd.blog.admin.system.sysuserrole.model.SysUserRole;
import cn.chendd.blog.admin.system.sysuserrole.vo.UserRoleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户角色Mapper
 *
 * @author chendd
 * @date 2020/6/25 20:45
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 查询角色列表（根据用户ID判断是否选中）
     */
    List<UserRoleResult> queryRolesByUserId(Long userId);

}
