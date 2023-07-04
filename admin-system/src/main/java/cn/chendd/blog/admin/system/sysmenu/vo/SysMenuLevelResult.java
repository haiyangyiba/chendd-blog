package cn.chendd.blog.admin.system.sysmenu.vo;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.base.enums.EnumMenuType;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.core.common.treeview.planar.Node;
import cn.chendd.core.common.treeview.planar.NodeView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 菜单层级显示
 *
 * @author chendd
 * @date 2020/6/12 16:07
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
public class SysMenuLevelResult {

    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("上级菜单ID")
    private String parentId;

    @ApiModelProperty("菜单层级")
    private Integer level;

    @ApiModelProperty("是否存在子节点")
    private Boolean isLeaf;

    @ApiModelProperty("菜单类型")
    private EnumMenuType menuType;

    @ApiModelProperty("可用状态")
    private EnumStatus menuStatus;

    @ApiModelProperty("菜单地址")
    private String menuUrl;

    @ApiModelProperty("菜单标识")
    private String menuKey;

    @ApiModelProperty("请求类型")
    private RequestMethod requestMethod;

    @ApiModelProperty("排列序号")
    private String menuOrder;

    @ApiModelProperty("菜单图标")
    private String menuIcon;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("修改时间")
    private String updateDate;

    @ApiModelProperty("菜单名称是否匹配")
    private Boolean menuNameMatch;

    @ApiModelProperty("菜单标识是否匹配")
    private Boolean menuKeyMatch;

    public SysMenuLevelResult(Node node , SysMenu menu, SysMenuParam menuParam) {
        //menu
        BeanUtils.copyProperties(menu , this);
        //node
        BeanUtils.copyProperties(node , this);
        //类型不一致的手动赋值
        this.setMenuId(menu.getMenuId().toString());
        //treegird默认的根节点ID为null或者 "" 或者 0
        if(NodeView.ROOT_ID.equals(this.getParentId())){
            this.setParentId("");
        }
        //查询条件
        String menuName = menuParam.getMenuName();
        String menuKey = menuParam.getMenuKey();
        this.setMenuNameMatch(
                StringUtils.isNotEmpty(this.getMenuName()) &&
                StringUtils.isNotEmpty(menuName) &&
                StringUtils.contains(this.getMenuName() , menuName));
        this.setMenuKeyMatch(
                StringUtils.isNotEmpty(this.getMenuKey()) &&
                StringUtils.isNotEmpty(menuKey) &&
                StringUtils.contains(this.getMenuKey() , menuKey));
    }
}
