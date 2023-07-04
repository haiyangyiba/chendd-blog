package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chendd
 * @date 2020/8/2 19:26
 */
@ApiModel
@Data
public class ArticleResult {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章类型")
    private String type;

    @ApiModelProperty(value = "文章类型文本")
    private String typeText;

    @ApiModelProperty(value = "文章归属")
    private ArticleAscriptionEnum ascription;

    @ApiModelProperty(value = "显示顺序")
    private String sortOrder;

}
