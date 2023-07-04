package cn.chendd.toolkit.dbproperty.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chendd
 * @date 2020/6/28 22:14
 */
@Data
@ApiModel
public class SysDbValueParam {

    @ApiModelProperty("参数名或参数值")
    private String param;
    @ApiModelProperty("参数组名")
    private String group;

}
