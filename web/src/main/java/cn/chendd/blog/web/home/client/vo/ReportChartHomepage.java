package cn.chendd.blog.web.home.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页导出报表
 * @author chendd
 * @date 2021/10/16 19:50
 */
@Data
public class ReportChartHomepage {

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameValueResult {

        @ApiModelProperty("显示名称")
        private String name;
        @ApiModelProperty("数据值")
        private String value;

    }

}
