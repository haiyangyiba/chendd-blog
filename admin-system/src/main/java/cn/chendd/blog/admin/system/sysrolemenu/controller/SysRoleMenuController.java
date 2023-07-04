package cn.chendd.blog.admin.system.sysrolemenu.controller;

import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.common.treeview.stratified.Treeview;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统管理-角色管理Controller
 *
 * @author chendd
 * @date 2019/9/18 16:58
 */
@Api(value = "角色菜单管理" , tags = "系统角色菜单管理")
@ApiSort(30)
@RequestMapping(value = "/system/rolemenu" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysRoleMenuController extends BaseController {

    @Resource
    private SysRoleMenuService roleMenuService;

    @GetMapping("/{roleId}")
    @ApiOperation(value = "根据角色查询菜单",notes = "批量查询角色对应菜单明细")
    @ApiOperationSupport(order = 10)
    public String editor(@ApiParam(value = "角色ID")
                         @PathVariable Long roleId){
        List<Treeview> treeviewList = roleMenuService.queryMenusByRole(roleId);
        super.addAttribute("treeview" , JSONObject.toJSONString(treeviewList))
             .addAttribute("roleId" , roleId);
        return "/system/rolemenu/editor";
    }

    @PutMapping("/{roleId}")
    @ApiOperation(value = "根据角色保存菜单",notes = "批量保存角色对应菜单明细")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Integer saveRoleMenu(@ApiParam(value = "角色ID")
                         @PathVariable Long roleId, String menuIds){
        Integer count = roleMenuService.saveRoleMenu(roleId , menuIds);
        return count;
    }

}
