package cn.chendd.blog.base.spring.configuration;

import cn.chendd.blog.base.filters.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 过滤器配置
 *
 * @author chendd
 * @date 2020/7/16 22:52
 */
@Configuration
public class FilterConfiguration {

    /**
     * 系统参数包装 Filter
     */
    @Bean
    public FilterRegistrationBean<RequestWrapperFilter> requestWrapperFilter() {
        FilterRegistrationBean<RequestWrapperFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestWrapperFilter());
        filterBean.setName("requestWrapperFilter");
        filterBean.addUrlPatterns("*.html");
        filterBean.addInitParameter("excludeUrls", "/statics/**");
        filterBean.setOrder(100100);
        filterBean.setEnabled(true);
        return filterBean;
    }

    /**
     * 系统访问明细 Filter
     */
    @Bean
    @Conditional(value = RequestDetailFilterCondition.class)
    public FilterRegistrationBean<RequestDetailFilter> requestDetailFilter() {
        FilterRegistrationBean<RequestDetailFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestDetailFilter());
        filterBean.setName("requestDetailFilter");
        filterBean.addUrlPatterns("*.html" , "/");
        filterBean.addInitParameter("excludeUrls", "/statics/**");
        filterBean.setOrder(100200);
        filterBean.setEnabled(true);
        return filterBean;
    }

    /**
     * 系统Api接口 Filter
     */
    @Bean
    public FilterRegistrationBean<RequestApiSessionFilter> requestApiSessionFilter() {
        FilterRegistrationBean<RequestApiSessionFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestApiSessionFilter());
        filterBean.setName("requestApiSessionFilter");
        filterBean.addUrlPatterns("*.html");
        filterBean.setOrder(SessionRepositoryFilter.DEFAULT_ORDER - 1);
        filterBean.setEnabled(true);
        return filterBean;
    }

    /**
     * 博客系统老版本的.a请求兼容，所有访问http://.../xxx.a结尾的请求，均被拦截，输出对应的响应文本
     * @return 过滤器
     */
    @Bean
    public FilterRegistrationBean<RequestSuffixActionFilter> requestSuffixActionFilter() {
        FilterRegistrationBean<RequestSuffixActionFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestSuffixActionFilter());
        filterBean.setName("requestSuffixActionFilter");
        filterBean.addUrlPatterns("*.a");
        filterBean.setOrder(10050);
        filterBean.setEnabled(true);
        return filterBean;
    }

    /**
     * 博客系统老版本的.a请求兼容，所有访问http://.../xxx.image结尾的请求，均被拦截，输出对应的响应图片
     * @return 过滤器
     */
    @Bean
    public FilterRegistrationBean<RequestSuffixImageFilter> requestSuffixImageFilter() {
        FilterRegistrationBean<RequestSuffixImageFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestSuffixImageFilter());
        filterBean.setName("requestSuffixImageFilter");
        filterBean.addUrlPatterns("*.image");
        filterBean.setOrder(10050);
        filterBean.setEnabled(true);
        return filterBean;
    }

    /**
     * 博客系统资源文件下载，限制登录后才可以下载
     * @return 过滤器
     */
    @Bean
    public FilterRegistrationBean<DownloadFilter> downloadFilter() {
        FilterRegistrationBean<DownloadFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new DownloadFilter());
        filterBean.setName("downloadFilter");
        filterBean.addUrlPatterns("*.zip" , "*.exe");
        //该过滤器需要获取用户信息，所以优先级必须低于spring session的过滤器优先级
        filterBean.setOrder(SessionRepositoryFilter.DEFAULT_ORDER + 1);
        filterBean.setEnabled(true);
        return filterBean;
    }

}
