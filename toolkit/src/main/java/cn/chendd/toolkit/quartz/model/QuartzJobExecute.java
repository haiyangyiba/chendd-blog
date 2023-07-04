package cn.chendd.toolkit.quartz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务执行历史
 * @auth chendd
 * @date 2020/07/05 22:44
 */
@Data
@ApiModel
@TableName("QUARTZ_JOB_EXECUTE")
public class QuartzJobExecute {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("jobGroup")
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @TableField("jobName")
    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @TableField("beginTime")
    @ApiModelProperty(value = "开始执行时间")
    private String beginTime;

    @TableField("endTime")
    @ApiModelProperty(value = "结束执行时间")
    private String endTime;

    @TableField("result")
    @ApiModelProperty(value = "执行结果")
    private String result;

    @TableField("remark")
    @ApiModelProperty(value = "结果信息")
    private String remark;

}
