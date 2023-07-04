package cn.chendd.blog.admin.system.sysrole.vo;

import cn.chendd.blog.admin.system.sysrolemenu.vo.SysRoleMenuDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统用户的角色对应菜单数据
 *
 * @author chendd
 * @date 2021/5/22 23:09
 */
@Data
public class SysUserRoleDTO {

    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("角色标识")
    private String roleKey;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色菜单集合")
    List<SysRoleMenuDTO> menuList;

}
