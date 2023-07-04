package cn.chendd.blog.admin.system.sysuserrole.controller;

import cn.chendd.blog.admin.system.sysuserrole.service.SysUserRoleService;
import cn.chendd.blog.admin.system.sysuserrole.vo.UserRoleResult;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户与角色管理
 *
 * @author chendd
 * @date 2020/6/25 20:41
 */
@Api(value = "系统用户管理" , tags = "系统用户管理")
@ApiSort(50)
@RequestMapping(value = "/system/userrole" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysUserRoleController extends BaseController {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "根据用户查询角色信息",notes = "根据用户查询角色信息")
    @ApiOperationSupport(order = 10)
    public String editor(@ApiParam(value = "用户ID")
                         @PathVariable Long userId) {
        List<UserRoleResult> dataList = sysUserRoleService.queryRolesByUserId(userId);
        super.addAttribute("dataList" , dataList).addAttribute("userId" , userId);
        return "/system/userrole/editor";
    }

    @PostMapping("/{userId}")
    @ApiOperation(value = "保存用户的角色",notes = "存储选择用户对应的角色数据")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult save(@ApiParam(value = "用户ID")
                           @PathVariable Long userId ,
                           @ApiParam(value = "角色ID，以逗号分割的多个")
                           String roleIds) {
        List<Long> roleList = Lists.newArrayList();
        String[] split = StringUtils.split(roleIds , ",");
        if(split != null) {
            for (String s : split) {
                roleList.add(Long.valueOf(s));
            }
        }
        sysUserRoleService.saveBatch(userId , roleList);
        return new SuccessResult<Integer>(split != null ? split.length : 0);
    }

}
