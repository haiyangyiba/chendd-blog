package cn.chendd.blog.admin.system.sysuserrole.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统用户角色关系表
 * @auth chendd
 * @date 2019/11/24 18:03
 */
@Data
@ApiModel
@TableName("sys_user_role")
public class SysUserRole {

    @TableId(value = "surId" , type = IdType.ASSIGN_ID)
    @TableField("surId")
    @ApiModelProperty(value = "主键ID")
    private Long surId;

    @TableField("userId")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @TableField("roleId")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    public SysUserRole(Long userId , Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
