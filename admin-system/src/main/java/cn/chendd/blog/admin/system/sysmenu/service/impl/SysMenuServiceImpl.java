package cn.chendd.blog.admin.system.sysmenu.service.impl;

import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.sysmenu.mapper.SysMenuMapper;
import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuLevelResult;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuParam;
import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.core.common.treeview.planar.Node;
import cn.chendd.core.common.treeview.planar.NodeView;
import cn.chendd.core.common.treeview.stratified.Tree;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.common.treeview.stratified.TreeviewView;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.chendd.core.common.constant.Constant.MENU_ROOT_ID;

/**
 * 系统菜单Service实现
 *
 * @author chendd
 * @date 2019/10/20 18:35
 */
@Service
@CacheConfig(cacheNames = CacheNameConstant.NOT_EXPIRED)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    @Log(description = "'查询菜单列表'" , scope = LogScopeEnum.Auto)
    public List<SysMenu> queryAll() {
        return super.list(new QueryWrapper<SysMenu>().orderByAsc("menuOrder"));
    }

    @Override
    @Cacheable(key = "'queryAllSysMenus'")
    public List<SysMenu> queryAllSysMenus() {
        return this.queryAll();
    }

    @Override
    @Log(description = "'菜单管理-[' + (#sysMenu.menuId == null ? '新增' : '修改') + ']'" , scope = LogScopeEnum.ALL)
    @CacheEvict(key = "'queryAllSysMenus'" , allEntries = false , beforeInvocation = true)
    public SysMenu saveSysMenu(SysMenu sysMenu) {
        SysMenu entity = new SysMenu();
        BeanUtils.copyProperties(sysMenu , entity);
        String menuKey = sysMenu.getMenuKey();
        Long menuId = sysMenu.getMenuId();
        if(menuId == null) {
            entity.setCreateDate(DateFormat.formatDatetime());
        } else {
            entity.setUpdateDate(DateFormat.formatDatetime());
        }
        Long parentId = sysMenu.getParentId();
        if(parentId == null) {
            //未选中上级菜单时，默认为根节点
            entity.setParentId(Long.valueOf(MENU_ROOT_ID));
        }
        if(StringUtils.isNotEmpty(menuKey)){
            //查询 roleKey 是否唯一，否则不可用
            QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().eq("menuKey", menuKey);
            if(menuId != null){
                queryWrapper.ne("menuId" , menuId);
            }
            Integer count = baseMapper.selectCount(queryWrapper);
            if(count > 0){
                throw new ValidateDataException("菜单标识重复！");
            }
        }
        super.saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Log(description = "菜单管理-删除" , scope = LogScopeEnum.ALL)
    @CacheEvict(key = "'queryAllSysMenus'" , allEntries = false , beforeInvocation = true)
    public Integer removeMenus(List<Long> ids) {
        QueryWrapper<SysMenu> countWapper = new QueryWrapper<>();
        countWapper.in("parentId" , ids);
        int menus = super.count(countWapper);
        if(menus > 0){
            throw new ValidateDataException(String.format("所选菜单有 %d 个子级菜单，请先删除子菜单！" , menus));
        }
        Integer roles = sysRoleMenuService.getRolesByMenus(ids);
        if(roles > 0) {
            throw new ValidateDataException(String.format("所选菜单有 %d 个角色关联，请先删除关联！" , roles));
        }

        return super.getBaseMapper().deleteBatchIds(ids);
    }

    @Override
    public List<SysMenu> querySysMenus(SysMenuParam menuParam) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuParam.getMenuName()) , "menuName" , menuParam.getMenuName());
        queryWrapper.likeRight(StringUtils.isNotBlank(menuParam.getMenuKey()) , "menuKey" , menuParam.getMenuKey());
        queryWrapper.orderByAsc("menuOrder");
        return super.list(queryWrapper);
    }

    @Override
    public List<SysMenuLevelResult> convertMenuList(List<SysMenu> menuList , SysMenuParam menuParam) {
        List<Node> nodeList = Lists.newArrayList();
        for (SysMenu menu : menuList) {
            Map<String , Object> attribute = Maps.newHashMap();
            nodeList.add(new Node(menu.getMenuId().toString() , menu.getMenuName() , menu.getParentId().toString() , attribute));
        }
        //bootstrap treegrid parentId默认根节点为null、""或者0
        List<Node> nodes = NodeView.newInstance(nodeList).convert();
        List<SysMenuLevelResult> resultList = Lists.newArrayList();
        for (Node node : nodes) {
            String nodeId = node.getId();
            for (SysMenu menu : menuList) {
                String menuId = menu.getMenuId().toString();
                if (StringUtils.equals(nodeId , menuId)) {
                    SysMenuLevelResult sysMenuLevelResult = new SysMenuLevelResult(node, menu, menuParam);
                    resultList.add(sysMenuLevelResult);
                }
            }
        }
        //详细参数
        return resultList;
    }

    @Override
    public List<Treeview> queryTreeviews() {
        String rootKey = "#blog_web_menu_types" , menuKey = "#blog_web_menu_";
        List<SysMenu> menuList = this.queryWebMenus(rootKey , menuKey);
        List<Tree> treeList = Lists.newArrayList();
        //将数据库查询的菜单对象转换为菜单结构处理的对象
        menuList.forEach(item -> {
            Tree tree = new Tree(item.getMenuId().toString() , item.getParentId().toString() , item.getMenuName());
            Map<String, Object> values = Maps.newHashMap();
            values.put("icon" , item.getMenuIcon());
            values.put("url" , item.getMenuUrl());
            tree.setValues(values);
            treeList.add(tree);
        });
        TreeviewView treeview = TreeviewView.newInstance(this.queryRootWebMenu(rootKey).getMenuId().toString() , treeList , TreeviewView.MAX_DEPTH);
        List<Treeview> treeviewList = treeview.convert(Lists.newArrayList());
        return treeviewList;
    }

    @Override
    public String getTreeviewType(List<Treeview> treeviews, Long type , String split) {
        String rootKey = "#blog_web_menu_types" , menuKey = "#blog_web_menu_";
        List<SysMenu> menuList = this.queryWebMenus(rootKey , menuKey);
        List<SysMenu> resultList = Lists.newArrayList();
        for (SysMenu menu : menuList) {
            if (menu.getMenuId().equals(type)) {
                this.queryParentMenuById(menu.getMenuId() , menuList , resultList);
            }
        }
        StringBuilder builder = new StringBuilder();
        int lens = resultList.size();
        if (lens > 0) {
            for (int i = 0; i < lens; i++) {
                SysMenu menu = resultList.get(i);
                builder.insert(0, menu.getMenuName()).insert(0, split);
            }
            builder.delete(0 , split.length());
        }
        return builder.toString();
    }

    private void queryParentMenuById(Long menuId , List<SysMenu> menuList , List<SysMenu> resultList) {
        for (SysMenu menu : menuList) {
            if (menu.getMenuId().equals(menuId)) {
                resultList.add(menu);
                if (menu.getMenuId().equals(Long.valueOf(MENU_ROOT_ID))) {
                    break;
                }
                this.queryParentMenuById(menu.getParentId() , menuList , resultList);
            }
        }
    }

    /**
     * 查询博客菜单
     * @return 博客菜单
     */
    private List<SysMenu> queryWebMenus(String rootKey , String menuKey) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>()
                .likeRight("menuKey", menuKey).notIn("menuKey" , rootKey)
                .eq("menuStatus" , EnumStatus.ENABLE.getValue());
        queryWrapper.orderByAsc("menuOrder");
        List<SysMenu> menuList = sysMenuMapper.selectList(queryWrapper);
        return menuList;
    }

    /**
     * 查询博客菜单
     * @return 博客菜单
     */
    private SysMenu queryRootWebMenu(String rootKey) {
        SysMenu parentMenu = sysMenuMapper.selectOne(new QueryWrapper<SysMenu>().eq("menuKey", rootKey));
        return parentMenu;
    }

}
