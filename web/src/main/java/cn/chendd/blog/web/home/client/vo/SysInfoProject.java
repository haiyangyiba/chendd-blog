package cn.chendd.blog.web.home.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统首页项目信息结果集映射对象
 *
 * @author chendd
 * @date 2020/10/31 20:41
 */
@Data
public class SysInfoProject {

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "分数")
    private Integer score;
    @ApiModelProperty(value = "logo地址")
    private String logo;
    @ApiModelProperty(value = "链接地址")
    private String link;
    @ApiModelProperty(value = "说明")
    private String description;
    @ApiModelProperty(value = "创建时间")
    private String createDate;

}
