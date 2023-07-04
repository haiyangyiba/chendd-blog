package cn.chendd.blog.base.model;

import cn.chendd.blog.base.enums.EnumUserSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方登录用户对象
 *
 * @author chendd
 * @date 2019/12/28 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ThirdUserResult {

    @ApiModelProperty(value = "用户ID，第三方用户时的用户编号")
    private String userNumber;

    @ApiModelProperty(value = "用户名称，第三方登录用户时的用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像地址")
    private String portrait;

    @ApiModelProperty(value = "用户来源")
    private EnumUserSource userSource;

    @ApiModelProperty(value = "重定向地址，一般用于外站请求")
    private String redirect;

}
