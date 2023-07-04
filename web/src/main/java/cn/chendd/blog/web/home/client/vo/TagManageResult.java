package cn.chendd.blog.web.home.client.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chendd
 * @date 2020/7/15 10:42
 */
@Data
@ApiModel
public class TagManageResult {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("标签名称")
    private String tag;

    @ApiModelProperty("是否推荐")
    private Strong strong;

    @ApiModelProperty("排序")
    private String sortOrder;

    @ApiModelProperty("绑定文章个数")
    private Integer counts;

    @ApiModelProperty("显示样式")
    private String style;

    @Data
    public static class Strong {

        private String text , value;

    }
}