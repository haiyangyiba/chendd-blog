package cn.chendd.blog.client.article.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章点赞数据对象
 *
 * @author chendd
 * @date 2021/2/18 10:09
 */
@Data
public class ArticlePraisesResult implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户头像地址")
    private String portrait;
    @ApiModelProperty(value = "用户来源")
    @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
    private EnumUserSource userSource;
    @ApiModelProperty("点赞类型，多个以,(英文逗号)分割")
    private String dataTypes;
    @ApiModelProperty(value = "点赞类型描述")
    private String description;

}
