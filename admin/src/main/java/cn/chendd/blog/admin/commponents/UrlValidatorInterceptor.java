package cn.chendd.blog.admin.commponents;

import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.base.spring.component.InterceptorConfiguration;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.Ajax;
import cn.chendd.core.utils.EnumUtil;
import cn.chendd.core.utils.Http;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * url地址拦截器，权限菜单拦截器
 *
 * @author chendd
 * @date 2021/5/30 20:38
 */
public class UrlValidatorInterceptor extends HandlerInterceptorAdapter {

    private InterceptorConfiguration config;

    public UrlValidatorInterceptor(InterceptorConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String methodType = request.getMethod();
        //处理一些按请求类型放行的地址
        boolean excludeType = this.validatorExcludeType(uri , methodType , config.getExcludeMappingTypes());
        if (excludeType) {
            return true;
        }
        SysUserResult user = UserContext.getCurrentUser(SysUserResult.class);
        //用户没有登录时的相应
        if (user == null) {
            this.validatorNoAuth(request, response , "用户未登录或已失效");
            return false;
        }
        //获取系统全量的后端菜单地址，并判断当前请求的地址是否在功能菜单的范围内
        SysMenuService sysMenuService = SpringBeanFactory.getBean(SysMenuService.class);
        List<SysMenu> allMenuList = sysMenuService.queryAllSysMenus();
        //记录当前请求地址url匹配对应的功能菜单
        Long menuId = null;
        for (SysMenu menu : allMenuList) {
            String menuUrl = menu.getMenuUrl();
            RequestMethod method = menu.getRequestMethod();
            if (StringUtils.equalsIgnoreCase(uri , menuUrl) || uri.matches(menuUrl)) {
                //如果找到地址相匹配的，同时再判断请求类型是否一致
                if (StringUtils.equalsIgnoreCase(method.name() , methodType)) {
                    menuId = menu.getMenuId();
                    break;
                }
            }
        }
        //如果当前请求菜单地址不在系统菜单拦截的范围内，直接放行
        if (menuId == null) {
            return true;
        }
        //获取当前用户所有角色和角色对应菜单，查找菜单地址中是否存在对应的功能权限
        boolean hasAuth = this.checkUserAuth(menuId , user);
        if (! hasAuth) {
            this.validatorNoAuth(request, response , "无操作权限！");
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 根据放行地址和类型验证放行
     * @param uri 请求地址uri
     * @param methodType 请求类型
     * @param excludeTypes 放行路径集合
     * @return true ？ 放行 ：拦截
     */
    private boolean validatorExcludeType(String uri , String methodType , List<String> excludeTypes) {
        for (String excludeType : excludeTypes) {
            String[] splits = StringUtils.split(excludeType , " ");
            if (splits.length != 2) {
                continue;
            }
            String url = splits[0];
            RequestMethod requestMethod = EnumUtil.getInstanceByName(splits[1] , RequestMethod.class);
            if (StringUtils.equalsIgnoreCase(uri , url) || uri.matches(url)) {
                //如果找到地址相匹配的，同时再判断请求类型是否一致
                if (StringUtils.equalsIgnoreCase(requestMethod.name() , methodType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 验证是否存在功能菜单权限，按请求方式进行对应的数据返回
     * @param request 请求
     * @param response 相应
     * @throws IOException 异常处理
     */
    private void validatorNoAuth(HttpServletRequest request, HttpServletResponse response , String message) throws IOException {
        boolean isAjax = Ajax.isAJAXRequest(request);
        String basePath = Http.getBasePath(request);
        if (isAjax) {
            Http.responseText(response , JSONObject.toJSONString(new ErrorResult<String>(message)));
        } else {
            response.sendRedirect(basePath + "/statics/frame/auth.html");
        }
    }

    /**
     * 根据菜单地址和用户信息检查是否具备某个角色对应的功能
     * 根据多次的实践验证，无限极的菜单几乎不存在，顶多存在三级上下级结构的菜单
     * @param menuId 菜单ID
     * @param user 用户对象
     * @return true ？ 有权限 ：无权限
     */
    private boolean checkUserAuth(Long menuId, SysUserResult user) {
        List<SysUserResult.SysRole> roleList = user.getRoles();
        for (SysUserResult.SysRole role : roleList) {
            //获取第一级菜单
            List<SysUserResult.SysRole.SysRoleMenu> menuList = role.getMenuList();
            for (SysUserResult.SysRole.SysRoleMenu menu : menuList) {
                Long id = menu.getMenuId();
                if (menuId.equals(id)) {
                    return true;
                }
                //获取第二级菜单
                List<SysUserResult.SysMenu> twoMenuList = menu.getRoleMenus();
                for (SysUserResult.SysMenu twoMenu : twoMenuList) {
                    if (checkUserAuthByChildMenu(menuId, twoMenu)) return true;
                }
            }
        }
        return false;
    }

    /**
     * 验证子级菜单节点是否存在匹配项
     * @param menuId 菜单ID
     * @param twoMenu 第二级菜单
     * @return true ？ 有权限 ：无权限
     */
    private boolean checkUserAuthByChildMenu(Long menuId, SysUserResult.SysMenu twoMenu) {
        Long twoId = twoMenu.getMenuId();
        if (menuId.equals(twoId)) {
            return true;
        }
        //获取第三级菜单
        List<SysUserResult.SysMenu> threeMenuList = twoMenu.getRoleMenus();
        for (SysUserResult.SysMenu threeMenu : threeMenuList) {
            Long threeId = threeMenu.getMenuId();
            if (menuId.equals(threeId)) {
                return true;
            }
            //获取第四级菜单
            List<SysUserResult.SysMenu> fourMenuList = threeMenu.getRoleMenus();
            for (SysUserResult.SysMenu fourMenu : fourMenuList) {
                Long fourId = fourMenu.getMenuId();
                if (menuId.equals(fourId)) {
                    return true;
                }
            }
        }
        return false;
    }

}
