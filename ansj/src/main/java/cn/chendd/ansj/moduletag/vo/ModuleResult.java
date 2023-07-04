package cn.chendd.ansj.moduletag.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 关键字分组统计
 *
 * @author chendd
 * @date 2021/2/8 11:19
 */
public class ModuleResult {

    @ApiModelProperty("关键字个数")
    private Integer count;

    @ApiModelProperty("关键字名称")
    private String label;

    @ApiModelProperty("字体大小")
    private String randomStyle;

    /**
     * 根据出现次数构造关键字颜色和大小
     * @return 颜色
     */
    public String getRandomStyle() {
        String result;
        switch (this.count % 10) {
            case 1:
                result = "color:#4535C5;font-size:13px;";
                break;
            case 2:
                result = "color:#5BC32C;font-size:13px;";
                break;
            case 3:
                result = "color:#9822C7;font-size:16px;";
                break;
            case 4:
                result = "color:#E46D06;font-size:17px;";
                break;
            case 5:
                result = "color:#475CD9;font-size:14px;";
                break;
            case 6:
                result = "color:#37C7BE;font-size:12px;";
                break;
            case 7:
                result = "color:#A0476D;font-size:15px;";
                break;
            default:
                result = "color:#878A84;font-size:15px;";
                break;
        }
        return result;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
