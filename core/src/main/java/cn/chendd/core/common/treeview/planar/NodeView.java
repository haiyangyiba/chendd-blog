package cn.chendd.core.common.treeview.planar;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 节点展示
 *
 * @author chendd
 * @date 2020/6/12 20:17
 */
public class NodeView {

    /**
     * 根节点ID
     */
    public static final String ROOT_ID = "-1";
    /**
     * 最大递归深度
     */
    public static final Integer MAX_DEPTH = 20;

    private String rootId;
    private List<Node> dataList;
    private Integer depth;

    private NodeView(String rootId , List<Node> dataList , Integer depth) {
        this.rootId = rootId;
        this.dataList = dataList;
        this.depth = depth;
    }

    public static NodeView newInstance(String rootId , List<Node> dataList , Integer depth) {
        return new NodeView(rootId , dataList , depth);
    }

    public static NodeView newInstance(List<Node> dataList) {
        return new NodeView(ROOT_ID , dataList , MAX_DEPTH);
    }

    /**
     * @return 数据转换
     */
    public List<Node> convert() {
        List<Node> newList = new ArrayList<>();
        List<Node> sonNodes = this.getChildrensByRoot();//获取全部第一级节点
        for (Node sonNode : sonNodes) {
            newList.add(sonNode);
            String id = sonNode.getId();
            recursionNode(newList , sonNode);
        }
        return newList;
    }

    /**
     * @return 获取根节点下的子节点
     */
    private List<Node> getChildrensByRoot() {
        List<Node> nodes = new ArrayList<>();
        for (Node node : dataList) {
            String parentId = String.valueOf(node.getParentId());
            if(StringUtils.equals(parentId , rootId)) {
                nodes.add(new Node(node.getId() , node.getName() , node.getParentId() , 1 , Boolean.TRUE , node.getArrtibutes()));
            }
        }
        return nodes;
    }

    /**
     * 递归当前节点查找所有子级节点
     * @param nodes 构造新的所有节点集合
     * @param node 当前递归节点
     */
    private void recursionNode(List<Node> nodes , Node node) {
        String id = node.getId();
        if(node.getLevel() >= depth) {
            return ;
        }
        for (Node all : dataList) {
            String parentId = all.getParentId();
            if(StringUtils.equals(id , parentId)) {
                node.setIsLeaf(Boolean.FALSE);
                Node child = new Node(all.getId() , all.getName() , all.getParentId() , node.getLevel() + 1 , Boolean.TRUE , all.getArrtibutes());
                nodes.add(child);
                this.recursionNode(nodes , child);
            }
        }
    }
}
