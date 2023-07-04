package cn.chendd.core.common.treeview.stratified;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author chendd
 * @date 2020/6/14 10:15
 */
@Getter
@Setter
@NoArgsConstructor
public class Tree {

    private String id;
    private String parentId;
    private String name;

    public Tree(String id , String parentId , String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    /**
     * 存储其它依附属性值
     */
    private Map<String , Object> values;

}
