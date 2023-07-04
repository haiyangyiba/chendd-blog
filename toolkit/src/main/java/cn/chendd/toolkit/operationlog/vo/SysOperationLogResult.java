package cn.chendd.toolkit.operationlog.vo;

import cn.chendd.toolkit.operationlog.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统操作日志查询
 *
 * @author chendd
 * @date 2020/7/13 14:59
 */
@Data
@ApiModel
public class SysOperationLogResult {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("操作人ID")
    private Long userId;

    @ApiModelProperty("操作用户")
    private String userName;

    @ApiModelProperty("操作开始时间")
    private String beginTime;

    @ApiModelProperty("操作结束时间")
    private String endTime;

    @ApiModelProperty("耗时时间")
    private String runTime;

    @ApiModelProperty("用户IP")
    private String ip;

    @ApiModelProperty("操作简述")
    private String description;

    @ApiModelProperty("内容信息")
    private String content;

    @ApiModelProperty("功能模块id")
    private String moduleId;

    @ApiModelProperty("模块名称")
    private String moduleName;

    @ApiModelProperty("功能名称")
    private String functionName;

    @ApiModelProperty("操作结果")
    private ResultEnum result;

}
