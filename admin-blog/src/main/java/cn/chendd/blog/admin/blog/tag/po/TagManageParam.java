package cn.chendd.blog.admin.blog.tag.po;

import cn.chendd.blog.base.enums.EnumWhether;
import cn.chendd.blog.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chendd
 * @date 2020/7/15 10:44
 */
@Data
@ApiModel
public class TagManageParam extends Query {

    @ApiModelProperty("标签名称")
    private String tag;

    @ApiModelProperty("是否推荐")
    private EnumWhether strong;

}
