package cn.chendd.blog.admin.blog.comment.model;

import cn.chendd.blog.admin.blog.comment.enums.EnumSysComment;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 留言评论表
 * @author chendd
 * @date 2021/02/19 20:46
 */
@Data
@ApiModel
@TableName("sys_comment")
public class SysComment {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("moduleKey")
    @ApiModelProperty(value = "功能模块，如博客文章、留言板")
    private EnumSysComment moduleKey;

    @TableField("targetId")
    @ApiModelProperty(value = "关联ID")
    private Long targetId;

    @TableField("editorContent")
    @ApiModelProperty(value = "回复内容")
    private String editorContent;

    @TableField("childId")
    @ApiModelProperty(value = "子评论ID")
    private Long childId;

    @TableField("ip")
    @ApiModelProperty(value = "ip地址")
    private String ip;

    @TableField("createUserId")
    @ApiModelProperty(value = "当前回复者ID")
    private Long createUserId;

    @TableField("replyUserId")
    @ApiModelProperty(value = "被回复者用户ID")
    private Long replyUserId;

    @TableField("createTime")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    private String dataStatus;

}
