package cn.chendd.blog.admin.system.info.po;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.base.page.FormParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统内容管理查询条件
 *
 * @author chendd
 * @date 2020/8/29 20:24
 */
@Data
public class SysInfoContentManageParam extends FormParam<SysInfoContent> {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("标识")
    private String key;

}
