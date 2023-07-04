package cn.chendd.core.common.treeview.stratified;

/**
 * @author chendd
 * @date 2020/6/14 10:12
 */

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 构造Treeview结构数据
 *
 * @author chendd
 * @date 2020/4/14 22:30
 */
@Data
public class Treeview {

    private String id;

    private String text;

    private List<Treeview> nodes;

    /**
     * 菜单状态
     */
    private Map<EnumTreeviewState, Boolean> state;

    /**
     * 层级
     */
    private Integer level;

    /**
     * Tree其它附属属性
     */
    private Map<String , Object> values;

}

