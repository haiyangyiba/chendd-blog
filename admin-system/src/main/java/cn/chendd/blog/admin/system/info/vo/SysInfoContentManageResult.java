package cn.chendd.blog.admin.system.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内容管理列表查询对象
 *
 * @author chendd
 * @date 2020/8/29 20:32
 */
@Data
@ApiModel
public class SysInfoContentManageResult {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("标识")
    private String key;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改人")
    private String updateUser;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("排序")
    private String sortOrder;

}
