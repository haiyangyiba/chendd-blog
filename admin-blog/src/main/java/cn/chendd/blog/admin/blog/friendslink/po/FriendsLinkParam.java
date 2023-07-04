package cn.chendd.blog.admin.blog.friendslink.po;

import cn.chendd.blog.base.enums.EnumStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 存储友链数据对象
 *
 * @author chendd
 * @date 2020/6/28 15:15
 */
@ApiModel
@Data
public class FriendsLinkParam {

    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "站点标题")
    private String title;
    @ApiModelProperty(value = "链接地址")
    private String link;
    @ApiModelProperty(value = "logo地址")
    private String logo;
    @ApiModelProperty(value = "站点标识")
    private String domainTag;
    @ApiModelProperty(value = "排序")
    private String sortOrder;
    @ApiModelProperty(value = "排序")
    private String remark;
    @ApiModelProperty(value = "状态，启用/禁用")
    private EnumStatus status;

}
