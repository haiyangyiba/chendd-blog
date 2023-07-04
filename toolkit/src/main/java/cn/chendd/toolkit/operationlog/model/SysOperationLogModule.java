package cn.chendd.toolkit.operationlog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统操作日志功能模块实体定义
 *
 * @author chendd
 * @date 2021/4/16 17:39
 */
@TableName("sys_operationlog_module")
@Data
@Accessors(chain = true)
public class SysOperationLogModule {

    @TableField("moduleId")
    @ApiModelProperty("功能模块ID")
    private String moduleId;

    @TableField("moduleName")
    @ApiModelProperty("模块名称")
    private Long moduleName;

    @TableField("functionName")
    @ApiModelProperty("功能名称")
    private String functionName;

}
