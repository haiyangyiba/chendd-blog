package cn.chendd.toolkit.operationlog.model;

import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.dynamic.DynamicTableName;
import cn.chendd.toolkit.operationlog.enums.ResultEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;

/**
 * 系统操作日志实体定义
 *
 * @author chendd
 * @date 2019/9/20 16:20
 */
@TableName("Sys_operationlog")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysOperationLog implements DynamicTableName {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty("主键ID")
    private Long id;

    @TableField("userId")
    @ApiModelProperty("操作人ID")
    private Long userId;

    @TableField("userName")
    @ApiModelProperty("操作用户")
    private String userName;

    @TableField("beginTime")
    @ApiModelProperty("操作开始时间")
    private String beginTime;

    @TableField("endTime")
    @ApiModelProperty("操作结束时间")
    private String endTime;

    @TableField("endTime")
    @ApiModelProperty("操作执行时间")
    private Long runTime;

    @TableField("ip")
    @ApiModelProperty("用户IP")
    private String ip;

    @TableField("result")
    @ApiModelProperty("操作结果")
    private ResultEnum resultEnum;

    @TableField("description")
    @ApiModelProperty("操作简述")
    private String description;

    @TableField("content")
    @ApiModelProperty("内容信息")
    private String content;

    @TableField("moduleId")
    @ApiModelProperty("日志功能模块")
    private String moduleId;

    @JsonIgnore
    @TableField(exist = false)
    @ApiModelProperty("表日期")
    private String tableDate;

    public SysOperationLog(Long id , String ip , String beginTime) {
        this.id = id;
        this.ip = ip;
        this.beginTime = beginTime;
    }

    /**
     * @return 动态表明后缀
     */
    @Override
    public String setTableNameCondition() {
        Date date = DateFormat.parseDatetime(tableDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String month = String.format("%02d" , calendar.get(Calendar.MONTH) + 1);
        return "_" + year + month;
    }

}
