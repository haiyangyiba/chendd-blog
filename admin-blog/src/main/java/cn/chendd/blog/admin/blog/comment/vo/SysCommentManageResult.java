package cn.chendd.blog.admin.blog.comment.vo;

import cn.chendd.blog.base.enums.EnumDataStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统评论管理查询分页结果集
 * @author chendd
 * @date 2021/04/12 21:37
 */
@Data
public class SysCommentManageResult {

    @ApiModelProperty("评论ID")
    private Long id;

    @ApiModelProperty("数据ID")
    private String targetId;

    @ApiModelProperty("数据标题")
    private String targetName;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("留言时间")
    private String createTime;

    @ApiModelProperty("用户名称")
    private String realName;

    @ApiModelProperty("用户来源")
    private EnumUserSource userSource;

    @ApiModelProperty("数据状态")
    private EnumDataStatus dataStatus;

}
