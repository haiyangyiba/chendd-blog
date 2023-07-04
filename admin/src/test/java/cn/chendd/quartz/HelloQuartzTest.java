package cn.chendd.quartz;

import cn.chendd.blog.Bootstrap;
import cn.chendd.core.utils.DateFormat;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Hello定时任务
 *
 * @author chendd
 * @date 2020/7/3 11:57
 */
public class HelloQuartzTest extends Bootstrap {

    @Resource(name="Scheduler")
    private Scheduler scheduler;

    private final String name = "test_hello_job";
    private final String groupName = "test_job_group";

    /**
     * 增加一个定时任务
     */
    @Test
    public void testAddOrPauseOrResumeJob() throws SchedulerException {
        System.out.println("//新增时考虑同一个组、同一个名称存在不同的触发器的情况--HelloQuartzTest.testAddOrPauseOrResumeJob");
        System.out.println("开始增加任务调度");
        JobDetail jobDetail = JobBuilder.newJob(HelloQuartz.class).withIdentity(name, groupName).withDescription("测试hello定时任务").build();
        String cronExpression = "0/5 * * * * ?";
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).withDescription("每5秒执行一次").build();
        boolean exist = scheduler.checkExists(JobKey.jobKey(name , groupName));
        System.out.println(scheduler.isStarted() + "---started");
        if(! exist) {
            scheduler.scheduleJob(jobDetail , trigger);
        }
        System.out.println("已经增加任务调度");

        for(int i=1 ; i <= 50 ; i++) {
            try {
                Thread.sleep(1000L);
                System.out.println(i + "---休眠---" + DateFormat.formatDatetime());
                if(i == 10) {
                    this.testPauseJob();
                }
                if(i == 20) {
                    this.testResumeJob();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testAddTwoJob() throws SchedulerException {
        System.out.println("新增两个定时任务，name与group一致，但corn不一致");
        System.out.println("开始增加任务调度");
        String newName = "more2_" + name , newGroupName = "more_" + groupName;
        JobDetail jobDetail = JobBuilder.newJob(HelloQuartz.class).withIdentity(newName, newGroupName).withDescription("test_job").build();
        CronTrigger trigger8_20 = TriggerBuilder.newTrigger().withIdentity(newName, newGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 8-20 * * ?")).withDescription("早上8点到晚上20点，每10分钟执行一次").build();
        scheduler.scheduleJob(jobDetail , trigger8_20);
        System.out.println("已经增加任务调度");
    }

    /**
     * 暂停一个定时任务
     */
    private void testPauseJob() throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, groupName);
        if(scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            System.out.println("暂停定时任务");
        }

    }

    /**
     * 继续一个定时任务
     */
    private void testResumeJob() throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, groupName);
        if(scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
            System.out.println("重新开始定时任务");
        }

    }

    /**
     * 删除一个定时任务
     */
    @Test
    public void testRemoveJob() throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, groupName);
        if(scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            System.out.println("删除定时任务");
        } else {
            System.out.println("定时任务不存在");
        }
    }

    @Test
    public void testUpdateJob() throws SchedulerException {
        String cronExpression = "0/10 * * * * ?";
        JobKey jobKey = JobKey.jobKey(name, groupName);
        if(scheduler.checkExists(jobKey)) {
            //修改一个任务
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
            scheduler.rescheduleJob(TriggerKey.triggerKey(name , groupName) , trigger);
            System.out.println("修改定时任务");
        }
    }

    @Test
    public void testQueryJob() throws SchedulerException {
        List<String> groupNames = scheduler.getJobGroupNames();
        System.out.println(groupNames);
        for (String group : groupNames) {
            System.out.println(scheduler.getListenerManager());
        }
    }

    /**
     * 立即执行定时任务
     */
    @Test
    public void testRunJob() throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(name , groupName));
    }

    /**
     * 查询所有定时任务
     */
    @Test
    public void testAllJob() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
        System.out.println(jobKeys);
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> jobs = scheduler.getTriggersOfJob(jobKey);
            for (Trigger job : jobs) {
                Trigger.TriggerState state = scheduler.getTriggerState(TriggerKey.triggerKey(job.getJobKey().getName(), job.getJobKey().getGroup()));
                System.out.println(state.name() + "--" + state.ordinal() + "--" + JSON.toJSONString(job));
            }
        }
    }

    /**
     * 获取所有正在运行的定时任务
     */
    @Test
    public void testAllJobByRun() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        System.out.println(executingJobs);
    }

    /**
     * 从数据库查询定时任务数据
     */
    @Test
    public void testAllJobByDb() {

    }

}
