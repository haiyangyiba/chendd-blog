package cn.chendd.blog.base.filters;

import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.Http;
import org.springframework.core.annotation.Order;
import org.springframework.session.web.http.SessionRepositoryFilter;

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
        filterChain.doFilter(request , response);
    }
}
