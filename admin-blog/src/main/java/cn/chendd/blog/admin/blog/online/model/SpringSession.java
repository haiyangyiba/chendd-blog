package cn.chendd.blog.admin.blog.online.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * null
 * @auth chendd
 * @date 2020/07/15 21:35
 */
@Data
@ApiModel
@TableName("SPRING_SESSION")
public class SpringSession {

    @TableId(type = IdType.ASSIGN_ID)
    @TableField("primaryId")
    @ApiModelProperty(value = "主键ID")
    private String primaryId;

    @TableField("sessionId")
    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    @TableField("creationTime")
    @ApiModelProperty(value = "创建时间")
    private Long creationTime;

    @TableField("lastAccessTime")
    @ApiModelProperty(value = "最后访问时间")
    private Long lastAccessTime;

    @TableField("maxInactiveInterval")
    @ApiModelProperty(value = "存活时间")
    private Integer maxInactiveInterval;

    @TableField("expiryTime")
    @ApiModelProperty(value = "到期时间")
    private Long expiryTime;

    @TableField("principalName")
    @ApiModelProperty(value = "主要名称")
    private String principalName;


}
