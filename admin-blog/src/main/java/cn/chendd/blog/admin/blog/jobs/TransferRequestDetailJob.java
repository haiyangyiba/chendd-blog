package cn.chendd.blog.admin.blog.jobs;

import cn.chendd.blog.admin.blog.requestdetail.service.SysRequestDetailService;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * 迁移历史数据并统计清理
 *
 * @author chendd
 * @date 2020/7/21 21:38
 */
public class TransferRequestDetailJob extends AbstractQuartzJob {

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {

        SysRequestDetailService sysRequestDetailService = SpringBeanFactory.getBean(SysRequestDetailService.class);
        Date date = new Date();
        String yesterday = DateFormat.formatDate(DateUtils.addDays(date, -1));
        String beginDate = DateFormat.formatDate(DateUtils.addDays(date, -100));
        sysRequestDetailService.transferRequestDetail(beginDate , yesterday);
        return new SuccessResult<>("定时任务执行完毕");
    }

}
