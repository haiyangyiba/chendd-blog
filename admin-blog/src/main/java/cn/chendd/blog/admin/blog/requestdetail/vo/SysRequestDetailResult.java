package cn.chendd.blog.admin.blog.requestdetail.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统访问明细查询管理对象
 *
 * @author chendd
 * @date 2020/7/27 19:47
 */
@Data
public class SysRequestDetailResult {

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
