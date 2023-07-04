package cn.chendd.blog.admin.system.sysrolemenu.service.impl;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.admin.system.sysrole.service.SysRoleService;
import cn.chendd.blog.admin.system.sysrole.vo.SysUserRoleDTO;
import cn.chendd.blog.admin.system.sysrolemenu.mapper.SysRoleMenuMapper;
import cn.chendd.blog.admin.system.sysrolemenu.model.SysRoleMenu;
import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.blog.admin.system.sysrolemenu.vo.SysRoleMenuDTO;
import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.admin.system.sysuser.model.SysUser;
import cn.chendd.blog.admin.system.sysuser.service.SysUserService;
import cn.chendd.blog.base.enums.EnumAdminTheme;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.common.treeview.stratified.*;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 系统角色菜单Service实现
 *
 * @author chendd
 * @date 2019/10/27 21:07
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper , SysRoleMenu> implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysRoleMenuDTO> getUserMenusByRoles(List<SysUserRoleDTO> roles) {

        return sysRoleMenuMapper.getUserMenusByRoles(roles);
    }

    @Override
    public Integer getMenusByRoles(List<Long> roleIds) {
        return sysRoleMenuMapper.selectCount(new QueryWrapper<SysRoleMenu>().in("roleId" , roleIds));
    }

    @Override
    public Integer getRolesByMenus(List<Long> menuIds) {
        return sysRoleMenuMapper.selectCount(new QueryWrapper<SysRoleMenu>().in("menuId" , menuIds));
    }

    @Override
    public List<Treeview> queryMenusByRole(Long roleId) {
        //查询所有菜单
        List<SysMenu> menuList = sysMenuService.queryAllSysMenus();
        //将菜单列表数据转换
        List<Tree> treeList = this.convertTreeList(menuList);
        TreeviewView treeviewView = TreeviewView.newInstance(treeList);
        //查询角色的菜单
        List<SysRoleMenu> roleMenuList = this.sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("roleId", roleId));
        List<TreeviewState> stateList = Lists.newArrayList();
        for (SysRoleMenu roleMenu : roleMenuList) {
            String id = roleMenu.getMenuId().toString();
            HashMap<EnumTreeviewState, Boolean> map = Maps.newHashMap();
            map.put(EnumTreeviewState.checked , true);
            stateList.add(new TreeviewState(id , map));
        }
        List<Treeview> treeviewList = treeviewView.convert(stateList);

        return treeviewList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "角色管理-菜单编辑" , scope = LogScopeEnum.ALL)
    public Integer saveRoleMenu(Long roleId, String menuIds) {
        Integer count = this.sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("roleId" , roleId));
        List<SysRoleMenu> list = Lists.newArrayList();
        if(StringUtils.isNotEmpty(menuIds)) {
            String ids[] = menuIds.split(",");
            for (String menuId : ids) {
                Long id = IdWorker.getId();
                list.add(new SysRoleMenu(id , roleId , Long.valueOf(menuId)));
            }
            return this.sysRoleMenuMapper.insertBatch(list);
        }
        return 0;
    }

    /**
     *
     * @param sysAccount 系统账号信息
     * @return 用户登录实体对象
     */
    @Override
    public SysUserResult putUserResult(SysAccount sysAccount) {
        SysUserResult userEntity = new SysUserResult();
        userEntity.setAccount(JSONObject.parseObject(JSONObject.toJSONString(sysAccount , SerializerFeature.WriteEnumUsingToString) , SysUserResult.SysAccount.class));
        //用户合法正常登录
        SysUser sysUser = this.sysUserService.querySysUserByAccountId(sysAccount.getAccountId());
        userEntity.setUser(JSONObject.parseObject(JSONObject.toJSONString(sysUser , SerializerFeature.WriteEnumUsingToString) , SysUserResult.SysUser.class));
        Long userId = sysUser.getUserId();
        //查询用户角色
        List<SysUserRoleDTO> roles = sysRoleService.getUserRolesByUserId(userId);
        //查询角色菜单
        List<SysRoleMenuDTO> menus = this.sysRoleMenuService.getUserMenusByRoles(roles);
        //将所有的角色菜单数据转换为对应的角色的对应菜单
        this.fillRoleMenus(roles , menus);
        userEntity.setRoles(JSONArray.parseArray(JSONArray.toJSONString(roles) , SysUserResult.SysRole.class));
        //设置用户配置信息
        SysUserResult.SysUserConfig userConfig = new SysUserResult.SysUserConfig();
        //默认主题
        userConfig.setAdminTheme(EnumAdminTheme.light.getType());
        userEntity.setUserConfig(userConfig);
        return userEntity;
    }

    @Override
    @Log(description = "'用户 [' + #username + '] 登录'" , scope = LogScopeEnum.METHOD_PARAMETER)
    public void loginUser(String username) {}

    @Override
    @Log(description = "'用户 [' + #username + '] 退出'" , scope = LogScopeEnum.METHOD_PARAMETER)
    public void logoutUser(String username) {}

    /**
     * 将数据转换为封装的treeview组件的结构
     */
    private List<Tree> convertTreeList(List<SysMenu> resultList) {
        List<Tree> treeList = Lists.newArrayList();
        for (SysMenu result : resultList) {
            Tree tree = new Tree(result.getMenuId().toString() , result.getParentId().toString() , result.getMenuName());
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 根据用户角色和菜单将数据转换为按角色对应的菜单数据集合
     * @param roles 用户所有角色
     * @param menus 用户所有菜单
     */
    private void fillRoleMenus(List<SysUserRoleDTO> roles , List<SysRoleMenuDTO> menus) {
        for (SysUserRoleDTO role : roles) {
            this.fillRoleMenu(role , menus);
        }
    }

    /**
     * 根据角色和菜单数据将数据对应，一共最多只查找 4 层级节点，实际上无限极在实际上并不存在
     * @param role 角色数据
     * @param menus 用户有权限的所有菜单集合
     */
    private void fillRoleMenu(SysUserRoleDTO role , List<SysRoleMenuDTO> menus) {
        Long roleId = role.getRoleId();
        //删除默认的根级菜单
        final String rootKey = "#blog_admin_menus";
        //找到后台管理的跟节点的id
        Long rootId = null;
        List<SysRoleMenuDTO> menuList = Lists.newArrayList();
        for (SysRoleMenuDTO menu : menus) {
            String menuKey = menu.getMenuKey();
            if (StringUtils.equals(rootKey , menuKey)) {
                rootId = menu.getMenuId();
                break;
            }
        }
        if (rootId == null) {
            return;
        }
        //找出根节点的第一级节点
        for (SysRoleMenuDTO menu : menus) {
            Long parentId = menu.getParentId();
            Long currentRoleId = menu.getRoleId();
            if (rootId.equals(parentId) && roleId.equals(currentRoleId)) {
                //找出第一级节点的子节点
                menuList.add(menu);
                //找出第二级节点的子节点
                this.appendMenu(menu , menus);
                //找出第三级节点的子节点
                List<SysRoleMenuDTO> threeMenuList = menu.getRoleMenus();
                if (threeMenuList == null) {
                    continue;
                }
                for (SysRoleMenuDTO three : threeMenuList) {
                    this.appendMenu(three , menus);
                    //找出第三级节点
                    List<SysRoleMenuDTO> fourMenuList = three.getRoleMenus();
                    if (fourMenuList == null) {
                        continue;
                    }
                    for (SysRoleMenuDTO four : fourMenuList) {
                        //找出第四级节点
                        this.appendMenu(four , menus);
                    }
                }
            }
        }
        role.setMenuList(menuList);
    }

    /**
     * 根级上级节点查询所有子级菜单
     * @param item 上级菜单
     * @param menus 菜单集合
     */
    private void appendMenu(SysRoleMenuDTO item , List<SysRoleMenuDTO> menus) {
        Long id = item.getMenuId();
        List<SysRoleMenuDTO> dataList = Lists.newArrayList();
        for (SysRoleMenuDTO menu : menus) {
            Long parentId = menu.getParentId();
            if (id.equals(parentId) && item.getRoleId().equals(menu.getRoleId())) {
                dataList.add(menu);
            }
        }
        item.setRoleMenus(dataList);
    }

}
