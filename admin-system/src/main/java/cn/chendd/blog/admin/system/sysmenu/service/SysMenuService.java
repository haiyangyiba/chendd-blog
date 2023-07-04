package cn.chendd.blog.admin.system.sysmenu.service;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuLevelResult;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuParam;
import cn.chendd.core.common.treeview.stratified.Treeview;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chendd
 * @date 2019/10/20 18:07
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 按条件查询菜单列表，记录查询操作日志，但不缓存
     */
    List<SysMenu> queryAll();

    /**
     * 按条件查询菜单列表，不记录操作既然，但缓存
     */
    List<SysMenu> queryAllSysMenus();

    /**
     * 保存系统菜单
     * @param menu 菜单对象
     * @return 菜单对象
     */
    SysMenu saveSysMenu(SysMenu menu);

    /**
     * 批量删除菜单信息
     * @param ids 菜单id集合
     * @return 删除影响结果
     */
    Integer removeMenus(List<Long> ids);

    /**
     * 转换集合，将集合数据转换为按层级先后顺序显示
     * @param menuList 菜单集合
     * @param menuParam 菜单查询参数
     * @return 按层级顺序显示集合
     */
    List<SysMenuLevelResult> convertMenuList(List<SysMenu> menuList , SysMenuParam menuParam);

    /**
     * 查询菜单信息
     * @param menuParam 查询条件
     * @return 数据列表
     */
    List<SysMenu> querySysMenus(SysMenuParam menuParam);

    /**
     * @return 所有子集菜单列表
     */
    List<Treeview> queryTreeviews();

    /**
     * 根据菜单类型ID和菜单列表获取菜单文本
     * @param treeviews 菜单列表
     * @param type 菜单类型
     * @return 菜单对象类型层级之间的文本
     */
    String getTreeviewType(List<Treeview> treeviews, Long type , String split);

}
