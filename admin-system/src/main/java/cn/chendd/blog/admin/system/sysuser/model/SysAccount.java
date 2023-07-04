package cn.chendd.blog.admin.system.sysuser.model;

import cn.chendd.blog.base.enums.EnumStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户账号
 * @auth chendd
 * @date 2019/11/24 13:54
 */
@Data
@ApiModel
@TableName("sys_account")
public class SysAccount implements Serializable {

    @TableId(value = "accountId" , type = IdType.ASSIGN_ID)
    @TableField("accountId")
    @ApiModelProperty(value = "用户账户主键ID")
    private Long accountId;

    @TableField("username")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @TableField("password")
    @ApiModelProperty(value = "用户密码")
    private String password;

    @TableField("status")
    @ApiModelProperty(value = "用户状态")
    private EnumStatus status;

    @TableField("createDate")
    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    private String dataStatus;


}
