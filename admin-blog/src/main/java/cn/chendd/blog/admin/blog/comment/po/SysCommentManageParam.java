package cn.chendd.blog.admin.blog.comment.po;

import cn.chendd.blog.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统评论管理查询分页结果集
 * @author chendd
 * @date 2021/04/12 21:37
 */
@Data
public class SysCommentManageParam extends Query {

    @ApiModelProperty("开始日期")
    private String beginDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("文章名称")
    private String title;

}
