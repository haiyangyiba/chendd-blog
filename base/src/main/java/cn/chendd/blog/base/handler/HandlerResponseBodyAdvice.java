package cn.chendd.blog.base.handler;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumExceptionType;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.Ajax;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.lang.annotation.Annotation;

/**
 * Controller响应结果统一封装，也可以使用 @RestControllerAdvice 注解处理
 *
 * @author chendd
 * @date 2019/9/22 18:03
 */
@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class HandlerResponseBodyAdvice implements ResponseBodyAdvice {

    /**
     * 必须规则：约定类继承了BaseController，表示为自定义模块
     * 可选规则：类被标记有@RestController
     * 可选规则：方法被标记有@Response
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Class<?> containingClass = methodParameter.getContainingClass();
        Annotation restController = containingClass.getAnnotation(RestController.class);
        ResponseBody responseBody = methodParameter.getMethod().getAnnotation(ResponseBody.class);
        boolean rest = restController != null || responseBody != null;
        boolean custom = BaseController.class.isAssignableFrom(containingClass);
        return rest && custom;
    }

    @Override
    public Object beforeBodyWrite(Object value, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(value == null){
            return new SuccessResult<>(null);
        }
        if(value instanceof BaseResult){
            return value;
        } else if(value instanceof BufferedImage) {
            return value;
        }
        return new SuccessResult<>(value);
    }

    /**
     * 全局 Rest类型 Controller 异常处理
     * @param ex 错误异常信息封装
     */
    @ExceptionHandler(Exception.class)
//    @ResponseBody
    public BaseResult handerRestApiException(Exception ex , HttpServletRequest request) throws Exception {
        //可根据request中请求判定是否为ajax类型
        //提示类的异常不需要输出错误堆栈
        if(!(ex instanceof ValidateDataException)){
            log.error("操作发生了错误：" , ex);
        }
        String api = request.getHeader("api");
        //是否是web项目的Forest Api接口调用发起的请求，如果是则表示按非Ajax请求报错处理
        boolean isApi = StringUtils.isNotEmpty(api);
        boolean isAjax = Ajax.isAJAXRequest(request);
        //非ajax请求直接使用系统默认的异常出来，判断是否为ajax请求、需要增加是否是forest的api请求
        if (!isAjax && !isApi) {
            throw ex;
        }
        EnumExceptionType exceptionType = EnumExceptionType.getExceptionType(ex.getClass().getSimpleName().toString());
        if(exceptionType != null){
            return new ErrorResult<>(exceptionType.getMessage());
        }
//        getRootCauseMessage
        String message = ExceptionUtils.getRootCauseMessage(ex);
        return new ErrorResult<>(StringUtils.substring(message , message.indexOf(": ") + 1));
    }

}
