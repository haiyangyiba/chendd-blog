package cn.chendd.blog.web.home.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统首页介绍信息结果集映射对象
 *
 * @author chendd
 * @date 2020/10/15 22:25
 */
@Data
public class SysInfoIntroduce {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "唯一标识")
    private String key;

    @ApiModelProperty(value = "排序")
    private String sortOrder;

    @ApiModelProperty(value = "内容信息")
    private String editorContent;

}
