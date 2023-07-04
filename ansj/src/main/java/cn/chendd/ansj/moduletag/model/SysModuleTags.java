package cn.chendd.ansj.moduletag.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模块标签表
 * @author chendd
 * @date 2020/08/08 21:06
 */
@Data
@ApiModel
@TableName("sys_module_tags")
@NoArgsConstructor
@AllArgsConstructor
public class SysModuleTags {

    @TableField("targetId")
    @ApiModelProperty(value = "关联功能ID")
    private Long targetId;

    @TableField("moduleName")
    @ApiModelProperty(value = "功能名称")
    private String moduleName;

    @TableField("tag")
    @ApiModelProperty(value = "关键词")
    private String tag;

}
