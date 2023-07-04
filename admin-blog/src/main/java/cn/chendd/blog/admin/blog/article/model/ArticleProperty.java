package cn.chendd.blog.admin.blog.article.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章内容实体对象
 *
 * @author chendd
 * @date 2020/8/2 22:00
 */
@Data
@ApiModel
@TableName("blog_article_property")
public class ArticleProperty {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "文章ID")
    @TableField("articleId")
    private Long articleId;

    @ApiModelProperty(value = "功能点类型")
    @TableField("property")
    private String property;

    @ApiModelProperty(value = "类型值")
    @TableField("value")
    private String value;

}
