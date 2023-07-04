package cn.chendd.blog.admin.system.sysrolemenu.vo;

import cn.chendd.blog.base.enums.EnumMenuType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单vo定义
 *
 * @author chendd
 * @date 2019/10/27 20:26
 */
@Data
public class SysRoleMenuDTO {

    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("菜单ID")
    private Long menuId;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单类型")
    private EnumMenuType menuType;
    @ApiModelProperty("菜单图标")
    private String menuIcon;
    @ApiModelProperty("上级菜单")
    private Long parentId;
    @ApiModelProperty("菜单标识")
    private String menuKey;
    @ApiModelProperty("菜单d地址")
    private String menuUrl;

    @ApiModelProperty("子级菜单集合")
    private List<SysRoleMenuDTO> roleMenus;

}
