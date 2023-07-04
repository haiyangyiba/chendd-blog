package cn.chendd.blog.admin.system.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.chendd.core.common.constant.Constant.SYSTEM_CURRENT_USER;

/**
 * 后台管理主题管理
 *
 * @author chendd
 * @date 2020/5/22 18:35
 */
@Controller
@RequestMapping("/system/theme")
public class SysAdminThemeController extends BaseController {

    @GetMapping("/{type}")
    @ResponseBody
    public BaseResult changeTheme(@ApiParam("主题类型") @PathVariable String type) {
        SysUserResult userEntity = super.getCurrentUser(SysUserResult.class);
        userEntity.getUserConfig().setAdminTheme(type);
        session.setAttribute(SYSTEM_CURRENT_USER, JSONObject.toJSON(userEntity));
        return new SuccessResult<>("已设置所选主题！");
    }

}
