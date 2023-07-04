package cn.chendd.blog.client.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户基本信息数据对象
 *
 * @author chendd
 * @date 2022/4/5 16:30
 */
@Data
public class UserInfoResult {

    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("头像")
    private String portrait;
    @ApiModelProperty("Email")
    private String email;
    @ApiModelProperty("注册时间")
    private String createTime;
    @ApiModelProperty("最后登录时间")
    private String lastLoginTime;
    @ApiModelProperty("用户来源")
    private String userSource;

}
