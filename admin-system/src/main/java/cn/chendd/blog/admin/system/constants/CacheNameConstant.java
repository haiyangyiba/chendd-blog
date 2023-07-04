package cn.chendd.blog.admin.system.constants;

/**
 * 系统管理常量类定义
 *
 * @author chendd
 * @date 2021/6/14 20:57
 */
public interface CacheNameConstant {

    /**
     * 自动过期缓存，缓存1天
     */
    String AUTO_EXPIRED_1_DAY = "Auto_Expired_1_Day";
    /**
     * 自动过期缓存，缓存10分钟
     */
    String AUTO_EXPIRED_10_MINUTE = "Auto_Expired_10_Minute";
    /**
     * 系统不过期缓存，触发重新加载缓存
     */
    String NOT_EXPIRED = "Not_Expired";
    /**
     * 按标签名称缓存
     */
    String TAG_CACHE = "Tag_Cache_Detail";

}
