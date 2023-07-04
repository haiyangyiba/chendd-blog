package cn.chendd.blog.admin.blog.requestdetail.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统访问请求统计
 * @auth chendd
 * @date 2020/07/23 19:02
 */
@Data
@ApiModel
@TableName("sys_request_statistics")
public class SysRequestStatistics {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("date")
    @ApiModelProperty(value = "访问日期")
    private String date;

    @TableField("count")
    @ApiModelProperty(value = "访问数量")
    private Integer count;

    @TableField("outLinkCount")
    @ApiModelProperty(value = "外站访问数量")
    private Integer outLinkCount;


}
