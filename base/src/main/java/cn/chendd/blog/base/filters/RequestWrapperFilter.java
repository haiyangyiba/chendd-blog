package cn.chendd.blog.base.filters;

import cn.chendd.blog.base.filters.wrapper.ParamRepairResult;
import cn.chendd.blog.base.filters.wrapper.BodyHttpParamRequestWrapper;
import cn.chendd.blog.base.filters.wrapper.ParameterHttpParamRequestWrapper;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.service.SysDbValueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.List;

/**
 * request包装器
 *
 * @author chendd
 * @date 2020/7/16 22:12
 */
@Slf4j
public class RequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ///若拦截文件上传类型会导致ueditor的文件上传“找不到上传的文件”，两种解决方式：通过此处不拦截ueditor文件上传，第二种调整ueditor服务端路径，去掉.html后缀
        /*if (StringUtils.startsWith(request.getContentType() , MediaType.MULTIPART_FORM_DATA_VALUE)) {
            filterChain.doFilter(request , servletResponse);
            return;
        }*/

        String contentType = request.getContentType();
        SysDbValueService dbValueService = SpringBeanFactory.getBean(SysDbValueService.class);
        List<SysDbValue> specialList = dbValueService.queryListByGroup("特殊字符过滤");
        List<SysDbValue> sensitiveList = dbValueService.queryListByGroup("敏感字符过滤");
        ParamRepairResult paramRepair = new ParamRepairResult();
        paramRepair.setSpecialList(specialList);
        paramRepair.setSensitiveList(sensitiveList);

        HttpServletRequestWrapper requestWrapper;
        if (StringUtils.startsWithIgnoreCase(contentType , MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new BodyHttpParamRequestWrapper(request , paramRepair);
        } else {
            requestWrapper = new ParameterHttpParamRequestWrapper(request , paramRepair);
        }
        //System.out.println("过滤器--->RequestWrapperFilter.doFilterInternal--->" + requestWrapper.getMethod() + "--->" + request.getRequestURL());
        ///System.out.println("after-->" + JSON.toJSONString(requestWrapper.getParameterMap()));
        filterChain.doFilter(requestWrapper , servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("requestWrapperFilter过滤器初始化");
    }

    @Override
    public void destroy() {
        log.info("requestWrapperFilter过滤器销毁");
    }

}
