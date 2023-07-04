package cn.chendd.blog.admin.system.sysuser.po;

import cn.chendd.blog.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统用户查询参数对象
 *
 * @author chendd
 * @date 2020/6/15 20:10
 */
@ApiModel
@Data
public class SysUserManageParam extends Query {

    @ApiModelProperty("用户名/昵称")
    private String realName;

    @ApiModelProperty("用户来源")
    List<String> userSource;

    @ApiModelProperty("用户状态")
    List<String> userStatus;

}
