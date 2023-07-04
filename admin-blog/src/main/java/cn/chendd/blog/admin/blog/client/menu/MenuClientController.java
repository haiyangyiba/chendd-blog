package cn.chendd.blog.admin.blog.client.menu;

import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.common.treeview.stratified.Treeview;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单访问
 *
 * @author chendd
 * @date 2020/11/14 11:16
 */
@Api(value = "菜单管理接口" , tags = "系统菜单管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(10)
@RequestMapping(value = "/system/menu")
@Controller
@ApiVersion("1")
public class MenuClientController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "查询博客菜单",notes = "查询提供于前台使用的所有菜单列表，按菜单层级展示")
    @ApiOperationSupport(order = 100)
    @ResponseBody
    public List<Treeview> queryTreeviews() {
        return sysMenuService.queryTreeviews();
    }

}
