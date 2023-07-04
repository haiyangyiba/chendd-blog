package cn.chendd.toolkit.quartz.enums;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * 定义不同状态对应的操作实现
 *
 * @author chendd
 * @date 2020/7/9 20:10
 */
public interface IEnumQuartzOperation {

    /**
     * 定义定时任务不同状态的具体操作
     */
    void execute(Scheduler scheduler , JobKey jobKey) throws SchedulerException;

}
