package cn.chendd.core.common.treeview.menu;

import lombok.Builder;
import lombok.Data;

/**
 * 菜单节点
 *
 * @author chendd
 * @date 2020/10/10 11:00
 */
@Data
@Builder
public class TreeMenu {

    /**
     * 菜单ID
     */
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 上级菜单ID
     */
    private String parentId;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 菜单图标
     */
    private String icon;

}
