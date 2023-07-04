package cn.chendd.blog.admin.system.sysuser.controller;

import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.admin.system.sysuser.po.SysAccountParam;
import cn.chendd.blog.admin.system.sysuser.service.ISysAccountService;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.utils.Codec;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.chendd.core.common.constant.Constant.LOGIN_VALIDATE_KEY;
import static cn.chendd.core.common.constant.Constant.SYSTEM_CURRENT_USER;

/**
 * 系统登录功能
 *
 * @author chendd
 * @date 2019/11/23 15:42
 */
@Api(value = "系统内用户登录" , tags = "系统内用户登录")
@ApiSort(30)
@RequestMapping(value = "/system/login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysUserLoginController extends BaseController {

    @Resource
    private ISysAccountService sysAccountService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @PostMapping
    @ApiOperation(value = "账号密码登录",notes = "查询用户角色菜单等对象信息")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public SysUserResult login(SysAccountParam sysAccountParam) throws ValidateDataException {
        try{
            //验证用户登录
            SysAccount sysAccount = validateLogin(sysAccountParam);
            //处理登录
            SysUserResult userEntity = sysRoleMenuService.putUserResult(sysAccount);
            //将当期用户的信息存储至session，包括用户的角色权限，session中的对象类型为JSON
            session.setAttribute(SYSTEM_CURRENT_USER, JSONObject.toJSON(userEntity));
            sysRoleMenuService.loginUser(userEntity.getAccount().getUsername());
            return userEntity;
        } finally {
            //清空验证码
            super.session.removeAttribute(LOGIN_VALIDATE_KEY);
        }
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "用户退出",notes = "用户退出销毁session")
    @ApiOperationSupport(order = 20)
    public String login() {
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        if(userResult != null) {
            sysRoleMenuService.logoutUser(userResult.getAccount().getUsername());
        }
        super.session.invalidate();
        return "redirect:/login.html";
    }

    @PutMapping(consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "获取用户登录后的数据",notes = "获取用户登录后的数据")
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public SysUserResult getLoginInfo() {
        //从前端过来的session与后端存在细微差异，故从session中获取用户数据对象，再查询一下统一返回至后端
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        String username = userResult.getAccount().getUsername();
        SysAccount sysAccount = this.sysAccountService.querySysAccountByUsername(username);
        SysUserResult userEntity = sysRoleMenuService.putUserResult(sysAccount);
        return userEntity;
    }

    /**
     * 验证用户名与密码
     * @param sysAccountParam 登录参数
     * @return 用户账户
     */
    private SysAccount validateLogin(SysAccountParam sysAccountParam) {
        String validateCode = sysAccountParam.getValidateCode();
        if(StringUtils.isEmpty(validateCode)){
            throw new ValidateDataException("验证码无效");
        }
        String sessionValidateCode = (String) session.getAttribute(LOGIN_VALIDATE_KEY);
        if(! StringUtils.equalsIgnoreCase(sessionValidateCode , validateCode)){
            throw new ValidateDataException("验证码输入错误");
        }
        String username = sysAccountParam.getUsername();
        String password = sysAccountParam.getPassword();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new ValidateDataException("用户名或密码不能为空");
        }
        SysAccount sysAccount = this.sysAccountService.querySysAccountByUsername(username);
        if(sysAccount == null){
            throw new ValidateDataException("用户名或密码错误");
        }
        if(! Codec.hasRightSecurityCode(password , sysAccount.getPassword())) {
            throw new ValidateDataException("用户名或密码错误");
        }
        if(! EnumStatus.ENABLE.equals(sysAccount.getStatus())){
            throw new ValidateDataException("用户状态不可用");
        }
        return sysAccount;
    }

}
