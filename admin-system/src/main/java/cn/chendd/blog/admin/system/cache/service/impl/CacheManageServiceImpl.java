package cn.chendd.blog.admin.system.cache.service.impl;

import cn.chendd.blog.admin.system.cache.dto.CacheKeyDto;
import cn.chendd.blog.admin.system.cache.dto.CacheManageDto;
import cn.chendd.blog.admin.system.cache.po.CacheManageParam;
import cn.chendd.blog.admin.system.cache.service.CacheManageService;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.NoneResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.google.common.collect.Lists;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 缓存管理Service接口实现
 *
 * @author chendd
 * @date 2021/11/13 21:12
 */
@Service
public class CacheManageServiceImpl implements CacheManageService {

    @Resource
    private EhCacheCacheManager cacheManager;

    @Override
    @Log(description = "缓存列表查询")
    public List<CacheManageDto> queryCachesList(CacheManageParam param) {
        List<CacheManageDto> dataList = Lists.newArrayList();
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            assert cache != null;
            net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
            CacheConfiguration config = nativeCache.getCacheConfiguration();
            CacheManageDto cacheDto = new CacheManageDto();
            BeanUtils.copyProperties(config , cacheDto , "memoryStoreEvictionPolicy");
            cacheDto.setMemoryStoreEvictionPolicy(config.getMemoryStoreEvictionPolicy().toString());
            cacheDto.setSize(nativeCache.getSize());
            dataList.add(cacheDto);
        }
        return dataList;
    }

    @Override
    @Log(description = "单个缓存名称查询")
    public List<CacheKeyDto> queryCacheByName(String cacheName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        assert cache != null;
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        @SuppressWarnings("unchecked")
        List<Object> cacheKeys = nativeCache.getKeys();
        List<CacheKeyDto> dataList = Lists.newArrayList();
        for (Object key : cacheKeys) {
            Element element = nativeCache.get(key);
            if (element == null) {
                continue;
            }
            String keyName;
            if (key instanceof String) {
                keyName = (String) key;
            } else if (key instanceof SimpleKey) {
                SimpleKey simpleKey = (SimpleKey) key;
                keyName = simpleKey.toString();
            } else {
                keyName = Objects.toString(key);
            }
            dataList.add(new CacheKeyDto(
                keyName , key.getClass().getName() ,
                DateFormat.formatDatetime(element.getCreationTime()) ,
                DateFormat.formatDatetime(element.getExpirationTime()) ,
                DateFormat.formatDatetime(element.getLastAccessTime()) ,
                DateFormat.formatDatetime(element.getLastUpdateTime()) ,
                DateFormat.formatDatetime(element.getLatestOfCreationAndUpdateTime()) ,
                element.getTimeToIdle() , element.getTimeToLive()
            ));
        }
        return dataList;
    }

    @Override
    @Log(description = "缓存数据删除[根据缓存名称和缓存标识]")
    public BaseResult removeCacheElement(String cacheName, String keyName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            return new NoneResult(String.format("未找到缓存 %s" , cacheName));
        }
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        boolean remove = nativeCache.remove(keyName);
        if (remove) {
            SuccessResult result = new SuccessResult();
            result.setMessage("缓存清除成功！");
            return result;
        }
        return new NoneResult(String.format("未找到缓存 %s 中的 %s" , cacheName , keyName));
    }

    @Override
    @Log(description = "'缓存数据删除[名称=' + #cacheName + ',key前缀=' + #keyNameStart")
    public void removeCacheElementStartWith(String cacheName, String keyNameStart) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            return;
        }
        net.sf.ehcache.Cache nativeCache = (net.sf.ehcache.Cache) cache.getNativeCache();
        List<?> keys = nativeCache.getKeys();
        keys.forEach(item -> {
            if (item instanceof String && ((String) item).startsWith(keyNameStart)) {
                nativeCache.remove(item);
            }
        });
    }

    @Override
    @Log(description = "缓存数据删除[全量缓存]")
    public BaseResult removeAllCache() {
        CacheManager cacheManager = this.cacheManager.getCacheManager();
        if (cacheManager != null) {
            cacheManager.clearAll();
        }
        return new NoneResult("全部缓存清除成功！");
    }

    @Override
    @Log(description = "'缓存数据删除[指定缓存' + #cacheName + ']'")
    public BaseResult removeSelectedCache(String cacheName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
        return new NoneResult("所选缓存清除成功！");
    }

}
