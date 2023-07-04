package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章管理查询列表对象
 *
 * @author chendd
 * @date 2020/7/31 21:30
 */
@ApiModel
@Data
public class ArticleManageResult {

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

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "创建用户名称")
    private String createUsername;

    @ApiModelProperty(value = "最后修改时间")
    private String updateTime;

    @ApiModelProperty(value = "最后修改用户")
    private String updateUsername;

    @ApiModelProperty("访问次数")
    private Integer visitsNumber;

    @ApiModelProperty("是否存在内容")
    @JsonIgnore
    private String existContent;

    @ApiModelProperty(value = "文章状态")
    private ArticlePropertyResult property;

}
