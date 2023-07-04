package cn.chendd.blog.base.api.version.commponents;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * ApiRequestMapping重写
 *
 * @author chendd
 * @date 2019/10/16 22:34
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 加入版本控制
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info != null) {
            return createApiVersionInfo(method, handlerType, info);
        }else{
            return info;
        }
    }

    @Override
    protected RequestCondition<ApiVesrsionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = (ApiVersion) AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVesrsionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = (ApiVersion) AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestMappingInfo createApiVersionInfo(Method method, Class<?> handlerType, RequestMappingInfo info) {

        boolean hasType = false;
        ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        if (typeAnnotation != null) {
            hasType = true;
        }
        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if (methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            //
            String value = !hasType ? ("v" + methodAnnotation.value()) : methodAnnotation.value();
            info = createApiVersionInfo(value, methodCondition).combine(info);
        }
        //如果方法上增加有版本号，再判断类上是否存在版本号，如果存在则结果为：/类版本/方法版本
        //如果不需要实现版本路径叠加则将此处修改为 if - else 逻辑即可
        if (hasType) {
            RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
            info = createApiVersionInfo("v" + typeAnnotation.value(), typeCondition).combine(info);
        }
        return info;
    }

    private RequestMappingInfo createApiVersionInfo(String value , RequestCondition<?> customCondition) {
        //多组地址映射路径
        String[] patterns = new String[]{ value };
        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(),
                        useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(), new ParamsRequestCondition(),
                new HeadersRequestCondition(), new ConsumesRequestCondition(),
                new ProducesRequestCondition(), customCondition);
    }

    private RequestCondition<ApiVesrsionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVesrsionCondition(apiVersion.value());
    }

}
