package cn.chendd.blog.client.article.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文章自定义内容Dto
 *
 * @author chendd
 * @date 2021/5/6 9:14
 */
@Getter
@Setter
public class ArticleCustomDto implements Serializable {

    @ApiModelProperty("文章ID")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建用户名称")
    private String createUsername;
    @ApiModelProperty("最后修改时间")
    private String updateTime;
    @ApiModelProperty("最后修改用户")
    private String updateUsername;
    @ApiModelProperty("访问次数")
    private Integer visitsNumber;

}
