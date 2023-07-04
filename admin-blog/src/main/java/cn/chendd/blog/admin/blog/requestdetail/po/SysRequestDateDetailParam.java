package cn.chendd.blog.admin.blog.requestdetail.po;

import cn.chendd.blog.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统访问明细查询条件对象
 *
 * @author chendd
 * @date 2023/3/1 17:35
 */
@Data
public class SysRequestDateDetailParam extends Query {

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("是否外链")
    private String isOutLink;

    @ApiModelProperty("URL")
    private String url;

}
