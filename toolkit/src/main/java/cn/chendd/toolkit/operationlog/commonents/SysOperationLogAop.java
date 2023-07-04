package cn.chendd.toolkit.operationlog.commonents;

import cn.chendd.core.spring.SpelStandUtil;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.Ip;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.ResultEnum;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.service.SysOperationLogService;
import cn.chendd.toolkit.operationlog.vo.MethodSignatureResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统功能日志记录Aop
 *
 * @author chendd
 * @date 2019/9/20 16:52
 */
@Component
@Aspect
@Slf4j
public class SysOperationLogAop {

    @Resource
    private SysOperationLogService operationLogService;

    @Pointcut("@annotation(cn.chendd.toolkit.operationlog.annotions.Log)")
    public void pointcutDeclare(){}

    /**
     * 方法前置
     * @param joinPoint 方法切面
     */
    @Before(value = "pointcutDeclare()")
    public void before(JoinPoint joinPoint) {

    }

    @Around(value = "pointcutDeclare()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //方法结构体
        MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
        MethodSignature signature = (MethodSignature) methodPoint.getSignature();
        String message = "方法名：%s，参数个数：%s，返回类型：%s";
        log.debug(String.format(message, signature.getName() , methodPoint.getArgs().length , signature.getReturnType()));
        Log logAnnotation = signature.getMethod().getDeclaredAnnotation(Log.class);
        //存储操作日志
        Object returnValue;
        Long id = IdWorker.getId();
        //记录方法执行时间
        long beginTime = System.currentTimeMillis();
        String begin = DateFormat.formatDatetime(beginTime);
        try {
            //存储开始执行日志，请求开始执行时就开始记录，某些操作耗时将在执行之前记录
            this.operationLogService.storageBeforeOperationLog(id , begin , Ip.getIpAddress(UserContext.getRequest()));
            returnValue = point.proceed();
            long endTime = System.currentTimeMillis();
            //执行完毕后更新日志
            this.storageAfterOperationLog(methodPoint , returnValue , ResultEnum.success , begin , DateFormat.formatDatetime(endTime) , endTime - beginTime , id);
        } catch (Exception e) {
            //发生错误时更新日志
            long endTime = System.currentTimeMillis();
            this.storageAfterOperationLog(methodPoint, e , ResultEnum.error , begin , DateFormat.formatDatetime(endTime) , endTime - beginTime , id);
            throw e;
        }
        return returnValue;
    }

    /**
     * 方法后置
     * @param joinPoint 方法切面
     */
    @After(value = "pointcutDeclare()")
    public void after(JoinPoint joinPoint) {

    }

    /**
     * 保存操作日志
     * @param methodPoint 方法切面
     * @param returnValue 返回值
     * @param resultEnum 执行结果
     * @param runTime 运行时间
     */
    private void storageAfterOperationLog(MethodInvocationProceedingJoinPoint methodPoint, Object returnValue, ResultEnum resultEnum , String beginTime , String endTime , Long runTime , Long id) {
        SysOperationLog entity = new SysOperationLog();
        //设置保存参数
        JSONObject userInfo = UserContext.getCurrentUser();
        if(userInfo != null) {
            JSONObject account = userInfo.getJSONObject("account");
            JSONObject user = userInfo.getJSONObject("user");
            entity.setUserId(user.getLong("userId")).setUserName(account.getString("username"));
        }
        entity.setId(id).setTableDate(beginTime).setEndTime(endTime).setRunTime(runTime).setResultEnum(resultEnum);

        MethodSignature signature = (MethodSignature) methodPoint.getSignature();
        String message = "方法名：%s，参数个数：%s，返回类型：%s";
        log.debug(String.format(message, signature.getName() , methodPoint.getArgs().length , signature.getReturnType()));
        Log logAnnotation = signature.getMethod().getDeclaredAnnotation(Log.class);
        //从方法结构中构建日志对象信息
        MethodSignatureResult methodSignatureResult = logAnnotation.scope().getMethodSignature(logAnnotation , methodPoint, returnValue);
        //如果出现错误，操作日志的描述信息将被重置为异常信息
        String description = this.getDescription(logAnnotation.description() , returnValue , methodSignatureResult);
        entity.setDescription(description);
        String logName = logAnnotation.name();
        if (StringUtils.isEmpty(logName)) {
            //将日志名称按照默认规则保存
            logName = signature.getDeclaringType().getSimpleName() + "." + signature.getMethod().getName();
        }
        entity.setModuleId(logName);
        entity.setContent(JSON.toJSONString(methodSignatureResult, SerializerFeature.WriteMapNullValue));
        //存储操作日志
        operationLogService.saveAfterOperationLog(entity);
    }

    /**
     * 解析description表达式（spel表达式）
     * @param description 日志描述信息
     * @param returnValue 方法返回值
     * @param methodSignatureResult 方法日志信息
     * @return 重构后的日志描述
     */
    private String getDescription(String description, Object returnValue, MethodSignatureResult methodSignatureResult) {
        //约定当表达式中包含‘'’或‘#’时，表示为spel表达式，采用对应的表达式引擎解析
        final String[] matchs = {"'" , "#"};
        boolean isSpel = false;
        for (String match : matchs) {
            if(StringUtils.indexOf(description , match) != -1) {
                isSpel = true;
            }
        }
        if (! isSpel) {
            return description;
        }
        if (returnValue instanceof Exception) {
            description = ((Throwable) returnValue).getMessage();
            //方法返回值等于异常堆栈信息
            methodSignatureResult.setMethodReturn(ExceptionUtils.getStackTrace((Throwable) returnValue));
            return description;
        }
        //如果方法执行出错时，操作日志描述对象为异常信息
        Object methodReturn = methodSignatureResult.getMethodReturn();
        Map<String , Object> methodParameter = methodSignatureResult.getMethodParameter();
        if (methodParameter == null) {
            methodParameter = Maps.newLinkedHashMap();
        }
        //参数名直接使用，并且增加return参数为返回值对象
        Map<String , Object> descriptionMap = Maps.newHashMap(methodParameter);

        descriptionMap.put("return" , methodReturn);
        SpelStandUtil spel = SpelStandUtil.newInstance();
        spel.sets(descriptionMap);
        String value = spel.get(description , String.class);
        return value;
    }

}
