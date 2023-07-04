package cn.chendd.blog.admin.system.sysmenu.vo;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统菜单查询分页对象
 *
 * @author chendd
 * @date 2019/10/26 22:12
 */
@Getter
@Setter
public class SysMenuParam extends FormParam<SysMenu> {

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单标识")
    private String menuKey;

}
