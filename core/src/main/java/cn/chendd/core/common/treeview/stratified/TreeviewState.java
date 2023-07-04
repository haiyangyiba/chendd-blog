package cn.chendd.core.common.treeview.stratified;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * treeview选中状态
 *
 * @author chendd
 * @date 2020/6/14 18:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeviewState {

    private String id;
    private Map<EnumTreeviewState , Boolean> treeviewState;

}
