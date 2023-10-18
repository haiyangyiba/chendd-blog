package cn.chendd.toolkit.quartz.po;

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
public class QuartzParam {

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
    @ApiModelProperty("任务参数json")
    private String jobData;

}
