package cn.chendd.blog.admin.system.sysuser.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统用户保存参数对象
 *
 * @author chendd
 * @date 2020/6/20 12:18
 */
@ApiModel
@Data
public class SysUserSaveParam {

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户名称")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("确认密码")
    private String passwordCheck;
    @ApiModelProperty("昵称/真实名称")
    private String realName;
    @ApiModelProperty("用户email")
    private String email;
    @ApiModelProperty("用户头像")
    private String userImage;

}
