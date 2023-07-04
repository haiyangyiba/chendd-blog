package cn.chendd.toolkit.quartz.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务管理查询条件
 *
 * @author chendd
 * @date 2020/7/4 23:12
 */
@Data
@ApiModel
public class QuartzManageParam {

    @ApiModelProperty("任务名称")
    private String jobName;
    @ApiModelProperty("任务组名")
    private String jobGroup;

}
