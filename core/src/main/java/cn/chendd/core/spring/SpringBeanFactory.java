package cn.chendd.core.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * SpringBean组件获取工具类
 *
 * @author chendd
 * @date 2019/9/13 18:19
 */
@Data
@Component
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactory.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String beanName , Class<T> clazz){
        T bean = applicationContext.getBean(beanName , clazz);
        return bean;
    }

    public static Object getBean(String beanName) {
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }

    public static  <T> T getBean(Class<T> clazz){
        T bean = applicationContext.getBean(clazz);
        return bean;
    }

    public static String getActiveProfile() {
        return getEnvironment().getActiveProfiles()[0];
    }

    public static Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

}
