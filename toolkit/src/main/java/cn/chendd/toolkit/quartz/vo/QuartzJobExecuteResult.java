package cn.chendd.toolkit.quartz.vo;

import cn.chendd.core.utils.DateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 定时任务执行明细查询
 * @author chendd
 * @date 2020/7/9 21:20
 */
@Data
@ApiModel
public class QuartzJobExecuteResult {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "开始执行时间")
    private String beginTime;

    @ApiModelProperty(value = "结束执行时间")
    private String endTime;

    @ApiModelProperty(value = "执行时间（毫秒）")
    private String executionTime;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "执行结果")
    private String result;

    @ApiModelProperty(value = "结果信息")
    private String remark;

    /**
     * 计算执行毫秒
     */
    public String getExecutionTime(){
        Date beginDate = null;
        try {
            beginDate = DateUtils.parseDate(beginTime, DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
            Date endDate = DateUtils.parseDate(endTime, DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
            return String.valueOf(endDate.getTime() - beginDate.getTime());
        } catch (ParseException e) {}
        return "-1";
    }
}
