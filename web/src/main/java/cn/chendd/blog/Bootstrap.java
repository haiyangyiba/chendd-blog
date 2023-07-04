package cn.chendd.blog;

import cn.chendd.blog.base.spring.configuration.ContextConfiguration;
import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.annotation.ServletSecurity;
import java.util.TimeZone;

/**
 * 前台web项目启动类
 * 排出不需要的引用
 * ① 定时任务自动配置，所有定时任务均有后端admin实现；
 *
 * @author chendd
 * @date 2019/9/22 9:31
 */
@SpringBootApplication(exclude = {QuartzAutoConfiguration.class , FreeMarkerAutoConfiguration.class})
@MapperScan("cn.chendd.**.mapper")
@ForestScan(basePackages = {"cn.chendd.blog.**.client" , "cn.chendd.blog.**.forest.examples"})
@Import(value = {ContextConfiguration.class})
@EnableTransactionManagement
@EnableScheduling
public class Bootstrap {

    /**
     * 服务器启动
     *
     * @param args 启动参数[无参数]
     */
    public static void main(String[] args) {
        //设置系统时区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(Bootstrap.class, args);
    }

    @Value("${server.http-port}")
    private Integer httpPort;

    @Value("${server.port}")
    private Integer httpsPort;

    /**
     * 定制web-server服务器，绑定http访问协议与端口等
     * @return server服务器设置
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //设置安全性约束
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint(ServletSecurity.TransportGuarantee.CONFIDENTIAL.name());
                //设置约束条件
                SecurityCollection collection = new SecurityCollection();
                //拦截所有请求
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        //设置http访问
        Connector httpConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        httpConnector.setPort(httpPort);
        httpConnector.setRedirectPort(httpsPort);
        ///如果设置了setSecure为true则http不会自动转向至https
        //httpConnector.setSecure(true);
        tomcat.addAdditionalTomcatConnectors(httpConnector);
        return tomcat;
    }

    /**
     * 自定义cookie设置，解决https向http中发送cookie实现spring session的登录
     * 1.Cookie总是在跨站点请求中发送；
     * 2.不使用安全模式cookie；
     * @return cookie设置
     */
    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        //Cookie总是在跨站点请求中发送
        cookieSerializer.setSameSite(SameSiteCookies.LAX.getValue());
        cookieSerializer.setUseHttpOnlyCookie(true);
        cookieSerializer.setCookiePath("/");
        //不使用安全模式cookie
        cookieSerializer.setUseSecureCookie(false);
        //cookieSerializer.setDomainName("chendd.cn");
        cookieSerializer.setCookieName("SESSION");
        ///cookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return cookieSerializer;
    }

}
