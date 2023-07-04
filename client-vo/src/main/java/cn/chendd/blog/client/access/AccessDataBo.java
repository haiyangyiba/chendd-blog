package cn.chendd.blog.client.access;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 访问数量数据对象
 *
 * @author chendd
 * @date 2023/3/11 22:13
 */
@Data
public class AccessDataBo implements Serializable {

    @ApiModelProperty("昨日访问")
    private Integer yesterday;

    @ApiModelProperty("今日访问")
    private Integer today;

    @ApiModelProperty("今日外链")
    private Integer todayOutLink;

    @ApiModelProperty("单日最高")
    private Integer maxCount;

    @ApiModelProperty("单日最高日期")
    private String maxDay;

    @ApiModelProperty("总访问量")
    private Integer total;

}
