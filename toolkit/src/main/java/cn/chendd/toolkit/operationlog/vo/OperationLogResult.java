package cn.chendd.toolkit.operationlog.vo;

import cn.chendd.toolkit.operationlog.enums.ResultEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统操作日志参数
 *
 * @author chendd
 * @date 2021/4/16 20:03
 */
public class OperationLogResult {

    @ApiModelProperty("功能名称")
    private String name;

    @ApiModelProperty("方法描述")
    private String description;

    @ApiModelProperty("运行时间")
    private String runTime;

    @ApiModelProperty("操作结果")
    private ResultEnum result;

    @ApiModelProperty("方法结构")
    private MethodSignatureResult methodSignature;

    public OperationLogResult() {
        super();
    }

    public OperationLogResult(String name , String description, String runTime, ResultEnum result, MethodSignatureResult methodSignature) {
        this.name = name;
        this.description = description;
        this.runTime = runTime;
        this.result = result;
        this.methodSignature = methodSignature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public MethodSignatureResult getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(MethodSignatureResult methodSignature) {
        this.methodSignature = methodSignature;
    }

}
