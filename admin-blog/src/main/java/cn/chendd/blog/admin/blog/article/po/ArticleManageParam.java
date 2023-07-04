package cn.chendd.blog.admin.blog.article.po;

import cn.chendd.blog.admin.blog.article.enums.EnumArticleSort;
import cn.chendd.blog.base.page.Query;
import cn.chendd.core.enums.EnumOrderType;
import cn.chendd.core.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 文章管理列表查询条件
 *
 * @author chendd
 * @date 2020/7/31 21:29
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class ArticleManageParam extends Query {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章类型")
    private String type;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty("排序名称")
    @JsonIgnore
    private String sortName;

    @ApiModelProperty("正序/倒叙")
    @JsonIgnore
    private String sortOrder;

    /**
     * @return 排序显示
     */
    public String getSortName() {
        EnumArticleSort sort = EnumUtil.getInstanceByName(this.sortName, EnumArticleSort.class);
        //默认按排序字段倒序 + 最后修改时间倒序
        if(sort == null) {
            String sortOrder = EnumArticleSort.sortOrder.getOrder(EnumOrderType.desc.name());
            String updateTime = EnumArticleSort.updateTime.getOrder(EnumOrderType.desc.name());
            return " order by " + sortOrder + " , " + updateTime;
        }
        return " order by " + sort.getOrder(this.getSortOrder());
    }

    public String getSortOrder() {
        if(EnumOrderType.hasEnum(this.sortOrder)) {
            return this.sortOrder;
        }
        return StringUtils.EMPTY;
    }

}
