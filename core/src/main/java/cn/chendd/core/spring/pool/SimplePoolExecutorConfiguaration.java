package cn.chendd.core.spring.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 简单线程参数配置类
 *
 * @author chendd
 * @date 2020/8/16 18:27
 */
/*@Configuration
@EnableAsync*/
public class SimplePoolExecutorConfiguaration implements AsyncConfigurer {

    @Resource
    private SimplePoolProperty poolProperty;

    @Bean(name = "simplePoolExecutor")
    public Executor simplePoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolProperty.getCorePoolSize());
        executor.setMaxPoolSize(poolProperty.getMaxPoolSize());
        executor.setQueueCapacity(poolProperty.getQueueCapacity());
        executor.setKeepAliveSeconds(poolProperty.getKeepAliveSeconds());
        executor.setThreadNamePrefix(poolProperty.getThreadNamePrefix());
        //调用shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(poolProperty.getWaitForTasksToCompleteOnShutdown());
        //当pool达到max size时的新线程处理方式：不在开启新的线程，由调用者所在线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
