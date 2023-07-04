package cn.chendd.blog.admin.system.sysuserrole.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色结果集
 *
 * @author chendd
 * @date 2020/6/25 21:35
 */
@Data
@ApiModel("用户与角色查询")
public class UserRoleResult {

    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色标识")
    private String roleKey;
    @ApiModelProperty("是否选中")
    private String isChecked;

}
