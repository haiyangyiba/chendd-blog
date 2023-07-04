package cn.chendd.blog.admin.system.jobs;

import cn.chendd.blog.admin.system.info.service.SystemInfoService;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import org.quartz.JobExecutionContext;

/**
 * 系统信息定时任务获取
 *
 * @author chendd
 * @date 2020/5/30 22:18
 */
public class SystemInfoScheduledTask extends AbstractQuartzJob {

    /**
     * 每个 N 分钟更新一次
     */
    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        SystemInfoService systemInfoService = SpringBeanFactory.getBean(SystemInfoService.class);
        systemInfoService.setSystemInfoResult();
        return new SuccessResult<>("定时更新系统信息任务执行成功！");
    }
}
