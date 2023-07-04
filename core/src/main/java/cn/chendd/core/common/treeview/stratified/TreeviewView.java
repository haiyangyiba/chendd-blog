package cn.chendd.core.common.treeview.stratified;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author chendd
 * @date 2020/6/14 10:14
 */
public class TreeviewView {

    /**
     * 根节点ID
     */
    public static final String ROOT_ID = "-1";
    /**
     * 最大递归深度
     */
    public static final Integer MAX_DEPTH = 20;

    private String rootId;
    private List<Tree> dataList;
    private Integer depth;

    private TreeviewView(String rootId , List<Tree> dataList , Integer depth) {
        this.rootId = rootId;
        this.dataList = dataList;
        this.depth = depth;
    }

    public static TreeviewView newInstance(String rootId , List<Tree> dataList , Integer depth) {
        return new TreeviewView(rootId , dataList , depth);
    }

    public static TreeviewView newInstance(List<Tree> dataList) {
        return new TreeviewView(ROOT_ID , dataList , MAX_DEPTH);
    }

    public List<Treeview> convert(List<TreeviewState> stateList) {
        Stream<Tree> rootList = dataList.stream().filter(tree -> rootId.equals(tree.getParentId()));
        List<Treeview> treeviewList = new ArrayList<>();
        this.convertTreeview(rootList, treeviewList , stateList);
        for (Treeview treeview : treeviewList) {
            this.collectList(treeview , this.dataList , stateList);
        }
        return treeviewList;
    }

    /**
     * 获取原始数据中的一级菜单
     */
    private void convertTreeview(Stream<Tree> cityStream, List<Treeview> treeviewList , List<TreeviewState> stateList) {
        cityStream.forEach(tree -> {
            Treeview treeview = new Treeview();
            treeview.setId(tree.getId());
            treeview.setText(tree.getName());
            treeview.setState(this.getTreeviewState(tree.getId() , stateList));
            treeview.setNodes(null);
            treeview.setLevel(1);
            treeview.setValues(tree.getValues());
            treeviewList.add(treeview);
        });
    }

    /**
     * 根据id获取对应id应该选中的数据状态
     */
    private Map<EnumTreeviewState , Boolean> getTreeviewState(String id , List<TreeviewState> stateList) {
        if(stateList != null) {
            for (TreeviewState treeviewState : stateList) {
                if(StringUtils.equals(treeviewState.getId() , id)) {
                    return treeviewState.getTreeviewState();
                }
            }
        }
        return null;
    }

    /**
     * 遍历一级菜单，并递归调用字级菜单，查找对应的菜单节点
     */
    private void collectList(Treeview treeview, List<Tree> list , List<TreeviewState> stateList) {
        Integer level = treeview.getLevel();
        if(level > depth) {
            return;
        }
        String nodeId = treeview.getId();
        for (Tree tree : list) {
            String parentId = tree.getParentId();
            if(parentId != null && parentId.equals(nodeId)){
                if(treeview.getNodes() == null){
                    treeview.setNodes(new ArrayList<>());
                }
                Treeview childMenuTreeview = new Treeview();
                childMenuTreeview.setId(tree.getId());
                childMenuTreeview.setText(tree.getName());
                childMenuTreeview.setState(this.getTreeviewState(tree.getId() , stateList));
                childMenuTreeview.setLevel(treeview.getLevel() + 1);
                childMenuTreeview.setValues(tree.getValues());
                treeview.getNodes().add(childMenuTreeview);
                this.collectList(childMenuTreeview , list , stateList);
            }
        }
    }

}
