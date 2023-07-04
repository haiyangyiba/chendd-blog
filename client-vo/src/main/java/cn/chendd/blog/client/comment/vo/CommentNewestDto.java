package cn.chendd.blog.client.comment.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import cn.chendd.core.utils.Html;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 最新的用户留言评论信息
 *
 * @author chendd
 * @date 2021/4/29 13:40
 */
@Getter
@Setter
public class CommentNewestDto implements Serializable {

    private static final Byte MAX_LENGTH = 30;

    @ApiModelProperty("名称")
    private String realName;
    @ApiModelProperty("头像地址")
    private String portrait;
    @ApiModelProperty("用户来源")
    @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
    private EnumUserSource userSource;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 将回复内容去除html标签，并截取文本长度
     * @return 评论内容
     */
    public String getContent() {
        String text = Html.getPureText(this.content);
        if (StringUtils.length(text) > MAX_LENGTH) {
            return StringUtils.substring(text , 0 , MAX_LENGTH).concat("...");
        }
        return text;
    }

}