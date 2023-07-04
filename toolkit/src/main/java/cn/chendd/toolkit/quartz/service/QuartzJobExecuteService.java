package cn.chendd.toolkit.quartz.service;

import cn.chendd.core.result.BaseResult;
import cn.chendd.toolkit.quartz.model.QuartzJobExecute;
import cn.chendd.toolkit.quartz.po.QuartzJobExecuteParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chendd
 * @date 2020/7/9 21:00
 */
public interface QuartzJobExecuteService extends IService<QuartzJobExecute> {

    /**
     * 查询定时任务执行明细结果查询
     */
    BaseResult queryQuartzJobExecuteList(QuartzJobExecuteParam param);

    /**
     * 保存定时任务执行明细
     */
    BaseResult saveQuartzJobExecute(QuartzJobExecute param);

    /**
     * 根据id查询任务执行结果
     * @param id 主键ID
     */
    String getResultById(Long id);
}
