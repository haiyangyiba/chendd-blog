package cn.chendd.blog.admin.blog.article.model;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 博客管理-文章管理
 * @author chendd
 * @date 2020/07/31 21:21
 */
@Data
@ApiModel
@TableName("blog_article")
public class Article {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("title")
    @ApiModelProperty(value = "文章标题")
    private String title;

    @TableField("type")
    @ApiModelProperty(value = "文章类型")
    private String type;

    @TableField("ascription")
    @ApiModelProperty(value = "文章归属")
    private ArticleAscriptionEnum ascription;

    @TableField("sortOrder")
    @ApiModelProperty(value = "显示顺序")
    private String sortOrder;

    @TableField("createTime")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField("createUsername")
    @ApiModelProperty(value = "创建用户名称")
    private String createUsername;

    @TableField("updateTime")
    @ApiModelProperty(value = "最后修改时间")
    private String updateTime;

    @TableField("updateUsername")
    @ApiModelProperty(value = "最后修改用户")
    private String updateUsername;

    @TableField("visitsNumber")
    @ApiModelProperty("访问次数")
    private Integer visitsNumber;

    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    private String dataStatus;
}
