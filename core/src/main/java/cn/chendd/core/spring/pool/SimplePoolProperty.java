package cn.chendd.core.spring.pool;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 线程配置参数
 *
 * @author chendd
 * @date 2020/8/16 18:28
 */
@Component
@PropertySource(encoding = "utf-8" , value = "classpath:config/pool/pool.yaml")
@Data
public class SimplePoolProperty {

    @Value("${corePoolSize}")
    private Integer corePoolSize;

    @Value("${maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${queueCapacity}")
    private Integer queueCapacity;

    @Value("${keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Value("${threadNamePrefix}")
    private String threadNamePrefix;

    @Value("${waitForTasksToCompleteOnShutdown}")
    private Boolean waitForTasksToCompleteOnShutdown;

}
