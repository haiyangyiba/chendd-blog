package cn.chendd.blog.admin.blog.friendslink.model;

import cn.chendd.blog.base.enums.EnumStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 友情链接数据信息表
 * @auth chendd
 * @date 2020/06/27 21:39
 */
@Data
@ApiModel
@TableName("blog_friends_link")
public class FriendsLink {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("title")
    @ApiModelProperty(value = "标题")
    private String title;

    @TableField("link")
    @ApiModelProperty(value = "链接地址")
    private String link;

    @TableField("logo")
    @ApiModelProperty(value = "logo地址")
    private String logo;

    @TableField("domainTag")
    @ApiModelProperty(value = "站点标识")
    private String domainTag;

    @TableField("sortOrder")
    @ApiModelProperty(value = "排序")
    private String sortOrder;

    @TableField("remark")
    @ApiModelProperty(value = "排序")
    private String remark;

    @TableField("status")
    @ApiModelProperty(value = "状态，启用/禁用")
    private EnumStatus status;


}
