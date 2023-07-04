package cn.chendd.toolkit.quartz.vo;

import cn.chendd.toolkit.quartz.enums.EnumTriggerState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务管理Vo对象
 *
 * @author chendd
 * @date 2020/7/4 22:47
 */
@Data
@ApiModel
public class QuartzManageResult {

    @ApiModelProperty("任务名称")
    private String jobName;
    @ApiModelProperty("任务组名")
    private String jobGroup;
    @ApiModelProperty("任务描述")
    private String jobDescription;
    @ApiModelProperty("任务实现类")
    private String jobClassName;
    @ApiModelProperty("表达式")
    private String cronExpression;
    @ApiModelProperty("任务触发描述")
    private String triggerDescription;
    @ApiModelProperty("上次触发时间")
    private String prevFireTime;
    @ApiModelProperty("下次触发时间")
    private String nextFireTime;
    @ApiModelProperty("执行状态")
    private EnumTriggerState triggerState;
    @ApiModelProperty("开始时间")
    private String startTime;

}
