package cn.chendd.blog.admin.system.sysrole.service.impl;

import cn.chendd.blog.admin.system.sysrole.mapper.SysRoleMapper;
import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.admin.system.sysrole.service.SysRoleService;
import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色管理Service实现
 *
 * @author chendd
 * @date 2019/9/19 14:58
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    @Log(description = "'角色管理-[' + (#sysRole.roleId == null ? '新增' : '修改') + ']'" , scope = LogScopeEnum.ALL)
    public SysRole saveSysRole(SysRole sysRole) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(sysRole , entity);
        String roleKey = sysRole.getRoleKey();
        Long roleId = sysRole.getRoleId();
        if(roleId == null) {
            entity.setCreateDate(DateFormat.formatDatetime());
        } else {
            entity.setUpdateDate(DateFormat.formatDatetime());
        }
        if(StringUtils.isNotEmpty(roleKey)){
            //查询 roleKey 是否唯一，否则不可用
            QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().eq("roleKey", roleKey);
            if(roleId != null){
                queryWrapper.ne("roleId" , roleId);
            }
            Integer count = baseMapper.selectCount(queryWrapper);
            if(count > 0){
                throw new ValidateDataException(String.format("角色标识 [%s] 重复！" , roleKey));
            }
        }
        super.saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Log(description = "角色管理-删除" , scope = LogScopeEnum.ALL)
    public Integer removeRoles(List<Long> ids) {
        Integer count = sysRoleMenuService.getMenusByRoles(ids);
        if(count > 0) {
            throw new ValidateDataException("当前所选角色有正在使用的功能菜单，不允许删除!");
        }
        return super.getBaseMapper().deleteBatchIds(ids);
    }

    @Override
    @Log(description = "'查询角色列表'")
    public List<SysRole> queryRoles(SysRole sysRole) {
        return super.list(this.getSysRoleQueryWrapper(sysRole));
    }

    @Override
    public List<SysUserRoleDTO> getUserRolesByUserId(Long userId) {

        return sysRoleMapper.getUserRolesByUserId(userId);
    }

    /**
     * 查询条件
     * @param sysRole 角色对象
     * @return 查询对象
     */
    private QueryWrapper<SysRole> getSysRoleQueryWrapper(SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        String roleName = sysRole.getRoleName();
        if(StringUtils.isNotEmpty(roleName)){
            queryWrapper.like("roleName" , sysRole.getRoleName());
        }
        String roleKey = sysRole.getRoleKey();
        if(StringUtils.isNotEmpty(roleKey)){
            queryWrapper.like("roleKey" , sysRole.getRoleKey());
        }
        queryWrapper.orderByDesc("createDate");
        return queryWrapper;
    }

}
