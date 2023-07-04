package cn.chendd.blog.admin.blog.online.po;

import cn.chendd.blog.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chendd
 * @date 2020/7/15 21:43
 */
@Data
@ApiModel
public class OnlineManageParam extends Query {

    @ApiModelProperty("数据范围")
    private String scope;

}
