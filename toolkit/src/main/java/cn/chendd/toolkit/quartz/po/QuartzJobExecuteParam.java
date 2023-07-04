package cn.chendd.toolkit.quartz.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务执行明细查询条件
 *
 * @author chendd
 * @date 2020/7/9 21:05
 */
@Data
@ApiModel
public class QuartzJobExecuteParam {

    @ApiModelProperty("开始时间")
    private String beginTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("任务组名")
    private String jobGroup;
    @ApiModelProperty("任务名称")
    private String jobName;
    @ApiModelProperty("页号")
    private Long pageNumber;
    @ApiModelProperty("每页大小")
    private Long pageSize;

}
