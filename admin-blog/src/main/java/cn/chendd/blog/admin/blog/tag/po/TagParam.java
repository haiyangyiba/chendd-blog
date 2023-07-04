package cn.chendd.blog.admin.blog.tag.po;

import cn.chendd.blog.admin.blog.tag.model.Tag;
import cn.chendd.blog.base.enums.EnumWhether;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签管理保存对象
 *
 * @author chendd
 * @date 2020/7/15 19:23
 */
@ApiModel
@Data
public class TagParam extends FormParam<Tag> {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("标签名称")
    private String tag;
    @ApiModelProperty("是否推荐")
    private EnumWhether strong;
    @ApiModelProperty("排序")
    private String sortOrder;

}
