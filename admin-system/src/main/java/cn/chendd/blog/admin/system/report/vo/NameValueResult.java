package cn.chendd.blog.admin.system.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 键值对对象类型
 * @author chendd
 * @date 2021/6/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValueResult implements Serializable {

    @ApiModelProperty("显示名称")
    private String name;
    @ApiModelProperty("数据值")
    private String value;

}
