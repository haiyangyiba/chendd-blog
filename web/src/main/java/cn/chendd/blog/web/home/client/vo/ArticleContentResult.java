package cn.chendd.blog.web.home.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页按页码查询文章内容数据集
 *
 * @author chendd
 * @date 2020/11/4 21:16
 */
@Data
public class ArticleContentResult {

    @ApiModelProperty("文章id")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章类型")
    private Long type;
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

    @ApiModelProperty("文章属性")
    private Property property;

    @ApiModelProperty("访问个数")
    private Integer visitsCount;
    @ApiModelProperty("点赞个数")
    private Integer praisesCount;
    @ApiModelProperty("评论个数")
    private Integer commentCount;

    @Data
    public static class ArticleTagResult {

        @ApiModelProperty("tagID")
        private Long id;
        @ApiModelProperty("标签名称")
        private String tag;
        @ApiModelProperty("是否重点推荐")
        private String strong;

    }

    @Data
    public static class Property {

        @ApiModelProperty("封面图片")
        private String coverImageUrl;

        @ApiModelProperty("置顶")
        private ArticleTopping topping;

        @ApiModelProperty("推荐")
        private ArticleRecommend recommend;

    }

    @Getter
    @Setter
    public static class ArticleTopping {

        private String text;

    }

    @Getter
    @Setter
    public static class ArticleRecommend {

        private String text;

    }

}
