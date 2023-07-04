package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.web.home.service.UserInfoService;
import cn.chendd.core.exceptions.ValidateDataException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.chendd.core.common.constant.Constant.SYSTEM_CURRENT_USER;

/**
 * 前台参数值交互Controller
 *
 * @author chendd
 * @date 2021/2/9 21:51
 */
@Api(value = "用户管理" , tags = "用户管理")
@ApiSort(70)
@Controller
@RequestMapping(value = "/user" , produces = MediaType.APPLICATION_JSON_VALUE)
public class UserInfoController extends BaseController {

    @Resource
    private UserInfoService userInfoService;
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" , Pattern.CASE_INSENSITIVE);

    @GetMapping("/userInfo")
    public String queryUserInfo() {
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        Long userId = userResult.getUser().getUserId();
        UserInfoResult userInfo = userInfoService.queryUserInfo(userId);
        super.addAttribute("userInfo" , userInfo);
        return "/user/userInfo";
    }

    /**
     * 修改用户密码页面
     * @return 页面地址
     */
    @GetMapping("/userEmail")
    public String updateUserEmail() {
        this.queryUserInfo();
        return "/user/userEmail";
    }

    /**
     * 修改用户密码
     * @return 执行结果
     */
    @PutMapping("/userEmail")
    @ResponseBody
    public Boolean updateUserEmail(@RequestBody String email) {
        //验证Email格式
        Matcher matcher = EMAIL_REGEX.matcher(email);
        boolean vaildator = matcher.find();
        if (! vaildator) {
            throw new ValidateDataException(String.format("输入邮箱“%s”不符合规则" , email));
        }
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        SysUserResult.SysUser sysUser = userResult.getUser();
        Long userId = userResult.getUser().getUserId();
        //把String数据转换为JSON格式
        this.userInfoService.updateUserEmail(userId , JSON.toJSONString(email));
        sysUser.setEmail(email);
        userResult.setUser(sysUser);
        session.setAttribute(SYSTEM_CURRENT_USER, JSONObject.toJSON(userResult));
        return Boolean.TRUE;
    }

}
