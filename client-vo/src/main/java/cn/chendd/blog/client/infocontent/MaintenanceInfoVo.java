package cn.chendd.blog.client.infocontent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 文章明细页面的各类参数信息对象
 *
 * @author chendd
 * @date 2021/11/6 15:52
 */
@Data
public class MaintenanceInfoVo implements Serializable {

    /**
     * 建站时间
     */
    @ApiModelProperty("建站时间")
    private Integer createSiteTime;
    /**
     * 用户总数
     */
    @ApiModelProperty("用户数量")
    private Integer users;
    /**
     * 文章总数
     */
    @ApiModelProperty("文章数量")
    private Integer articles;
    /**
     * 评论数量
     */
    @ApiModelProperty("评论数量")
    private Integer comments;
    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Integer praises;
    /**
     * 最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private String lastUpdateTime;
    /**
     * 年份数量
     */
    @ApiModelProperty("年份数据集")
    private List<String> years;
    /**
     * 标签云
     */
    @ApiModelProperty("标签数据集")
    private List<String> tags;
    /**
     * 点赞最多的前N篇文章
     */
    @ApiModelProperty("点赞文章")
    private List<ArticleRecord> pariseList;
    /**
     * 评论最多的前N篇文章
     */
    @ApiModelProperty("评论文章")
    private List<ArticleRecord> commentList;

    /**
     * 文章数据信息
     */
    @Getter
    @Setter
    public static class ArticleRecord implements Serializable {

        /**
         * 文章ID
         */
        @ApiModelProperty("文章ID")
        private Long articleId;
        /**
         * 数据
         */
        @ApiModelProperty("数据")
        private Integer counts;
        /**
         * 文章标题
         */
        @ApiModelProperty("文章标题")
        private String title;

    }

}
