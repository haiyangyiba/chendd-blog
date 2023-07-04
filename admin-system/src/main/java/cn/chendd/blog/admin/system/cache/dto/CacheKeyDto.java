package cn.chendd.blog.admin.system.cache.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缓存key数据Dto
 *
 * @author chendd
 * @date 2021/11/20 19:25
 */
@Data
@NoArgsConstructor
public class CacheKeyDto {

    @ApiModelProperty("key名称")
    private String keyName;
    @ApiModelProperty("key类型")
    private String keyType;
    @ApiModelProperty("创建时间")
    private String creationTime;
    @ApiModelProperty("到期时间")
    private String expirationTime;
    @ApiModelProperty("最后访问时间")
    private String lastAccessTime;
    @ApiModelProperty("最后更新时间")
    private String lastUpdateTime;
    @ApiModelProperty("最新的创建和更新时间")
    private String latestOfCreationAndUpdateTime;
    @ApiModelProperty("空闲时间(秒)")
    private Integer timeToIdle;
    @ApiModelProperty("有效时间(秒)")
    private Integer timeToLive;

    public CacheKeyDto(String keyName, String keyType, String creationTime, String expirationTime,
                       String lastAccessTime, String lastUpdateTime, String latestOfCreationAndUpdateTime,
                       Integer timeToIdle, Integer timeToLive) {
        this.keyName = keyName;
        this.keyType = keyType;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
        this.lastAccessTime = lastAccessTime;
        this.lastUpdateTime = lastUpdateTime;
        this.latestOfCreationAndUpdateTime = latestOfCreationAndUpdateTime;
        this.timeToIdle = timeToIdle;
        this.timeToLive = timeToLive;
    }
}
