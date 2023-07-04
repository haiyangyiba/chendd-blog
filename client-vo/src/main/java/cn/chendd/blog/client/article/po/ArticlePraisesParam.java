package cn.chendd.blog.client.article.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章点赞参数传输对象
 *
 * @author chendd
 * @date 2021/2/18 15:03
 */
@Getter
@Setter
public class ArticlePraisesParam {

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "点赞类型")
    private String dataType;

    @ApiModelProperty(value = "用户ID")
    private Long createUserId;

    @ApiModelProperty(value = "用户名称")
    private String createUserName;

}
