package cn.chendd.toolkit.quartz.service;

import cn.chendd.core.result.BaseResult;
import cn.chendd.toolkit.quartz.enums.EnumQuartzOperation;
import cn.chendd.toolkit.quartz.po.QuartzManageParam;
import cn.chendd.toolkit.quartz.po.QuartzParam;
import org.quartz.SchedulerException;

/**
 * 定时任务管理接口定义
 * @author chendd
 * @date 2020/7/4 23:25
 */
public interface QuartzManageService {

    /**
     * 查询定时任务列表数据
     * @param param 查询条件
     * @return 含列表数据
     */
    BaseResult queryQuartzList(QuartzManageParam param);

    /**
     * 保存定时任务数据
     * @param param 定时任务
     * @return 操作结果
     */
    BaseResult saveQuartz(QuartzParam param) throws SchedulerException;

    /**
     * 根据任务组名与任务名称查询定时任务
     * @param jobGroup 任务组名
     * @param jobName 任务名称
     * @return 定时任务对象
     */
    BaseResult getQuartz(String jobGroup, String jobName) throws SchedulerException;

    /**
     * 根据任务组名与任务名称删除定时任务
     * @param jobGroup 任务组名
     * @param jobName 任务名称
     * @return 操作结果
     */
    BaseResult removeQuartz(String jobGroup, String jobName) throws SchedulerException;

    /**
     * 停用、启用、立即运行等
     */
    BaseResult operationQuartz(EnumQuartzOperation operation, String jobGroup, String jobName) throws SchedulerException;
}
