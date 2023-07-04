package cn.chendd.blog.admin.system.sysrolemenu.vo;

import cn.chendd.blog.base.enums.EnumMenuType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单vo定义
 *
 * @author chendd
 * @date 2019/10/20 17:14
 */
@Data
public class MenuResult {

    @ApiModelProperty("菜单ID")
    private Long menuId;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单类型")
    private EnumMenuType menuType;
    @ApiModelProperty("菜单图标")
    private String menuIcon;
    @ApiModelProperty("上级菜单")
    private String parentId;
    @ApiModelProperty("菜单标识")
    private String menuKey;

}
