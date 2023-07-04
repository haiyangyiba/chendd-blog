package cn.chendd.blog.admin.blog.tag.vo;

import cn.chendd.blog.base.enums.EnumWhether;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chendd
 * @date 2020/7/15 10:42
 */
@Data
@ApiModel
public class TagManageResult implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("标签名称")
    private String tag;

    @ApiModelProperty("是否推荐")
    private EnumWhether strong;

    @ApiModelProperty("排序")
    private String sortOrder;

    @ApiModelProperty("绑定文章个数")
    private Integer counts;

    @ApiModelProperty("显示样式")
    private String style;

}
