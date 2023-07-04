package cn.chendd.blog.admin.system.sysuserrole.service.impl;

import cn.chendd.blog.admin.system.sysrole.mapper.SysRoleMapper;
import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.admin.system.sysuserrole.mapper.SysUserRoleMapper;
import cn.chendd.blog.admin.system.sysuserrole.model.SysUserRole;
import cn.chendd.blog.admin.system.sysuserrole.service.SysUserRoleService;
import cn.chendd.blog.admin.system.sysuserrole.vo.UserRoleResult;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author chendd
 * @date 2020/6/25 20:48
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<UserRoleResult> queryRolesByUserId(Long userId) {
        return sysUserRoleMapper.queryRolesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "用户管理-角色编辑" , scope = LogScopeEnum.ALL)
    public void saveBatch(Long userId, List<Long> roleIds) {
        Collection<SysUserRole> dataList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                dataList.add(new SysUserRole(userId , roleId));
            }
        }
        //删除用户ID已有的角色数据，再保存对应的角色数据
        super.remove(new QueryWrapper<SysUserRole>().eq("userId" , userId));
        super.saveBatch(dataList);
    }

    @Override
    public void saveDefaultUserRole(Long userId) {
        SysRole sysRole = this.sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("roleKey", Constant.SYSTEM_DEFAULT_ROLE_KEY));
        if (sysRole != null) {
            this.sysUserRoleMapper.insert(new SysUserRole(userId , sysRole.getRoleId()));
        }
    }
}
