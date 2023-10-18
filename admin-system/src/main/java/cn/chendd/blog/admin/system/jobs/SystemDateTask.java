package cn.chendd.blog.admin.system.jobs;

import cn.chendd.blog.base.log.BaseLog;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import com.alibaba.fastjson.JSON;
import org.quartz.JobExecutionContext;

/**
 * 任务调度参数获取定时任务
 *
 * @author chendd
 * @date 2023/10/18 13:24
 */
public class SystemDateTask extends AbstractQuartzJob {

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        BaseLog.getLogger().info("定时任务数据1：{}" , JSON.toJSONString(context.getMergedJobDataMap()));
        BaseLog.getLogger().info("定时任务数据2：{}" , JSON.toJSONString(context.getJobDetail().getJobDataMap()));
        return new SuccessResult<>(DateFormat.formatDatetime());
    }
}
