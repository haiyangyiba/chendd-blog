package cn.chendd.blog.admin.system.cache.service;

import cn.chendd.blog.admin.system.cache.dto.CacheKeyDto;
import cn.chendd.blog.admin.system.cache.dto.CacheManageDto;
import cn.chendd.blog.admin.system.cache.po.CacheManageParam;
import cn.chendd.core.result.BaseResult;

import java.util.List;

/**
 * 缓存管理Service接口定义
 *
 * @author chendd
 * @date 2021/11/13 20:47
 */
public interface CacheManageService {

    /**
     * 根据查询条件查询分页
     * @param param 参数对象
     * @return 分页数据
     */
    List<CacheManageDto> queryCachesList(CacheManageParam param);

    /**
     * 根据缓存名称查询缓存中数据
     * @param cacheName 缓存名称
     * @return 列表数据
     */
    List<CacheKeyDto> queryCacheByName(String cacheName);

    /**
     * 根据缓存名称和key名称删除缓存中的一项数据
     * @param cacheName 缓存名称
     * @param keyName key名称
     * @return 操作结果
     */
    BaseResult removeCacheElement(String cacheName, String keyName);

    /**
     * 根据缓存名称和key名称前缀删除缓存中的一些数据
     * @param cacheName 缓存名称
     * @param keyNameStart 缓存key名称前缀
     */
    void removeCacheElementStartWith(String cacheName, String keyNameStart);

    /**
     * 删除所有缓存
     * @return 操作结果
     */
    BaseResult removeAllCache();

    /**
     * 根据缓存名称删除缓存
     * @param cacheName 缓存名称
     * @return 操作结果
     */
    BaseResult removeSelectedCache(String cacheName);
}
