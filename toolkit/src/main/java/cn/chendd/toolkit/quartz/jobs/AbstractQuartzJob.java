package cn.chendd.toolkit.quartz.jobs;

import cn.chendd.core.enums.EnumResult;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.quartz.model.QuartzJobExecute;
import cn.chendd.toolkit.quartz.service.QuartzJobExecuteService;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

/**
 * baseJob
 *
 * @author chendd
 * @date 2020/7/4 0:38
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    protected long beginTime;
    protected JobKey jobKey;
    protected BaseResult baseResult;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        before();
        try {
            this.jobKey = context.getJobDetail().getKey();
            log.info("开始执行定时任务类：{}" , ClassUtils.getName(this.getClass()));
            baseResult = doExecute(context);
        } catch (Exception e) {
            baseResult = new ErrorResult<>(ExceptionUtils.getStackTrace(e));
            throw e;
        } finally {
            log.info("结束执行定时任务类：{}" , ClassUtils.getName(this.getClass()));
            after();
        }
    }

    protected abstract BaseResult doExecute(JobExecutionContext context);

    protected void before() {
        beginTime = System.currentTimeMillis();
    }

    protected void after() {
        long endTime = System.currentTimeMillis();
        log.info("定时任务 {} 执行耗时 {} ms!" , this.getClass() , endTime - beginTime);
        //构造日志明细对象记录入库
        QuartzJobExecuteService service = SpringBeanFactory.getBean(QuartzJobExecuteService.class);
        QuartzJobExecute param = new QuartzJobExecute();
        param.setJobGroup(jobKey.getGroup());
        param.setJobName(jobKey.getName());
        //开始时间
        param.setBeginTime(DateFormat.formatDatetime(new Date(beginTime)));
        //结束时间
        param.setEndTime(DateFormat.formatDatetime(new Date(endTime)));
        param.setResult(baseResult.getResult());
        if(StringUtils.equalsIgnoreCase(baseResult.getResult() , EnumResult.success.name())) {
            Object value = baseResult.getData();
            if(value instanceof String) {
                //执行成功时提示信息
                param.setRemark(baseResult.getData() != null ? baseResult.getData().toString() : "");
            } else {
                //执行成功时提示信息
                param.setRemark(JSON.toJSONString(baseResult.getData()));
            }
        } else {
            //执行失败时包含错误信息堆栈
            param.setRemark(baseResult.getMessage());
        }
        service.saveQuartzJobExecute(param);
    }

}
