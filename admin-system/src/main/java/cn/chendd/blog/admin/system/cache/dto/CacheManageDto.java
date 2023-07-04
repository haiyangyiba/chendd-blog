package cn.chendd.blog.admin.system.cache.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 缓存管理分页查询Dto数据对象
 * 参数对象来源于：ehcache.xml文件，组件参数对象为：CacheConfiguration
 *
 * @author chendd
 * @date 2021/11/13 20:37
 */
@Data
public class CacheManageDto {

    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("内存最多存放个数")
    private int maxElementsInMemory;
    @ApiModelProperty("是否永久有效")
    private boolean eternal;
    @ApiModelProperty("允许闲置时间（毫秒）")
    private long timeToIdleSeconds;
    @ApiModelProperty("存活时间")
    private long timeToLiveSeconds;
    @ApiModelProperty("启用磁盘缓存")
    private boolean overflowToDisk;
    @ApiModelProperty("磁盘最大缓存数量")
    private int maxElementsOnDisk;
    @ApiModelProperty("持久化磁盘")
    private boolean diskPersistent;
    @ApiModelProperty("清理磁盘缓存线程间隔")
    private long diskExpiryThreadIntervalSeconds;
    @ApiModelProperty("缓存释放策略")
    private String memoryStoreEvictionPolicy;
    @ApiModelProperty("缓存个数")
    private Integer size;

}
