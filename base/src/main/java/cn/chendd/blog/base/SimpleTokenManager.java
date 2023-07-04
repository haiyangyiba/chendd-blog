package cn.chendd.blog.base;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 登录Token管理
 *
 * @author chendd
 * @date 2023/2/26 10:17
 */
public class SimpleTokenManager {

    /**
     * 实例对象
     */
    private static SimpleTokenManager instance;

    /**
     * 登录用户token
     */
    private Cache<String , String> cache;

    /**
     * 默认构造函数
     */
    private SimpleTokenManager() {
        this.cache = CacheBuilder.newBuilder()
                //设置设置值后5分钟过期
                .expireAfterWrite(3L , TimeUnit.MINUTES).build();
    }

    /**
     * 单例实例
     * @return 实例
     */
    public static SimpleTokenManager getInstance() {
        if (instance == null) {
            synchronized (SimpleTokenManager.class) {
                if (instance == null) {
                    instance = new SimpleTokenManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取缓存的数据
     * @param key key
     * @return value
     */
    public String get(String key) {
        return this.cache.getIfPresent(key);
    }

    /**
     * 设置缓存的数据
     * @param key key
     * @param value value
     */
    public void set(String key , String value) {
        this.cache.put(key , value);
    }

    /**
     * 【临时备选方案】设置request中的参数为key（浏览器ID + 操作系统名称 + 浏览器名称 + 浏览器版本）
     * 参考数据：
     * {"browser":"CHROME45","browserVersion":{"majorVersion":"45","minorVersion":"0","version":"45.0.2454.93"},"id":35131186,"operatingSystem":"WINDOWS_10"}
     * {"browser":"OPERA","browserVersion":{"majorVersion":"58","minorVersion":"0","version":"58.0.3135.127"},"id":35130369,"operatingSystem":"WINDOWS_10"}
     * {"browser":"BOT","id":16843020,"operatingSystem":"UNKNOWN"}
     * @param request request
     * @param value value
     */
    public void setAgent(HttpServletRequest request, String value) {
        this.set(this.getAgentKey(request) , value);
    }

    /**
     * 获取request中agent转换为key的数据
     * @return value
     */
    public String getAgent(HttpServletRequest request) {
        return this.get(this.getAgentKey(request));
    }

    /**
     * 从request中的agent参数中获取key
     * @param request request
     * @return key
     */
    private String getAgentKey(HttpServletRequest request) {
        String userAgentString = request.getHeader("user-agent");
        UserAgent agent = UserAgent.parseUserAgentString(userAgentString);
        StringBuilder keyBuilder = new StringBuilder(agent.getId());
        //操作系统名称
        OperatingSystem operatingSystem = agent.getOperatingSystem();
        if (operatingSystem != null) {
            keyBuilder.append(operatingSystem.toString());
        }
        //浏览器名称
        Browser browser = agent.getBrowser();
        if (browser != null) {
            keyBuilder.append(browser.toString());
        }
        //浏览器版本
        Version browserVersion = agent.getBrowserVersion();
        if (browserVersion != null) {
            keyBuilder.append(browserVersion.getVersion());
        }
        return keyBuilder.toString();
    }
}
