package cn.chendd.blog.admin.blog.article.po;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章保存参数对象
 *
 * @author chendd
 * @date 2020/8/1 22:43
 */
@Data
@ApiModel
public class ArticleParam extends FormParam<Article> {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章类型")
    private String type;

    @ApiModelProperty(value = "文章归属")
    private ArticleAscriptionEnum ascription;

    @ApiModelProperty(value = "显示顺序")
    private String sortOrder;

}
