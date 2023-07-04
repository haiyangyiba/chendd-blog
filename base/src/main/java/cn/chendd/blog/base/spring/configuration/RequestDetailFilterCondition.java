package cn.chendd.blog.base.spring.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 系统请求过滤器
 *
 * @author chendd
 * @date 2023/2/26 15:06
 */
public class RequestDetailFilterCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String applicationVersion = context.getEnvironment().getProperty("application.formatted-version");
        //只对web项目生效
        return StringUtils.startsWith(applicationVersion , "vweb-");
    }
}
