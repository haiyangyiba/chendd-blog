package cn.chendd.blog.client.infocontent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统首页项目信息结果集映射对象
 *
 * @author chendd
 * @date 2020/10/31 20:41
 */
@Data
public class SysInfoProjectResult implements Serializable {

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "分数")
    private Integer score;
    @ApiModelProperty(value = "logo地址")
    private String logo;
    @ApiModelProperty(value = "链接地址")
    private String link;
    @ApiModelProperty(value = "简单描述")
    private String description;
    @ApiModelProperty(value = "详细描述")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private String createDate;
    @ApiModelProperty("官方网站")
    private String website;
    @ApiModelProperty("标签链接")
    private String tag;
    //--------------文章属性
    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章id")
    private String id;
    @ApiModelProperty(value = "访问次数")
    private Integer visitsNumber;
    @ApiModelProperty("关联文章")
    private List<String> articles;

}
