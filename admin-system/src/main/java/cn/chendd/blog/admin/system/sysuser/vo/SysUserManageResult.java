package cn.chendd.blog.admin.system.sysuser.vo;

import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理查询结果集
 *
 * @author chendd
 * @date 2020/6/15 19:44
 */
@Data
@ApiModel("用户管理列表查询")
public class SysUserManageResult {

    @ApiModelProperty("账号ID")
    private Long accountId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称/真实姓名")
    private String realName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户状态")
    private EnumStatus status;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("最后登录时间")
    private String lastLoginTime;

    @ApiModelProperty("用户来源")
    private EnumUserSource userSource;

}
