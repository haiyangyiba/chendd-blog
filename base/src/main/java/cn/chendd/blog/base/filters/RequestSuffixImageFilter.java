package cn.chendd.blog.base.filters;

import com.mchange.v1.io.InputStreamUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 拦截博客老版本中的Image请求，以“.image”结尾的请求，特指1.0版本的图片及附件等资源类的访问等
 *
 * @author chendd
 * @date 2022/6/25 22:04
 */
public class RequestSuffixImageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        ClassPathResource resource = new ClassPathResource("/statics/images/image.png");
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] imageBytes = InputStreamUtils.getBytes(inputStream);
            response.getOutputStream().write(imageBytes);
        }
    }
}
