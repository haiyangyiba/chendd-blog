package cn.chendd.blog.admin.system.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 统计图表类型结果集对象
 * @author chendd
 * @date 2021/6/26 21:38
 */
@Data
public class HomepageChartsResult implements Serializable {

    @ApiModelProperty("用户来源统计")
    private List<NameValueResult> userSources;

    @ApiModelProperty("文章年份统计")
    private List<NameValueResult> articleYears;

    @ApiModelProperty("文章访问量排名统计")
    private List<NameValueResult> articleVisits;

    @ApiModelProperty("文章分类数量统计")
    private List<NameValueResult> articleNumbers;

    @ApiModelProperty("数量统计")
    private List<NameValueResult> amountNumbers;

    @ApiModelProperty("推荐标签")
    private List<NameValueResult> tagNames;

}
