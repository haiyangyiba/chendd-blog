package cn.chendd.blog.admin.system.home.controller;

import cn.chendd.blog.admin.system.shortcut.config.ShortcutMenuParamComponent;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumAdminTheme;
import cn.chendd.blog.client.user.vo.SysUserResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 后台管理首页
 *
 * @author chendd
 * @date 2020/5/22 18:35
 */
@Controller
public class HomepageController extends BaseController {

    @Resource
    private ShortcutMenuParamComponent shortcutMenuParam;

    @GetMapping({"/" , "/index" , "/login"})
    public String index() {
        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        if(userResult == null) {
            return "/login";
        }
        //主题
        super.addAttribute("adminThemes" , EnumAdminTheme.values());
        //快捷菜单
        super.addAttribute("shortcutMenus" , shortcutMenuParam.getShortcutMenus());
        return "/index";
    }

}
