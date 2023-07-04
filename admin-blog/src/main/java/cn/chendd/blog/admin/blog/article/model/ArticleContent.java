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
@TableName("blog_article_content")
public class ArticleContent {

    @TableId(value = "articleId" , type = IdType.ASSIGN_ID)
    @TableField("articleId")
    @ApiModelProperty(value = "主键ID")
    private Long articleId;

    @TableField("simpleContent")
    @ApiModelProperty(value = "纯文本内容")
    private String simpleContent;

    @TableField("editorContent")
    @ApiModelProperty(value = "html富文本")
    private String editorContent;

}
