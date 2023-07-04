package cn.chendd.blog;

import cn.chendd.blog.base.spring.configuration.ContextConfiguration;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

/**
 * 服务器启动类
 * @author chendd
 * @date 2019/9/12 13:21
 */
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@Import(value = ContextConfiguration.class)
@MapperScan("cn.chendd.**.mapper")
@EnableTransactionManagement
@EnableCaching
public class Bootstrap {

    /**
    * 服务器启动
    * @param args 启动参数
    */
    public static void main(String[] args) {
        //设置系统时区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        //启动
        SpringApplication.run(Bootstrap.class , args);
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
        //cookieSerializer.setCookiePath("/");
        //不使用安全模式cookie
        cookieSerializer.setUseSecureCookie(false);
        //cookieSerializer.setDomainName("chendd.cn");
        cookieSerializer.setCookieName("SESSION");
        //cookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return cookieSerializer;
    }

}
