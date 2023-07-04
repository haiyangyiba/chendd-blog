package cn.chendd.blog.admin.system.sysrole.vo;

import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统角色查询条件对象
 *
 * @author chendd
 * @date 2019/10/20 20:11
 */
@ApiModel
@Data
public class SysRoleParam extends FormParam<SysRole> {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色名称")
    private String roleKey;

}
