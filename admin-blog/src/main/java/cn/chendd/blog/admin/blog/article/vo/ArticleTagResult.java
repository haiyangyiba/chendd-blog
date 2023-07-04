package cn.chendd.blog.admin.blog.article.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chendd
 * @date 2020/11/7 19:38
 */
@Data
public class ArticleTagResult implements Serializable {

    @ApiModelProperty("tagID")
    private Long id;
    @ApiModelProperty("标签名称")
    private String tag;
    @ApiModelProperty("是否重点推荐")
    private String strong;

}
