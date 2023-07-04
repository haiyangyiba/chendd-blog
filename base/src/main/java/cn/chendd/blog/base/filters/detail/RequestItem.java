package cn.chendd.blog.base.filters.detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统请求详细记录
 * @auth chendd
 * @date 2020/07/20 19:00
 */
@Data
public class RequestItem {

    @ApiModelProperty(value = "请求url路径")
    private String url;

    @ApiModelProperty("请求来源")
    private String referer;

    @ApiModelProperty(value = "浏览器Agent")
    private String userAgent;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "访问日期")
    private String createDate;

    @ApiModelProperty(value = "访问时间")
    private String createTime;

}
