package cn.chendd.blog.client.friendslink;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 友情链接获取前N条数据Dto
 *
 * @author chendd
 * @date 2021/4/30 15:53
 */
@Getter
@Setter
public class FriendsLinkNewestDto implements Serializable {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("链接地址")
    private String link;
    @ApiModelProperty("logo图标")
    private String logo;
    @ApiModelProperty("说明")
    private String remark;
    @ApiModelProperty("访问个数")
    private Integer counts;

}
