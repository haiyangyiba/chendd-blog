package cn.chendd.blog.base.spring.configuration;

import cn.chendd.blog.base.servlet.FileReaderServlet;
import cn.chendd.blog.base.servlet.InternetImageReaderServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet配置
 *
 * @author chendd
 * @date 2020/8/4 21:03
 */
@Configuration
public class ServletConfiguration {

    @Bean
    public ServletRegistrationBean fileServletRegistrationBean(){
        ServletRegistrationBean<FileReaderServlet> bean = new ServletRegistrationBean<FileReaderServlet>(new FileReaderServlet());
        bean.addUrlMappings("/file/*");
        return bean;
    }

    @Bean
    public ServletRegistrationBean internetImageServletRegistrationBean(){
        ServletRegistrationBean<InternetImageReaderServlet> bean = new ServletRegistrationBean<>(new InternetImageReaderServlet());
        bean.addUrlMappings("/internet/image/*");
        return bean;
    }

    @Bean
    public ServletRegistrationBean internetLogoServletRegistrationBean(){
        ServletRegistrationBean<InternetImageReaderServlet> bean = new ServletRegistrationBean<>(new InternetImageReaderServlet());
        bean.addUrlMappings("/internet/logo/*");
        return bean;
    }

}
