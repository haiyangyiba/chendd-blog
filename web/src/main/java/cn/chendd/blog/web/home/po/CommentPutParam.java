package cn.chendd.blog.web.home.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论管理数据保存对象
 *
 * @author chendd
 * @date 2021/2/22 20:58
 */
@Data
public class CommentPutParam {

    @ApiModelProperty(value = "功能模块，如博客文章、留言板")
    private String moduleKey;

    @ApiModelProperty(value = "关联ID")
    private Long targetId;

    @ApiModelProperty(value = "页码")
    private Long pageNumber;

    @ApiModelProperty(value = "回复内容")
    private String editorContent;

    @ApiModelProperty(value = "子评论ID")
    private Long childId;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "当前回复者ID")
    private Long createUserId;

    @ApiModelProperty(value = "被回复者用户ID")
    private Long replyUserId;

}
