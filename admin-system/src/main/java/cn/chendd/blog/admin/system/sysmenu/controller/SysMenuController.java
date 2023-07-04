package cn.chendd.blog.admin.system.sysmenu.controller;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuLevelResult;
import cn.chendd.blog.admin.system.sysmenu.vo.SysMenuParam;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumMenuType;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统菜单管理Controller
 *
 * @author chendd
 * @date 2019/10/20 18:01
 */
@Api(value = "菜单管理" , tags = "系统菜单管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(10)
@RequestMapping(value = "/system/menu")
@Controller
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping()
    @ApiOperation(value = "菜单管理" , notes = "菜单管理页面")
    @ApiOperationSupport(order = 10)
    public String menuManage() {

        return "/system/menu/menuManage";
    }

    @GetMapping("/menu")
    @ApiOperation(value = "菜单编辑" , notes = "菜单新增修改页面")
    @ApiOperationSupport(order = 20)
    public String menu() {
        super.addAttribute("menuStatus" , EnumStatus.values())
                .addAttribute("menuTypes" , EnumMenuType.values())
                .addAttribute("requestMethods" , RequestMethod.values());
        return "/system/menu/menu";
    }

    @PostMapping
    @ApiOperation(value = "查询菜单-按条件",notes = "按条件查询所有菜单数据列表")
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public List<SysMenuLevelResult> queryMenuList(SysMenuParam menuParam) {
        List<SysMenu> menuList = sysMenuService.queryAll();
        //将菜单列表数据转换
        List<SysMenuLevelResult> dataList = sysMenuService.convertMenuList(menuList , menuParam);
        return dataList;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "新增与修改菜单",notes = "新增与修改菜单（<b>根据ID是否为空区分</b>）")
    @ApiOperationSupport(order = 50)
    @ResponseBody
    public SysMenu saveSysMenu(@ApiParam(value = "菜单对象")
                               @RequestBody SysMenu menu){
        this.validatorSysMenu(menu);
        String remark = menu.getRemark();
        if(StringUtils.isNotEmpty(remark)) {
            if(remark.length() > 200) {
                throw new ValidateDataException("菜单描述不能超过200个字符！");
            }
            remark = remark.replace(Constant.JAVA_ENTER_CHAR , Constant.HTML_ENTER_CHAR);
            menu.setRemark(remark);
        }
        SysMenu sysMenu = sysMenuService.saveSysMenu(menu);
        return sysMenu;
    }

    @DeleteMapping("/{menuId}")
    @ApiOperation(value = "删除菜单",notes = "批量删除菜单" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public Integer removeMenus(@ApiParam(value = "菜单ids")
                               @PathVariable Long menuId){
       Integer result = sysMenuService.removeMenus(Lists.newArrayList(menuId));
       return result;
    }

    @GetMapping("/view_{menuId}")
    @ApiOperation(value = "根据ID查询",notes = "新增与修改菜单（<b>根据ID是否为空区分</b>）")
    @ApiOperationSupport(order = 70)
    public String viewSysMenu(@PathVariable(required = true)
                               @ApiParam(value = "菜单ID")
                               Long menuId){
        this.sysMenu(menuId);
        return "/system/menu/view";
    }

    @GetMapping("/{menuId}")
    @ApiOperation(value = "根据ID查询",notes = "新增与修改菜单（<b>根据ID是否为空区分</b>）")
    @ApiOperationSupport(order = 80)
    public String sysMenu(@PathVariable(required = true)
                               @ApiParam(value = "菜单ID")
                               Long menuId){
        SysMenu sysMenu = sysMenuService.getById(menuId);
        super.addAttribute("sysMenu" , sysMenu)
              .addAttribute("menuStatus" , EnumStatus.values())
              .addAttribute("menuTypes" , EnumMenuType.values())
              .addAttribute("requestMethods" , RequestMethod.values());
        if(StringUtils.isNotEmpty(sysMenu.getRemark())) {
            sysMenu.setRemark(StringUtils.replace(sysMenu.getRemark() , Constant.HTML_ENTER_CHAR , Constant.JAVA_ENTER_CHAR));
        }
        return "/system/menu/menu";
    }

    @GetMapping("/selectMenu/{menuId}")
    @ApiOperation(value = "菜单管理" , notes = "菜单管理页面")
    @ApiOperationSupport(order = 90)
    public String selectMenu(@ApiParam("菜单ID") @PathVariable Long menuId) {
        super.addAttribute("menuId" , menuId);
        return "/system/menu/selectMenu";
    }

    @PostMapping("/selectMenu")
    @ApiOperation(value = "查询菜单-按条件匹配查询",notes = "按条件查询所有角色数据数据")
    @ApiOperationSupport(order = 100)
    @ResponseBody
    public List<SysMenuLevelResult> selectMenu(@ApiParam("查询条件") SysMenuParam menuParam) {
        menuParam.setMenuKey("#blog_");
        List<SysMenu> menuList = sysMenuService.querySysMenus(menuParam);
        //将菜单列表数据转换
        List<SysMenuLevelResult> dataList = sysMenuService.convertMenuList(menuList , menuParam);
        return dataList;
    }

    /**
     * 校验菜单数据是否合法
     */
    private void validatorSysMenu(SysMenu menu) {
        if(menu == null) {
            throw new ValidateDataException("非法的参数提交！");
        }
        String menuName = menu.getMenuName();
        if(StringUtils.isBlank(menuName)) {
            throw new ValidateDataException("菜单名称不能为空！");
        }
        EnumMenuType menuType = menu.getMenuType();
        String menuUrl = menu.getMenuUrl();
        String menuKey = menu.getMenuKey();
        if(EnumMenuType.MENU.equals(menuType) && StringUtils.isBlank(menuUrl)) {
            throw new ValidateDataException(String.format("菜单类型为： %s，[菜单地址] 不能为空！" , EnumMenuType.MENU.getText()));
        } else if(EnumMenuType.BUTTON.equals(menuType) && StringUtils.isBlank(menuKey)) {
            throw new ValidateDataException(String.format("菜单类型为： %s，[菜单标识] 不能为空！" , EnumMenuType.BUTTON.getText()));
        }
        //菜单请求方式
        if (menu.getRequestMethod() == null) {
            throw new ValidateDataException("菜单请求方式不能为空！");
        }

        EnumStatus status = menu.getMenuStatus();
        if(status == null) {
            throw new ValidateDataException("菜单状态不能为空！");
        }
    }
}
