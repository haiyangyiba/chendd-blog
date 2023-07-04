package cn.chendd.blog.admin.system.sysuser.model;

import cn.chendd.blog.base.enums.EnumUserSource;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表
 * @auth chendd
 * @date 2019/11/24 13:54
 */
@Data
@ApiModel
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(value = "userId" , type = IdType.ASSIGN_ID)
    @TableField("userId")
    @ApiModelProperty(value = "用户信息的主键ID")
    private Long userId;

    @TableField("userNumber")
    @ApiModelProperty(value = "用户ID，第三方用户时的用户编号")
    private String userNumber;

    @TableField("realName")
    @ApiModelProperty(value = "真实姓名，第三方登录用户时的用户昵称")
    private String realName;

    @TableField("portrait")
    @ApiModelProperty(value = "用户头像地址")
    private String portrait;

    @TableField("email")
    @ApiModelProperty(value = "用户邮箱地址")
    private String email;

    @TableField("createTime")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField("lastLoginTime")
    @ApiModelProperty(value = "最后一次登录时间")
    private String lastLoginTime;

    @TableField("userSource")
    @ApiModelProperty(value = "用户来源")
    private EnumUserSource userSource;

    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    private String dataStatus;

    @TableField("accountId")
    @ApiModelProperty(value = "账号ID")
    private Long accountId;

}
