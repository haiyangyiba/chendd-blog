package cn.chendd.blog.admin.system.sysrole.controller;

import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.admin.system.sysrole.service.SysRoleService;
import cn.chendd.blog.admin.system.sysrole.vo.SysRoleParam;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
@ApiSort(20)
@Api(value = "角色管理" , tags = "系统角色管理")
@RequestMapping(value = "/system/role")
@Controller
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    @GetMapping
    @ApiOperation(value = "角色管理" , notes = "角色管理页面")
    @ApiOperationSupport(order = 1)
    public String roleManage() {

        return "/system/role/roleManage";
    }

    @GetMapping("/role")
    @ApiOperation(value = "角色编辑" , notes = "角色新增修改页面")
    @ApiOperationSupport(order = 2)
    public String role() {

        return "/system/role/role";
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "新增与修改角色",notes = "新增与修改角色（<b>根据ID是否为空区分</b>）")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public SysRole role(
            @ApiParam(value = "角色对象")
            @RequestBody SysRole role){
        String roleName = role.getRoleName();
        if(StringUtils.isBlank(roleName)) {
            throw new ValidateDataException("角色名称不能为空！");
        }
        String remark = role.getRemark();
        if(StringUtils.isNotEmpty(remark)) {
            if(remark.length() > 200) {
                throw new ValidateDataException("角色描述不能超过200个字符！");
            }
            remark = remark.replace(Constant.JAVA_ENTER_CHAR , Constant.HTML_ENTER_CHAR);
            role.setRemark(remark);
        }
        SysRole sysRole = sysRoleService.saveSysRole(role);
        return sysRole;
    }

    @GetMapping("/{roleId}")
    @ApiOperation(value = "根据ID查询",notes = "查询角色对象" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ApiOperationSupport(order = 60)
    public String role(
            @PathVariable(required = true)
            @ApiParam(value = "角色ID")
                    Long roleId) {

        SysRole sysRole = sysRoleService.getById(roleId);
        if(StringUtils.isNotEmpty(sysRole.getRemark())) {
            sysRole.setRemark(StringUtils.replace(sysRole.getRemark() , Constant.HTML_ENTER_CHAR , Constant.JAVA_ENTER_CHAR));
        }
        super.addAttribute("sysRole" , sysRole);
        return "/system/role/role";
    }

    @PostMapping
    @ApiOperation(value = "查询角色-按条件",notes = "查询所有角色对象信息" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public List<SysRole> queryRoleList(SysRoleParam role) throws Exception {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(role , sysRole);
        List<SysRole> roleList = sysRoleService.queryRoles(sysRole);
        return roleList;
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色",notes = "批量删除角色" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Integer removeRoles(@RequestParam List<Long> ids){
        Integer result = sysRoleService.removeRoles(ids);
        return result;
    }

}

