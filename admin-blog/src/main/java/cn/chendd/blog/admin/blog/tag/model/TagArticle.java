package cn.chendd.blog.admin.blog.tag.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 博客标签与文章关系表
 * @author chendd
 * @date 2020/10/17 22:44
 */
@Data
@ApiModel
@TableName("blog_tag_article")
public class TagArticle {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("tagId")
    @ApiModelProperty(value = "标签ID")
    private Long tagId;

    @TableField("articleId")
    @ApiModelProperty(value = "文章ID")
    private Long articleId;

}
