package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 前台参数值交互Controller
 *
 * @author chendd
 * @date 2021/2/9 21:51
 */
@Api(value = "系统内登录" , tags = "系统内用户登录")
@ApiSort(10)
@Controller
public class UserLoginController extends BaseController {

    @Value("${forest.variables.serverAdminPath}")
    private String url;

    @GetMapping("/login")
    @ApiOperation(value = "用户登录" , notes = "系统内用户登录")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public void login() throws IOException {
        super.response.sendRedirect(url);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "处理第三方登录用户退出" , notes = "用户退出")
    @ApiOperationSupport(order = 30)
    public String handleLogout() {
        session.invalidate();
        return "redirect:/";
    }

}
