package cn.chendd.blog.client.article.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签文章数据对象，摘取一些简单的字段展示
 *
 * @author chendd
 * @date 2022/2/7 0:19
 */
@Data
public class TagArticleDto implements Serializable {

    @ApiModelProperty("文章ID")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("最后修改时间")
    private String lastUpdateTime;
    @ApiModelProperty("最后修改人")
    private String lastUpdateUser;
    @ApiModelProperty("访问次数")
    private Integer visitsNumber;

}
