package cn.chendd.blog.admin.system.sysuser.po;

import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户账户登录Vo对象
 *
 * @author chendd
 * @date 2019/11/24 14:36
 */
@ApiModel
@Data
public class SysAccountParam extends FormParam<SysAccount> {

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String validateCode;


}
