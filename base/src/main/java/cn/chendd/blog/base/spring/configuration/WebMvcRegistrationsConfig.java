package cn.chendd.blog.base.spring.configuration;

import cn.chendd.blog.base.api.version.commponents.ApiRequestMappingHandlerMapping;
import cn.chendd.blog.base.spring.component.InterceptorConfiguration;
import cn.chendd.core.enums.EnumCommonFormatter;
import cn.chendd.core.spring.SpringBeanFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * Webmvc配置
 *
 * @author chendd
 * @date 2019/10/17 11:03
 */
@Configuration
@Slf4j
public class WebMvcRegistrationsConfig extends WebMvcConfigurationSupport {
//public class WebMvcRegistrationsConfig implements WebMvcConfigurer {
//public class WebMvcRegistrationsConfig extends WebMvcConfigurerAdapter {

    /**
     * 增加ApiVersion版本控制Mapping实现
     */
    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        System.out.println("WebMvcRegistrationsConfig.createRequestMappingHandlerMapping");
        return new ApiRequestMappingHandlerMapping();
    }

    /*@Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/templates/" , ".html").cache(false);
        super.configureViewResolvers(registry);
    }*/


    /**
     * 重写RequestMappingHandlerMapping后需要增加放行对于swagger地址放行的实现，否则找不到swagger页面
     * 资源文件过滤规则
     * 建议：页面html路径引用静态statics目录下文件使用../statics/xxx的方式，可兼容IDEA直接打开html文件
     *       页面html路径也可以简化引用，直接images/xxx
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //增加初始化自定义的用户登录拦截器
        this.addInterceptors();
        //statics 静态路径跟目录
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        //static/images等 静态文件路径简化路径引用，在sbt路径中可省略前缀
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/statics/assets/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/statics/images/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/statics/plugins/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/statics/js/")
                .setCacheControl(CacheControl.noCache());
        //增加swagger路径
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        //设置资源文件的生效级别优先于默认配置
        super.addResourceHandlers(registry);
    }

    @SuppressWarnings("unchecked")
    private void addInterceptors() {
        try{
            String[] names = SpringBeanFactory.getApplicationContext().getBeanNamesForType(InterceptorConfiguration.class);
            ApiRequestMappingHandlerMapping handlerMapping = SpringBeanFactory.getBean(ApiRequestMappingHandlerMapping.class);
            for (String name : names) {
                InterceptorConfiguration config = SpringBeanFactory.getBean(name , InterceptorConfiguration.class);
                String beanClassName = config.getBeanClassName();
                List<String> includeMapping = config.getIncludeMapping();
                List<String> excludeMapping = config.getExcludeMapping();
                HandlerInterceptor interceptor = (HandlerInterceptor) ConstructorUtils.invokeConstructor(Class.forName(beanClassName) , config);
                InterceptorRegistry interceptorRegistry = new InterceptorRegistry();
                interceptorRegistry.addInterceptor(interceptor).addPathPatterns(includeMapping).excludePathPatterns(excludeMapping);
                List<Object> rrrList = (List<Object>) MethodUtils.invokeMethod(interceptorRegistry , true , "getInterceptors");
                handlerMapping.setInterceptors(rrrList.toArray());
            }
            MethodUtils.invokeMethod(handlerMapping , true , "initInterceptors");
        } catch (Exception e) {
            log.error("WebMvcRegistrationsConfig.addInterceptors is error" , e);
        }
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(new LoginValidatorInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/blog/article/1289882275223195649.html" , "/blog/article/*.html" *//*, "/blog/article/praises/*.html"*//* , "/statics/**");*/
        super.addInterceptors(registry);
    }

    /** 默认静态资源处理器 **/

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //super.configureDefaultServletHandling(configurer);
        //configurer.enable("stati");
        configurer.enable();
    }

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 解决HandlerResponseBodyAdvice中的处理String类型结果报错的方案
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //解决Controller返回String报错的问题
        converters.add(0, new BufferedImageHttpMessageConverter());
        //增加Long类型处理
        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(Long.TYPE , ToStringSerializer.instance);
        customModule.addSerializer(Long.class , ToStringSerializer.instance);
        //开启枚举参数传递""时为null
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        objectMapper.registerModule(customModule);
        //增加@RequestBody注解转换参数时的非全量json支持，既：支持不完全属性转换
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converters.add(1, new MappingJackson2HttpMessageConverter(objectMapper));
        //解决...
        converters.add(2, new ResourceHttpMessageConverter());

        //converters.add(3 , new MappingJackson2HttpMessageConverter(objectMapper));
        //增加BufferedImage输出图片
    }

    //--设置url可支持路径通配，比如xxx的后台controller地址可以用xxx.do，xxx.action，xxx.html访问

    /*@Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true).setUseRegisteredSuffixPatternMatch(true);
        super.configurePathMatch(configurer);
    }*/

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
        super.configureContentNegotiation(configurer);
    }

    //--设置路径


    @Override
    protected void addFormatters(FormatterRegistry registry) {
        //增加自定义枚举类型转换
        registry.addConverterFactory(new EnumCommonFormatter());
        super.addFormatters(registry);
    }

    /**
     * 设置controller接口超时时间
     * @param configurer 配置
     */
    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {

        configurer.setDefaultTimeout(120 * 1000);
        configurer.registerCallableInterceptors(SpringBeanFactory.getBean(TimeoutCallableProcessingInterceptor.class));
        super.configureAsyncSupport(configurer);
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    /**
     * 解决Controller返回Object类型null格式化对象的问题，因为如果json不输出null，则当方法返回值为Object时就无任何数据输出了，
     * 同时将不会触发HandlerResponseBodyAdvice类的拦截输出逻辑
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}
