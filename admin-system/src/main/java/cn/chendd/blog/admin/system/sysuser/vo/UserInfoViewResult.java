package cn.chendd.blog.admin.system.sysuser.vo;

import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息查看
 *
 * @author chendd
 * @date 2020/6/20 22:49
 */
@Data
@ApiModel
public class UserInfoViewResult {

    @ApiModelProperty("账号ID")
    private Long accountId;
    @ApiModelProperty("用户名称")
    private String username;
    @ApiModelProperty("用户状态")
    private EnumStatus status;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("用户头像")
    private String portrait;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("创建日期")
    private String createTime;
    @ApiModelProperty("最后登录时间")
    private String lastLoginTime;
    @ApiModelProperty("用户来源ID")
    private String userNumber;
    @ApiModelProperty("用户来源")
    private EnumUserSource userSource;

}
