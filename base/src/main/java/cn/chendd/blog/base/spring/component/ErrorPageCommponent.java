package cn.chendd.blog.base.spring.component;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 错误页面定制
 *
 * @author chendd
 * @date 2022/3/7 21:23
 */
@Component
public class ErrorPageCommponent implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        //model对象原型为java.util.Collections$UnmodifiableMap无法修改
        Map<String , Object> result = Maps.newHashMap(model);
        Object exception = request.getAttribute("javax.servlet.error.exception");
        if (exception instanceof Exception) {
            String stackTrace = ExceptionUtils.getStackTrace((Throwable) exception);
            result.put("stackTrace" , stackTrace);
        }
        //状态码4xx的错误页面
        if (status.is4xxClientError()) {
            return new ModelAndView("/frame/4xx" , result , status);
        } else if (status.is5xxServerError()) {
            return new ModelAndView("/frame/5xx" , result , status);
        }
        return null;
    }
}
