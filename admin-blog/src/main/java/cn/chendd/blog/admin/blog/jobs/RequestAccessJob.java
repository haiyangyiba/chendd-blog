package cn.chendd.blog.admin.blog.jobs;

import cn.chendd.blog.base.filters.RequestDetailFilter;
import cn.chendd.core.result.BaseResult;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 资源访问定时任务
 *
 * @author chendd
 * @date 2022/9/11 17:15
 */
public class RequestAccessJob extends AbstractQuartzJob {

    /**
     * 不处理请求日志
     */
    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        RequestDetailFilter.REQUEST_ITEM_QUEUE.clear();
        return null;
    }

}
