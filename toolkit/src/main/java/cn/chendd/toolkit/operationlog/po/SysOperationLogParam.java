package cn.chendd.toolkit.operationlog.po;

import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.dynamic.DynamicTableName;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * 系统操作日志分页查询参数
 *
 * @author chendd
 * @date 2020/7/13 14:49
 */
@Data
@ApiModel
public class SysOperationLogParam implements DynamicTableName {

    @ApiModelProperty("数据id")
    private Long id;
    @ApiModelProperty("查询日期")
    private String date;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("页号")
    private Long pageNumber;
    @ApiModelProperty("每页显示数")
    private Long pageSize;

    @Override
    public String getTableName() {
        return SysOperationLog.class.getAnnotation(TableName.class).value();
    }

    /**
     * @return 动态表明后缀
     */
    @Override
    public String setTableNameCondition() {
        Date date = DateFormat.parseDatetime(this.date + " " + Constant.TIME_BEGIN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //获取到下个月的日期
        String month = String.format("%02d" , calendar.get(Calendar.MONTH) + 1);
        return "_" + year + month;
    }
}
