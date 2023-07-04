package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 按页码查询文章内容数据集
 *
 * @author chendd
 * @date 2020/11/4 21:16
 */
@Data
public class ArticleContentResult implements Serializable {

    @ApiModelProperty("文章id")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章类型")
    private Long type;
    @ApiModelProperty("文章类型文本")
    private ArticleAscriptionEnum ascription;
    @ApiModelProperty("文章类型文本")
    private String typeText;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("最后修改时间")
    private String updateTime;
    @ApiModelProperty("文章内容")
    private String simpleContent;

    @ApiModelProperty("标签列表")
    private List<ArticleTagResult> tagList;
    @ApiModelProperty("文章附属属性")
    private ArticlePropertyResult property;

    @ApiModelProperty("访问个数")
    private Integer visitsCount;
    @ApiModelProperty("点赞个数")
    private Integer praisesCount;
    @ApiModelProperty("评论个数")
    private Integer commentCount;

}
