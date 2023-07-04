package cn.chendd.blog.admin.system.sysrole.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息实体对象
 *
 * @author chendd
 * @date 2019/9/19 13:16
 */
@Data
@ApiModel
@TableName("sys_role")
public class SysRole implements Serializable {

    @ApiModelProperty(value = "角色ID" , example = "1001")
    @TableId(value = "roleId" , type = IdType.ASSIGN_ID)
    @TableField("roleId")
    private Long roleId;

    @ApiModelProperty(value = "角色名称" , example = "管理员")
    @TableField("roleName")
    private String roleName;

    @ApiModelProperty(value = "创建日期" , example = "2019-05-20 05:20:00")
    @TableField("createDate")
    private String createDate;

    @ApiModelProperty(value = "唯一标识" , example = "role_admin")
    @TableField("roleKey")
    private String roleKey;

    @ApiModelProperty(value = "备注信息" , example = "管理员角色")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "修改时间" , example = "2019-05-20 05:20:00")
    @TableField("updateDate")
    private String updateDate;

    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    private String dataStatus;

}


