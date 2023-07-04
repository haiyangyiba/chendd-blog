package cn.chendd.blog.base.spring.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;

/**
 * Freemarker配置类
 *
 * @author chendd
 * @date 2022/4/15 22:10
 */
@Configuration
public class FreemarkerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setTemplateLoaderPaths("classpath:/freemarker");
        return configurer;
    }

}
