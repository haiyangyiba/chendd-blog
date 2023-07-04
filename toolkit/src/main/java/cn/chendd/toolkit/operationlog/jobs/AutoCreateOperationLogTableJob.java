package cn.chendd.toolkit.operationlog.jobs;

import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.service.AutoCreateTableService;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import java.util.Calendar;

/**
 * 定时任务自动创建操作日志表
 * 系统日志表：每个月存储一张表，当前月判断生成下个月的表
 * @author chendd
 * @date 2020/7/8 22:33
 */
@Slf4j
public class AutoCreateOperationLogTableJob extends AbstractQuartzJob {

    final String tableName = SysOperationLog.class.getAnnotation(TableName.class).value().toLowerCase();

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {

        AutoCreateTableService autoService = SpringBeanFactory.getBean(AutoCreateTableService.class);
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        //获取到下个月的日期
        String month = String.format("%02d" , calendar.get(Calendar.MONTH) + 2);
        String fullTableName = tableName + "_" + year + month;
        log.info("判断表名 {} 是否存在" , fullTableName);
        Boolean exist = autoService.tableExist(fullTableName);
        if(exist) {
            String message = String.format("当前构造表名 %s 已存在，不执行创建表" , fullTableName);
            log.info("当前构造表名 {} 已存在，不执行创建表" , fullTableName);
            return new SuccessResult<>(message);
        }
        autoService.createTable(tableName , fullTableName);
        log.info("原始 {} 表 ，新表 {} 已经创建" , tableName , fullTableName);
        return new SuccessResult<>(String.format("表 %s 已经创建！" , fullTableName));
    }

}
