package cn.chendd.blog.admin.system.info.po;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内容管理数据保存对象
 *
 * @author chendd
 * @date 2020/8/29 23:10
 */
@Data
@ApiModel
public class SysInfoContentParam extends FormParam<SysInfoContent> {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("排序")
    private String sortOrder;
    @ApiModelProperty("唯一标识")
    private String key;
    @ApiModelProperty("内容信息")
    private String editorContent;
}
