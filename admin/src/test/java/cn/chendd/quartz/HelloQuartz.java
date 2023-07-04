package cn.chendd.quartz;

import cn.chendd.core.utils.DateFormat;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 测试定时任务
 *
 * @author chendd
 * @date 2020/7/3 11:10
 */
public class HelloQuartz implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("-------->执行定时任务--" + DateFormat.formatDatetime());
    }
}
