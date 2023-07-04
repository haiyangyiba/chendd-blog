package cn.chendd.blog.client.user.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户信息创建Dto对象
 *
 * @author chendd
 * @date 2021/4/29 10:46
 */
@Getter
@Setter
public class UserNewestDto implements Serializable {

    @ApiModelProperty("名称")
    private String realName;
    @ApiModelProperty("头像地址")
    private String portrait;
    @ApiModelProperty("用户来源")
    @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
    private EnumUserSource userSource;
    @ApiModelProperty("创建时间")
    private String createTime;

}
