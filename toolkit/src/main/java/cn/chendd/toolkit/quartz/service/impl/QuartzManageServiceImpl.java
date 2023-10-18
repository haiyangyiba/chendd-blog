package cn.chendd.toolkit.quartz.service.impl;

import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import cn.chendd.toolkit.quartz.enums.EnumQuartzOperation;
import cn.chendd.toolkit.quartz.enums.EnumTriggerState;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import cn.chendd.toolkit.quartz.mapper.QuartzManageMapper;
import cn.chendd.toolkit.quartz.po.QuartzManageParam;
import cn.chendd.toolkit.quartz.po.QuartzParam;
import cn.chendd.toolkit.quartz.service.QuartzManageService;
import cn.chendd.toolkit.quartz.vo.QuartzManageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务管理接口实现
 *
 * @author chendd
 * @date 2020/7/4 23:27
 */
@Service
public class QuartzManageServiceImpl implements QuartzManageService {

    /**
     * quartz组件Scheduler对象，非spring内置定时任务
     */
    @Resource(name="Scheduler")
    private Scheduler scheduler;

    @Resource
    private QuartzManageMapper quartzManageMapper;

    @Override
    @Log(description = "定时任务管理-列表查询")
    public BaseResult queryQuartzList(QuartzManageParam param) {
        List<QuartzManageResult> dataList = quartzManageMapper.queryQuartzList(param);
        return new SuccessResult<>(dataList);
    }

    @Override
    @Log(description = "定时任务管理-新增或修改" , scope = LogScopeEnum.ALL)
    public BaseResult saveQuartz(QuartzParam param) throws SchedulerException {
        String jobClassName = param.getJobClassName();
        Object jobInstance;
        try {
            jobInstance = Class.forName(jobClassName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ValidateDataException(String.format("反射任务实现类 %s 出现错误，参考：%s！" , jobClassName , e.getMessage()));
        }
        if(! (jobInstance instanceof AbstractQuartzJob)) {
            throw new ValidateDataException(String.format("系统约定定时任务必须继承 %s 类！" , AbstractQuartzJob.class.getName()));
        }
        String cronExpression = param.getCronExpression();
        boolean valid = CronExpression.isValidExpression(cronExpression);
        if(! valid) {
            throw new ValidateDataException(String.format("表达式 %s 不合法！" , cronExpression));
        }

        AbstractQuartzJob jobClass = (AbstractQuartzJob) jobInstance;
        String name = param.getJobName();
        String group = param.getJobGroup();
        JobKey jobKey = JobKey.jobKey(name, group);
        CronTriggerImpl trigger = (CronTriggerImpl) TriggerBuilder.newTrigger().withIdentity(name, group)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).withDescription(param.getTriggerDescription()).build();
        if(scheduler.checkExists(jobKey)) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (Trigger.TriggerState.BLOCKED.equals(triggerState)) {
                throw new ValidateDataException(String.format("定时任务处于 %s 不允许修改！" , triggerState.name()));
            }
            /**
             * 发现使用 rescheduleJob 重置定时任务时，存在 JobDataMap 和 Description 不生效，故而使用先删除再增加的方式
             * scheduler.rescheduleJob(TriggerKey.triggerKey(name , group) , trigger);
             * 需要注意的是，使用 `deleteJob` 和 `scheduleJob`
             * 方法进行任务更新存在一个缺点：在删除任务之后，在新任务添加到调度程序之前，调度程序可能会出现空闲状态，因此在这段时间内可能会丢失一些任务。
             * 因此，如果可能的话，建议使用 `rescheduleJob` 方法进行任务更新，因为它可以确保任何被删除的任务都会在新任务添加到调度程序之前进行重新安排。
             */
            scheduler.deleteJob(jobKey);
            JobDetail jobDetail = JobBuilder.newJob(jobClass.getClass()).withIdentity(name, group).withDescription(param.getJobDescription())
                    .usingJobData(this.getJobDataMap(param.getJobData())).build();
            scheduler.scheduleJob(jobDetail , trigger);
            return new SuccessResult<>("定时任务修改成功！");
        } else {
            JobDetail jobDetail = JobBuilder.newJob(jobClass.getClass()).withIdentity(name, group).withDescription(param.getJobDescription())
                    .usingJobData(this.getJobDataMap(param.getJobData())).build();
            scheduler.scheduleJob(jobDetail , trigger);
            trigger.getTimeZone().setID("");
            return new SuccessResult<>("定时任务新增成功！");
        }
    }

    @Override
    public BaseResult getQuartz(String jobGroup, String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if(! scheduler.checkExists(jobKey)) {
            return new ErrorResult<>("当前选择任务不存在！");
        }
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        CronTrigger jobTrigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
        QuartzParam param = new QuartzParam();
        param.setJobName(jobName);
        param.setJobGroup(jobGroup);
        param.setCronExpression(jobTrigger.getCronExpression());
        param.setJobClassName(jobDetail.getJobClass().getName());
        param.setJobDescription(jobDetail.getDescription());
        param.setTriggerDescription(jobTrigger.getDescription());
        param.setJobData(JSON.toJSONString(jobDetail.getJobDataMap() , true));
        return new SuccessResult<>(param);
    }

    @Override
    @Log(description = "定时任务管理-删除" , scope = LogScopeEnum.ALL)
    public BaseResult removeQuartz(String jobGroup, String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if(scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            return new SuccessResult<>("定时任务删除成功！");
        }
        return new ErrorResult<>("当前选择任务不存在！");
    }

    @Override
    @Log(description = "'定时任务管理-' + #operation.text" , scope = LogScopeEnum.ALL)
    public BaseResult operationQuartz(EnumQuartzOperation operation, String jobGroup, String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if(scheduler.checkExists(jobKey)) {
            Trigger.TriggerState state = scheduler.getTriggerState(TriggerKey.triggerKey(jobName, jobGroup));
            EnumTriggerState[] states = operation.getTriggerStates();
            boolean flag = false;
            for (EnumTriggerState triggerState : states) {
                if(StringUtils.equalsIgnoreCase(triggerState.name() , state.name())) {
                    flag = true;
                    break;
                }
            }
            if(! flag) {
                return new ErrorResult<>(String.format("所选任务状态 %s 不允许被 %s ！" , state.name() , operation.getText()));
            }
            operation.execute(scheduler , jobKey);
            return new SuccessResult<>(String.format("定时任务 [%s] 成功！" , operation.getText()));
        }
        return new ErrorResult<>("当前选择任务不存在！");
    }

    /**
     * 根据jobData参数构造对应的参数对象
     * @param jobData jobData
     * @return jobDataMap
     */
    private JobDataMap getJobDataMap(String jobData) {
        JobDataMap jobDataMap = new JobDataMap();
        if (StringUtils.isBlank(jobData)) {
            return jobDataMap;
        }
        JSONValidator.Type type = JSONValidator.from(jobData).getType();
        if (! JSONValidator.Type.Object.equals(type)) {
            throw new ValidateDataException("任务参数json应为`{}`对象格式！");
        }
        JSONObject json = JSON.parseObject(jobData);
        Set<Map.Entry<String, Object>> entrySet = json.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            jobDataMap.put(entry.getKey() , entry.getValue());
        }
        return jobDataMap;
    }
}
