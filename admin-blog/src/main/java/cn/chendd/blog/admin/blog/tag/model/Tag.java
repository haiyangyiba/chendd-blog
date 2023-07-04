package cn.chendd.blog.admin.blog.tag.model;

import cn.chendd.blog.base.enums.EnumWhether;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签/标记管理
 * @auth chendd
 * @date 2020/07/15 09:59
 */
@Data
@ApiModel
@TableName("blog_tag")
public class Tag {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("tag")
    @ApiModelProperty(value = "标签名称")
    private String tag;

    @TableField("strong")
    @ApiModelProperty(value = "是否重点强调")
    private EnumWhether strong;

    @TableField("sortOrder")
    @ApiModelProperty(value = "排序")
    private String sortOrder;


}
