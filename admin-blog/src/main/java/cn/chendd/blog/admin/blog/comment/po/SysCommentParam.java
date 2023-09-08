package cn.chendd.blog.admin.blog.comment.po;

import cn.chendd.blog.admin.blog.comment.enums.EnumSysComment;
import cn.chendd.core.enums.EnumJacksonDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统留言参数对象
 *
 * @author chendd
 * @date 2021/2/19 20:54
 */
@Data
public class SysCommentParam {

    @ApiModelProperty(value = "功能模块，如博客文章、留言板")
    @JsonDeserialize(using = EnumJacksonDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EnumSysComment moduleKey;

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

    /**
     * 默认的页码
     * @return 页码
     */
    public Long getPageNumber() {
        if (this.pageNumber == null) {
            this.pageNumber = 1L;
        }
        return pageNumber;
    }
}
