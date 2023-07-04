package cn.chendd.blog.client.comment.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 评论查询结果集
 * 当前数据如果为创建者数据：childId、replyUserId、replyUserName、replyUserPortrait 均为空
 * @author chendd
 * @date 2021/2/20 19:48
 */
@Data
public class CommentQueryResult implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("目标资源ID")
    private Long targetId;
    @ApiModelProperty("内容信息")
    private String editorContent;
    @ApiModelProperty("目标回复ID")
    private Long childId;
    @ApiModelProperty("IP地址")
    private String ip;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建者ID")
    private Long createUserId;
    @ApiModelProperty("创建者名称")
    private String createUserName;
    @ApiModelProperty("创建者头像")
    private String createUserPortrait;
    @ApiModelProperty("创建者用户来源")
    @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
    private EnumUserSource createUserSource;

    @ApiModelProperty("被回复者ID")
    private Long replyUserId;
    @ApiModelProperty("被回复名称")
    private String replyUserName;
    @ApiModelProperty("被回复头像")
    private String replyUserPortrait;
    @ApiModelProperty("数据有效性状态")
    private String dataStatus;

    @ApiModelProperty("回复明细集合")
    private List<CommentQueryResult> childList = Lists.newArrayList();

}
