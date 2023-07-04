package cn.chendd.blog.base.page;

import cn.chendd.core.common.constant.Constant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页条件参数，页号与每页大小
 *
 * @author chendd
 * @date 2019/10/26 19:42
 */
@Getter
@Setter
public class Query {

    @ApiModelProperty("页码")
    private Long pageNumber;
    @ApiModelProperty("每页大小")
    private Long pageSize;

    @ApiModelProperty("排序名称")
    private String sortName;

    @ApiModelProperty("排序方式")
    private String sortOrder;

    public Long getPageNumber() {
        if (pageNumber == null) {
            this.pageNumber = 1L;
        }
        return pageNumber;
    }

    public Long getPageSize() {
        if (pageSize == null || pageSize > Constant.DEFAULT_MAX_PAGE_SIZE) {
            this.pageSize = Long.valueOf(Constant.DEFAULT_PAGE_SIZE);
        }
        return pageSize;
    }
}
