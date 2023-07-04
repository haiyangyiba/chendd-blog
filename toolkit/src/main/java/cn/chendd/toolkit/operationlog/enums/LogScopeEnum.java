package cn.chendd.toolkit.operationlog.enums;

import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.vo.MethodSignatureResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

/**
 * 日志记录范围
 *
 * @author chendd
 * @date 2021/4/16 19:51
 */
public enum LogScopeEnum implements LogScopeI {


    /**
     * 方法参数、方法名、方法返回值，方法运行正常时正常返回数据，运行报错时方法返回值为异常堆栈信息
     */
    ALL {

        @Override
        public MethodSignatureResult getMethodSignature(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint, Object returnValue) {
            MethodSignatureResult result = new MethodSignatureResult();
            //设置方法信息
            result.setMethodInfo(super.getMethodInfo(methodPoint));
            //设置方法参数对象
            result.setMethodParameter(super.getMethodParameter(logAnnotation , methodPoint));
            //设置方法返回值
            result.setMethodReturn(returnValue);
            return result;
        }

    },
    /**
     * 方法参数、方法名、方法返回值（注：当方法报错试时返回值为记录异常堆栈）
     */
    Auto {
        @Override
        public MethodSignatureResult getMethodSignature(Log logAnnotation, MethodInvocationProceedingJoinPoint methodPoint, Object returnValue) {
            MethodSignatureResult result = new MethodSignatureResult();
            //设置方法信息
            result.setMethodInfo(super.getMethodInfo(methodPoint));
            //设置方法参数对象
            result.setMethodParameter(super.getMethodParameter(logAnnotation , methodPoint));
            //设置方法返回值
            if (returnValue instanceof Exception) {
                //方法返回值等于异常堆栈信息
                result.setMethodReturn(ExceptionUtils.getStackTrace((Throwable) returnValue));
            }
            return result;
        }
    },

    /**
     * 方法名
     */
    METHOD {

        @Override
        public MethodSignatureResult getMethodSignature(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint, Object returnValue) {
            MethodSignatureResult result = new MethodSignatureResult();
            result.setMethodInfo(super.getMethodInfo(methodPoint));
            return result;
        }

    },
    /**
     * 方法名 + 方法参数
     */
    METHOD_PARAMETER {

        @Override
        public MethodSignatureResult getMethodSignature(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint, Object returnValue) {
            MethodSignatureResult result = new MethodSignatureResult();
            //设置方法信息
            result.setMethodInfo(super.getMethodInfo(methodPoint));
            //设置方法参数对象
            result.setMethodParameter(super.getMethodParameter(logAnnotation , methodPoint));
            return result;
        }
    },
    /**
     * 方法名 + 返回值
     */
    METHOD_RETURN {

        @Override
        public MethodSignatureResult getMethodSignature(Log logAnnotation , MethodInvocationProceedingJoinPoint methodPoint, Object returnValue) {
            MethodSignatureResult result = new MethodSignatureResult();
            //设置方法信息
            result.setMethodInfo(super.getMethodInfo(methodPoint));
            //设置方法返回值
            result.setMethodReturn(returnValue);
            return result;
        }

    },
    ;

}
