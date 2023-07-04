package cn.chendd.blog.base.filters;

import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截博客老版本中的Action请求，所有“.a”结尾的请求，特指1.0版本的文章内容及全站页面访问访问等
 *
 * @author chendd
 * @date 2022/6/25 22:04
 */
public class RequestSuffixActionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        response.getWriter().write("<h3>本站已经升级新版，欢迎<a style=\"color:red;font-size:32px;\" href=\"/\">访问新版</a>，" +
                "右上角“关键字搜一搜”仍可找到您关心的资料。</h3>");
    }
}
