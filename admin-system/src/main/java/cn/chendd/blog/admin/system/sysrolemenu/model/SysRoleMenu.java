package cn.chendd.blog.admin.system.sysrolemenu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统角色菜单关系表
 * @auth chendd
 * @date 2020/06/08 21:27
 */
@Data
@ApiModel
@TableName("sys_role_menu")
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenu {

    @TableId(value = "rmId" , type = IdType.ASSIGN_ID)
    @TableField("rmId")
    @ApiModelProperty(value = "系统角色菜单关系表，主键ID")
    private Long rmId;

    @TableField("roleId")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @TableField("menuId")
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
