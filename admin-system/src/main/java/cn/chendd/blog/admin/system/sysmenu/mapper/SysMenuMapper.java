package cn.chendd.blog.admin.system.sysmenu.mapper;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统菜单Mapper定义
 *
 * @author chendd
 * @date 2019/10/20 18:37
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询web菜单集合（排除首页）
     * @return web菜单集合
     */
    List<SysMenu> queryWebMenus();

    /**
     * 根据文章类型查询所有文章数量
     * @param menuIdList 文章类型
     * @return 个数
     */
    Integer queryArticleCountByType(List<Long> menuIdList);

    /**
     * 查询文章数量
     * @return 个数
     */
    Integer queryArticleCount();
}
