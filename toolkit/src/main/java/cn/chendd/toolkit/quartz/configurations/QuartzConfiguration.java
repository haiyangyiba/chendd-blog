package cn.chendd.toolkit.quartz.configurations;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 定时任务调度管理组件类
 *
 * @author chendd
 * @date 2020/7/2 23:43
 */
@Configuration
public class QuartzConfiguration {

    private static final String QUARTZ_CONFIG_LOCATION = "/config/quartz/quartz.properties";

    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * 读取配置文件信息quartz.properties
     */
    @Bean(name = "quartzProperties")
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        ClassPathResource resource = new ClassPathResource(QUARTZ_CONFIG_LOCATION);
        propertiesFactoryBean.setLocation(resource);
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzProperties")Properties properties) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(properties);
        factory.setStartupDelay(10);//延时10秒启动
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        //factory.setSchedulerName("DefaultQuartzScheduler");
        factory.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        factory.setAutoStartup(true);
        return factory;
    }

    /*
     * quartz初始化监听器，监听到工程启动自动加载定时任务
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler(@Qualifier("SchedulerFactory") SchedulerFactoryBean schedulerFactoryBean) throws IOException {
        return schedulerFactoryBean.getScheduler();
    }

}
