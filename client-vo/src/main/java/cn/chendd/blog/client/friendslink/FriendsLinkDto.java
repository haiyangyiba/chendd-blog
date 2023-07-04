package cn.chendd.blog.client.friendslink;

import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import cn.chendd.core.enums.EnumJacksonDeserializer;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class FriendsLinkDto implements Serializable {


    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("链接地址")
    private String link;
    @ApiModelProperty("域名关键字")
    private String domainTag;
    @ApiModelProperty("logo图标")
    private String logo;
    @ApiModelProperty("说明")
    private String remark;
    @ApiModelProperty("访问个数")
    private Integer counts;
    @ApiModelProperty("状态")
    @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
    private EnumStatus status;
    @ApiModelProperty("状态")
    private String sortOrder;

}
