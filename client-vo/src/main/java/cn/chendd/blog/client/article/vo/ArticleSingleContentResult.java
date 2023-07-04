package cn.chendd.blog.client.article.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 单条文章数据对象
 *
 * @author chendd
 * @date 2020/11/14 22:36
 */
@Data
public class ArticleSingleContentResult implements Serializable {

    @ApiModelProperty("文章ID")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章分类ID")
    private String type;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建用户名称")
    private String createUsername;
    @ApiModelProperty("最后修改时间")
    private String updateTime;
    @ApiModelProperty("文章内容")
    private String editorContent;
    @ApiModelProperty("文章排序")
    private String sortOrder;

    @ApiModelProperty("上一篇和下一篇")
    private ArticlePosition articlePosition;
    @ApiModelProperty("文章标签")
    private List<ArticleTag> articleTags;
    @ApiModelProperty("文章关键字")
    private List<ArticleKeywords> articleKeywords;
    @ApiModelProperty("感兴趣的文章")
    private List<ArticleDependent> articleDependents;
    @ApiModelProperty("文章属性")
    private ArticleProperty articleProperty;

    @ApiModelProperty("是否包含代码块")
    private Boolean hasCode = false;

    /**
     * 文章上一篇和下一篇
     */
    @Data
    public static class ArticlePosition implements Serializable {

        @ApiModelProperty("上一篇ID")
        private Long prevId = null;
        @ApiModelProperty("上一篇名称")
        private String prevTitle = "没有了";
        @ApiModelProperty("下一篇ID")
        private Long nextId = prevId;
        @ApiModelProperty("下一篇名称")
        private String nextTitle = prevTitle;

        @JSONField(serialize = false)
        @JsonIgnore
        private Long id;
        @JSONField(serialize = false)
        @JsonIgnore
        private String name;
        @JSONField(serialize = false)
        @JsonIgnore
        private String position;

        /**
         * 将查询的上一篇和下一篇的集合类型结构转换为一条数据的结构
         * @param list 上一篇union下一篇
         * @return 单条数据
         */
        public static ArticlePosition convertList2Entity(List<ArticlePosition> list) {
            ArticlePosition articlePosition = new ArticlePosition();
            for (ArticlePosition item : list) {
                String position = item.getPosition();
                if (AritclePostionEnum.prev.name().equals(position)) {
                    articlePosition.setPrevId(item.getId());
                    articlePosition.setPrevTitle(item.getName());
                } else if (AritclePostionEnum.next.name().equals(position)) {
                    articlePosition.setNextId(item.getId());
                    articlePosition.setNextTitle(item.getName());
                }
            }
            return articlePosition;
        }

    }

    /**
     * 文章标签
     */
    @Data
    public static class ArticleTag implements Serializable {

        @ApiModelProperty("标签id")
        private String id;
        @ApiModelProperty("标签名称")
        private String tag;
        @ApiModelProperty("是否推荐")
        private String strong;

    }

    @Data
    public static class ArticleKeywords implements Serializable {

        @ApiModelProperty("关键字个数")
        private Integer count;
        @ApiModelProperty("关键字文本")
        private String label;
        @ApiModelProperty("关键字样式")
        private String randomStyle;

    }

    @Data
    public static class ArticleDependent implements Serializable {

        @ApiModelProperty("文章ID")
        private Long id;
        @ApiModelProperty("文章标题")
        private String title;

    }

    /**
     * 文章上一篇和下一篇枚举定义
     */
    public enum AritclePostionEnum implements Serializable {

        /**
         * 上一篇
         */
        prev,
        /**
         * 下一篇
         */
        next,
        ;

    }

    /**
     * 文章关联属性
     */
    @Getter
    @Setter
    public static class ArticleProperty implements Serializable {

        @ApiModelProperty("文章ID")
        private Long articleId;

        @ApiModelProperty("评论")
        private EnumArticleComment comment;

        /**
         * 评论属性
         */
        public enum EnumArticleComment {

            close("评论"),
            ;

            private String text;

            EnumArticleComment(String text) {
                this.text = text;
            }

            public String getValue() {
                return this.name();
            }
        }

    }

}
