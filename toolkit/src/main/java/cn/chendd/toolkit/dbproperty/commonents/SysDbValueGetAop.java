package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.core.common.convert.NamePartitionConvert;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import cn.chendd.toolkit.dbproperty.enums.EnumValueType;
import cn.chendd.toolkit.dbproperty.enums.IEnumValueType;
import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数注入Aop取值
 *
 * @author chendd
 * @date 2020/7/14 20:51
 */
@Component
@Aspect
@Slf4j
public class SysDbValueGetAop {

    /**
     * 拦截规则：
     * 1、类必须包含注解：DbValueConfiguration
     * 2、必须是以 get开头的书属性方法
     * 3、该方法对应的有字段，并且注解有 DbValue
     */
    @Pointcut("execution(* cn.chendd..*.get*())")
    public void pointcutDeclare(){}

    @Around(value = "pointcutDeclare()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //方法结构体
        MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
        MethodSignature signature = (MethodSignature) methodPoint.getSignature();
        Object returnValue;
        Class<?> clazz = signature.getDeclaringType();
        Field target = null;
        if(clazz.isAnnotationPresent(DbValueConfiguration.class)){
            List<Field> allFieldsList = FieldUtils.getAllFieldsList(signature.getDeclaringType());
            for (Field field : allFieldsList) {
                boolean present = field.isAnnotationPresent(DbValue.class);
                if (!present) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName() , signature.getDeclaringType());
                String readMethodName = pd.getReadMethod().getName();
                if(StringUtils.equals(readMethodName , signature.getName())) {
                    target = field;
                    break;
                }
            }
        }
        //判定是否需要代理实现
        if (target == null) {
            returnValue = point.proceed();
            return returnValue;
        }
        //采用默认的参数注入实现
        List<SysDbValue> sysDbValueList = SpringBeanFactory.getBean(SysDbValueMapping.class).getAllList();
        returnValue = this.disposeFieldValue(methodPoint.getThis() , target , sysDbValueList);
        return returnValue;
    }

    private Object disposeFieldValue(Object bean, Field field, List<SysDbValue> sysDbValues) throws Exception {
        /// 如果某个Field字段曾被赋值过，可以无需再次赋值，每次都赋值可以获取到最新的数据
        /*final Object fieldValue = FieldUtils.readField(field, bean, true);
        if (fieldValue != null) {
            return fieldValue;
        }*/

        DbValue dbValue = field.getAnnotation(DbValue.class);
        String group = dbValue.group();
        String prefix = dbValue.prefix();
        String suffix = dbValue.suffix();
        String value = dbValue.value();
        List<SysDbValue> dataList = sysDbValues.stream()
                .filter(item -> (StringUtils.isEmpty(group)) || (StringUtils.isNotEmpty(group) && StringUtils.equals(item.getGroup(), group)))
                .filter(item -> (StringUtils.isEmpty(prefix)) || (StringUtils.isNotEmpty(prefix) && StringUtils.startsWith(item.getKey(), prefix)))
                .filter(item -> (StringUtils.isEmpty(suffix)) || (StringUtils.isNotEmpty(suffix) && StringUtils.endsWith(item.getKey(), suffix)))
                .filter(item -> {
                    boolean valueEmpty = StringUtils.isEmpty(value);
                    boolean groupEmpty = StringUtils.isEmpty(group);
                    boolean prefixEmpty = StringUtils.isEmpty(prefix);
                    boolean suffixEmpty = StringUtils.isEmpty(suffix);
                    //如果value属性为空，则从当前的字段名称中读取对应参数，并且处理定义大写字母
                    if (valueEmpty && groupEmpty && prefixEmpty && suffixEmpty) {
                        String filedName = field.getName();
                        String name = NamePartitionConvert.getConvertNameByField(filedName);
                        return StringUtils.equals(item.getKey(), name);
                    } else if(valueEmpty){
                        return true;
                    }
                    return StringUtils.equals(item.getKey(), value);
                }).collect(Collectors.toList());

        Class<?> fieldType = field.getType();
        int size = dataList.size();
        if(size == 0){
            return null;
        }
        //Map及其子类的参数映射
        if (Map.class.isAssignableFrom(fieldType)) {
            Map<String , Object> valueMap = new HashMap<>();
            for (SysDbValue sysDbValue : dataList) {
                valueMap.put(sysDbValue.getKey() , sysDbValue.getValue());
            }
            return valueMap;
        } else if(! field.getType().getName().startsWith("java.lang.")){
            //非Java自定义的常用javabean属性定义
            Object fieldInstance = null;
            try {
                fieldInstance = field.getType().newInstance();
                List<Field> newInstanceFields = FieldUtils.getAllFieldsList(field.getType());
                for (Field newInstanceField : newInstanceFields) {
                    IEnumValueType valueType = EnumValueType.getInstanceByFieldType(newInstanceField.getType().getSimpleName());
                    if(valueType == null) {
                        log.warn("当前字段 {} 的类型不支持参数赋值" , newInstanceField.getType().getSimpleName());
                        continue;
                    }
                    for (SysDbValue sysDbValue : dataList) {
                        String key = sysDbValue.getKey();
                        if(StringUtils.equalsIgnoreCase(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL , key) , newInstanceField.getName())){
                            FieldUtils.writeField(newInstanceField , fieldInstance , valueType.convertValue(sysDbValue.getValue()) , true);
                        }
                    }
                }
                return fieldInstance;
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("类型 {} {} {} 赋值出现错误" , e , fieldType , fieldInstance);
            }

        } else {
            //非Map类型的参数全部当做基本类型变量使用，如Integer、Double、String、Boolean等
            if(size > 1){
                log.warn("当前匹配的类型变量存在 {} 条数据，可能会存在错误配置" , size);
            }
            String result = dataList.get(0).getValue();
            IEnumValueType valueType = EnumValueType.getInstanceByFieldType(fieldType.getName());
            if(valueType !=  null){
                return valueType.convertValue(result);
            }
        }
        return null;
    }
}
