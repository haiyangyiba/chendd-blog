package cn.chendd.blog.admin.system.client.sysuser;

import cn.chendd.blog.admin.system.sysuser.service.SysUserService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户ClientController
 *
 * @author chendd
 * @date 2021/4/29 10:27
 */
@Api(value = "系统用户管理V1" , tags = "系统用户管理V1")
@ApiSort(40)
@RequestMapping(value = "/system/user" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@ApiVersion("1")
public class SysUserClientController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/newest")
    @ApiOperation(value = "获取最新用户注册数据",notes = "查询最新注册前N条用户数据")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public List<UserNewestDto> queryUserNewestList() {
        List<UserNewestDto> dataList = this.sysUserService.queryUserNewestList(6);
        return dataList;
    }

    @GetMapping("{userId}")
    @ApiOperation(value = "用户信息",notes = "获取用户基本信息")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public UserInfoResult queryUserInfo(@ApiParam(name="userId" , value="用户Id") @PathVariable Long userId) {
        return sysUserService.queryUserInfo(userId);
    }

    @PutMapping("{userId}")
    @ApiOperation(value = "修改Email",notes = "根据用户Id修改Email")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public void updateEmailByUserId(@ApiParam(name="userId" , value="用户Id") @PathVariable String userId ,
                                        @ApiParam(name="email" , value="Email") @RequestBody String email) {
        sysUserService.updateEmailByUserId(userId , email);
    }

}
