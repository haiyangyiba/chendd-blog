package cn.chendd.blog.admin.blog.article.model;

import cn.chendd.blog.admin.blog.article.enums.EnumArticlePraises;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 博客文章点赞信息
 * @author chendd
 * @date 2021/02/16 21:51
 */
@Data
@ApiModel
@TableName("blog_article_praises")
public class ArticlePraises {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("articleId")
    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @TableField("dataType")
    @ApiModelProperty(value = "点赞类型")
    private EnumArticlePraises dataType;

    @TableField("createUserId")
    @ApiModelProperty(value = "用户id")
    private Long createUserId;

    @TableField("createUserName")
    @ApiModelProperty(value = "用户名称")
    private String createUserName;

    @TableField("createTime")
    @ApiModelProperty(value = "操作时间")
    private String createTime;

}
