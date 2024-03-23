package cn.chendd.blog.base.filters;

import cn.chendd.blog.base.log.BaseLog;
import cn.chendd.blog.base.model.UserInfo;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.Http;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 下载文件过滤器
 *
 * @author chendd
 * @date 2022/7/5 22:19
 */
public class DownloadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Boolean login = UserContext.isLogin(request.getSession());
        if (! login) {
            //未登录页面
            String basePath = Http.getBasePath(request);
            response.sendRedirect(basePath + "/blog/frame/login.html");
            return;
        }
        UserInfo userInfo = UserContext.getCurrentUser(UserInfo.class);
        if (userInfo != null) {
            final UserInfo.SysAccount account = userInfo.getAccount();
            final UserInfo.SysUser user = userInfo.getUser();
            if (account != null && user != null) {
                BaseLog.getLogger().info("{} 用户 {} 下载文件 {}" , user.getUserSource().getText() , account.getUsername() , request.getRequestURI());
            }
        }
        filterChain.doFilter(request , response);
    }
}
