package cn.chendd.blog.admin.system.sysuser.controller;

import cn.chendd.blog.admin.system.sysuser.po.SysUserManageParam;
import cn.chendd.blog.admin.system.sysuser.po.SysUserSaveParam;
import cn.chendd.blog.admin.system.sysuser.service.SysUserService;
import cn.chendd.blog.admin.system.sysuser.vo.SysUserManageResult;
import cn.chendd.blog.admin.system.sysuser.vo.UserInfoViewResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统管理-用户管理Controller
 *
 * @author chendd
 * @date 2020/6/14 19:22
 */
@Api(value = "系统用户管理" , tags = "系统用户管理")
@ApiSort(40)
@RequestMapping(value = "/system/user" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping
    @ApiOperation(value = "用户管理主页面",notes = "跳转至用户管理主页面")
    @ApiOperationSupport(order = 10)
    public String userManage() {
        super.addAttribute("userSources" , EnumUserSource.values())
             .addAttribute("userStatus" , EnumStatus.values());
        return "/system/user/userManage";
    }

    @PostMapping
    @ApiOperation(value = "用户分页查询",notes = "用户查询分页")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<SysUserManageResult> userManage(SysUserManageParam userParam) {
        Page<SysUserManageResult> page = sysUserService.querySysUserPage(userParam);
        return page;
    }

    @GetMapping("/user")
    @ApiOperation(value = "用户新增页面",notes = "跳转至用户新增页面")
    @ApiOperationSupport(order = 30)
    public String user() {

        return "/system/user/user";
    }

    @GetMapping("/account/{username}")
    @ApiOperation(value = "检查用户名是否存在",notes = "检查用户名是否存在")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public Boolean checkUser(@PathVariable("username") String username) {
        return sysUserService.querySysUserByUsername(username) == null;
    }

    @PutMapping
    @ApiOperation(value = "用户管理主页面",notes = "跳转至用户管理主页面")
    @ApiOperationSupport(order = 50)
    @ResponseBody
    public BaseResult saveUser(@RequestBody SysUserSaveParam param) {
        Long userId = param.getUserId();
        if(userId == null) {
            this.validatParam(param);
            return sysUserService.saveUser(param);
        } else {
            return sysUserService.editUser(param);
        }
    }

    @GetMapping("/view_{userId}")
    @ApiOperation(value = "查看用户详细",notes = "跳转至用户查看页面")
    @ApiOperationSupport(order = 60)
    public String view(@PathVariable Long userId) {
        UserInfoViewResult userInfo = sysUserService.getUserInfoViewById(userId);
        super.addAttribute("userInfo" , userInfo);
        return "/system/user/view";
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "修改用户数据",notes = "跳转至用户修改页面")
    @ApiOperationSupport(order = 70)
    public String edit(@PathVariable Long userId) {
        UserInfoViewResult userInfo = sysUserService.getUserInfoViewById(userId);
        super.addAttribute("userInfo" , userInfo);
        return "/system/user/user";
    }

    @GetMapping("/enable_{userId}")
    @ApiOperation(value = "修改用户数据",notes = "跳转至用户修改页面")
    @ApiOperationSupport(order = 80)
    @ResponseBody
    public BaseResult enable(@PathVariable Long userId) {
        sysUserService.editUserStatus(EnumStatus.ENABLE.getValue() , userId);
        return new SuccessResult<String>("用户已启用！");
    }

    @GetMapping("/disable_{userId}")
    @ApiOperation(value = "修改用户数据",notes = "跳转至用户修改页面")
    @ApiOperationSupport(order = 90)
    @ResponseBody
    public BaseResult disable(@PathVariable Long userId) {
        sysUserService.editUserStatus(EnumStatus.DISABLE.getValue() , userId);
        return new SuccessResult<String>("用户已禁用！");
    }

    @GetMapping("/password_{accountId}")
    @ApiOperation(value = "修改用户数据",notes = "跳转至用户修改页面")
    @ApiOperationSupport(order = 100)
    public String password(@PathVariable Long accountId) {
        super.addAttribute("accountId" , accountId);
        return "/system/user/password";
    }

    @PutMapping("/password_{accountId}")
    @ApiOperation(value = "修改用户数据",notes = "跳转至用户修改页面")
    @ApiOperationSupport(order = 110)
    @ResponseBody
    public BaseResult resetPassword(@PathVariable Long accountId , @RequestBody String password) {
        if(StringUtils.length(StringUtils.trim(password)) < 8) {
            throw new ValidateDataException("请输入8位以上长度的密码！");
        }
        sysUserService.resetPassword(accountId , password);
        return new SuccessResult<String>(String.format("已重置为 [%s]！" , password));
    }

    @PutMapping("/third")
    @ApiOperation(value = "存储第三方用户登录信息",notes = "用户存储")
    @ApiOperationSupport(order = 120)
    @ApiVersion("1")
    @ResponseBody
    public SysUserResult thirdUserStore(@RequestBody ThirdUserResult userResult) {

        return sysUserService.thirdUserStore(userResult);
    }

    /**
     * 参数验证
     */
    private void validatParam(SysUserSaveParam param) {
        String username = param.getUsername();
        if(StringUtils.isBlank(username)) {
            throw new ValidateDataException("用户名称不能为空！");
        }
        String password = param.getPassword();
        if(StringUtils.isBlank(password)) {
            throw new ValidateDataException("用户密码不能为空！");
        }
        if(StringUtils.length(password) < 8) {
            throw new ValidateDataException("用户密码长度不能小于8位！");
        }
        String passwordCheck = param.getPasswordCheck();
        if(! StringUtils.equals(password , passwordCheck)) {
            throw new ValidateDataException("用户密码与确认密码不一致！");
        }

    }

}
