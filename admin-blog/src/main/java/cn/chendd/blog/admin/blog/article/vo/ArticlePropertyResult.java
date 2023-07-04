package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.admin.blog.article.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章状态结果对象
 *
 * @author chendd
 * @date 2020/8/2 22:27
 */
@ApiModel
@Data
public class ArticlePropertyResult implements Serializable {

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("是否有内容体")
    private EnumArticleContent content;

    @ApiModelProperty("发布状态")
    private EnumArticleState state;

    @ApiModelProperty("置顶")
    private EnumArticleTopping topping;

    @ApiModelProperty("推荐")
    private EnumArticleRecommend recommend;

    @ApiModelProperty("封面")
    private EnumArticleCover cover;

    @ApiModelProperty("封面图片地址")
    private String coverImageUrl;

    @ApiModelProperty("评论")
    private EnumArticleComment comment;

}
