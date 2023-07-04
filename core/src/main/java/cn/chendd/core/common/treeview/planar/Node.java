package cn.chendd.core.common.treeview.planar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <strong>
 * 平面的tree结构转换
 * 将集合数据转换为按上下级顺序显示，如需按顺序显示，则需要原始数据存在一定顺序
 * 注意：
 * 1、一定保证原始数据的层级不含有死循环结构；
 * 2、不存在节点上下级的数据将被忽略；
 * </strong>
 * @author chendd
 * @date 2020/6/12 15:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Node {

    /**
     * id
     */
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 层级编号
     */
    private Integer level;
    /**
     * 是否叶子节点（叶子节点：是否存在子级）
     */
    private Boolean isLeaf;

    /**
     * 一些其它属性参数
     */
    private Map<String , Object> arrtibutes;

    public Node(String id , String name , String parentId , Map<String , Object> arrtibutes) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.arrtibutes = arrtibutes;
    }

}
