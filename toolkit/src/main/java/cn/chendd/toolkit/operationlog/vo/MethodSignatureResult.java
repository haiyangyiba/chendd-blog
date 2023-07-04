package cn.chendd.toolkit.operationlog.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 参数值对象
 *
 * @author chendd
 * @date 2021/4/16 19:53
 */
public class MethodSignatureResult {


    @ApiModelProperty("方法参数")
    private Map<String , Object> methodParameter;

    @ApiModelProperty("返回值")
    private Object methodReturn;

    @ApiModelProperty("方法信息")
    private String methodInfo;

    public Map<String, Object> getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(Map<String, Object> methodParameter) {
        this.methodParameter = methodParameter;
    }

    public Object getMethodReturn() {
        return methodReturn;
    }

    public void setMethodReturn(Object methodReturn) {
        this.methodReturn = methodReturn;
    }

    public String getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(String methodInfo) {
        this.methodInfo = methodInfo;
    }

}
