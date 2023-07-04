package cn.chendd.blog.admin.blog.tag.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章管理查询列表对象
 *
 * @author chendd
 * @date 2020/10/17 21:30
 */
@ApiModel
@Data
public class TagArticleResult {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章类型文本")
    private String typeText;

    @ApiModelProperty(value = "是否选中：'true'|'false'")
    private String selected;

}
