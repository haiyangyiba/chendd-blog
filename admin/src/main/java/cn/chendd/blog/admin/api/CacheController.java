package cn.chendd.blog.admin.api;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.spring.SpringBeanFactory;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * cache示例Controller
 *
 * @author chendd
 * @date 2021/6/14 18:28
 */
@RestController
@RequestMapping(value = "/ehcache" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiVersion("1")
@Api(tags = "测试ehcache")
public class CacheController extends BaseController {

    @GetMapping
    @ApiOperation(value = "获取cacheManager" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 10)
    public String getCacheManager(){
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        return cacheManager.getClass().getName();
    }

    @GetMapping("/names")
    @ApiOperation(value = "获取ehcache所有名称" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 20)
    public Collection<String> getCacheNames(){
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        return cacheManager.getCacheNames();
    }

    @GetMapping("/{cacheName}")
    @ApiOperation(value = "获取cache缓存配置信息" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 30)
    public CacheConfiguration getCacheConfiguration(@ApiParam("cacheName") @PathVariable String cacheName){
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        assert cache != null;
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        return nativeCache.getCacheConfiguration();
    }

    @PostMapping("/cache/keys")
    @ApiOperation(value = "获取cache中所有key" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 35)
    public List getCacheByKey(@ApiParam("缓存名称") @RequestParam String cacheName) {
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        assert cache != null;
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        List keys = nativeCache.getKeys();
        List<String> resultList = Lists.newArrayList();
        for (Object key : keys) {
            if (key instanceof String) {
                resultList.add((String) key);
            } else if (key instanceof SimpleKey) {
                SimpleKey simpleKey = (SimpleKey) key;
                resultList.add(simpleKey.toString());
            } else {
                resultList.add(Objects.toString(key));
            }
        }
        return resultList;
    }

    @PostMapping("/cache/key")
    @ApiOperation(value = "获取cache中某个key对象" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 40)
    public net.sf.ehcache.Element getCacheByKey(@ApiParam("缓存名称") @RequestParam String cacheName ,
                                  @ApiParam("缓存key") @RequestParam String key) {
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        assert cache != null;
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        return nativeCache.get(key);
    }

    @PostMapping("/remove/{cacheName}")
    @ApiOperation(value = "根据名称清空某个key缓存" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 50)
    public boolean removeCacheByKey(@ApiParam("缓存名称") @PathVariable String cacheName,
                                    @ApiParam("缓存key") @PathVariable String key) {
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        return cache.evictIfPresent(key);
    }

    @PostMapping("/remove/{cacheName}")
    @ApiOperation(value = "根据名称清空缓存" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 60)
    public void clearCache(@ApiParam("缓存名称") @PathVariable String cacheName) {
        CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
        Objects.requireNonNull(cacheManager.getCache(cacheName)).invalidate();
    }

}
