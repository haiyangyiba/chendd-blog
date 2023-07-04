package cn.chendd.blog.admin.blog.friendslink.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 友链访问次数
 * @author chendd
 * @date 2020/07/23 22:03
 */
@Data
@ApiModel
@TableName("blog_friends_link_count")
public class FriendsLinkCount {

    @TableId(value = "flId" , type = IdType.ASSIGN_ID)
    @TableField("flId")
    @ApiModelProperty(value = "友链ID，主键ID")
    private Long flId;

    @TableField("count")
    @ApiModelProperty(value = "次数")
    private Integer count;

}
