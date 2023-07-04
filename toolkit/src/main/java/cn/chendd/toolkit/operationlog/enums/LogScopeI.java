package cn.chendd.toolkit.operationlog.enums;

import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.vo.MethodSignatureResult;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志范围定义接口
 *
 * @author chendd
 * @date 2021/4/16 18:07
 */
public interface LogScopeI {

    /**
     * 根据方法切面返回方法结构体
     * @param logAnnotation log注解
     * @param methodPoint 方法切面
     * @param returnValue 方法返回值
     * @return 方法信息对象
     */
    MethodSignatureResult getMethodSignature(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint , Object returnValue);

    /**
     * 获取方法信息
     * @param methodPoint 方法切面
     * @return 方法信息
     */
    default String getMethodInfo(MethodInvocationProceedingJoinPoint methodPoint) {
        MethodSignature signature = (MethodSignature) methodPoint.getSignature();
        Method method = signature.getMethod();
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    /**
     * 获取方法参数信息
     * @param logAnnotation log注解
     * @param methodPoint 方法切面
     * @return 方法参数信息
     */
    default Map<String , Object> getMethodParameter(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint) {
        MethodSignature signature = (MethodSignature) methodPoint.getSignature();
        Method method = signature.getMethod();
        Map<String , Object> parameters = Maps.newLinkedHashMap();
        int count = method.getParameterCount();
        if (count > 0) {
            String[] names = signature.getParameterNames();
            Object args[] = methodPoint.getArgs();
            //判断是否存在需要排除的属性
            String[] exclude = logAnnotation.exclude();
            for (int i=0 ; i < count ; i++) {
                boolean flag = false;
                for (String ecd : exclude) {
                    if (StringUtils.equalsIgnoreCase(ecd , names[i])) {
                        flag = true;
                        break;
                    }
                }
                if(! flag) {
                    parameters.put(names[i] , args[i]);
                }
            }
        }
        return parameters;
    }

}
