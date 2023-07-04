package cn.chendd.toolkit.quartz.enums;

import lombok.Getter;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * 定时任务功能操作
 *
 * @author chendd
 * @date 2020/7/5 0:18
 */
@Getter
public enum EnumQuartzOperation implements IEnumQuartzOperation {

    /**
     * 暂停定时任务
     */
    paused("停用" , new EnumTriggerState[]{EnumTriggerState.NORMAL , EnumTriggerState.WAITING , EnumTriggerState.ACQUIRED , EnumTriggerState.COMPLETE}){
        @Override
        public void execute(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
            scheduler.pauseJob(jobKey);
        }
    },
    /**
     * 从暂停中恢复
     */
    resume("启用" , new EnumTriggerState[]{EnumTriggerState.PAUSED}){
        @Override
        public void execute(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
            scheduler.resumeJob(jobKey);
        }
    },
    /**
     * 立即执行任务
     */
    run("运行" , new EnumTriggerState[]{EnumTriggerState.NORMAL , EnumTriggerState.WAITING , EnumTriggerState.ACQUIRED , EnumTriggerState.COMPLETE}){
        @Override
        public void execute(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
            scheduler.triggerJob(jobKey);
        }
    }
    ;


    private String text;
    private EnumTriggerState[] triggerStates;

    EnumQuartzOperation(String text , EnumTriggerState[] triggerStates) {
        this.text = text;
        this.triggerStates = triggerStates;
    }

}
