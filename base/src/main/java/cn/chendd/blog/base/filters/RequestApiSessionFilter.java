package cn.chendd.blog.base.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 排除Api接口请求的spring session生成
 *
 * @author chendd
 * @date 2021/12/21 10:47
 */
public class RequestApiSessionFilter extends OncePerRequestFilter {

    private static final String API_PATH_MATCHER = "/v\\d+/.*?";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        //绕过session filter如：所有以/v数字/开头的url；或者以/api/开头的URL，都绕过session filter，不去访问session
        if (uri.matches(API_PATH_MATCHER)) {
            request.setAttribute("org.springframework.session.web.http.SessionRepositoryFilter.FILTERED", Boolean.TRUE);
        }
        filterChain.doFilter(request , response);
    }

}
