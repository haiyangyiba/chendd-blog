package cn.chendd.toolkit.quartz.service.impl;

import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.quartz.mapper.QuartzJobExecuteMapper;
import cn.chendd.toolkit.quartz.model.QuartzJobExecute;
import cn.chendd.toolkit.quartz.po.QuartzJobExecuteParam;
import cn.chendd.toolkit.quartz.service.QuartzJobExecuteService;
import cn.chendd.toolkit.quartz.vo.QuartzJobExecuteResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时任务执行明细接口实现
 *
 * @author chendd
 * @date 2020/7/9 21:09
 */
@Service
public class QuartzJobExecuteServiceImpl extends ServiceImpl<QuartzJobExecuteMapper , QuartzJobExecute> implements QuartzJobExecuteService {

    @Resource
    private QuartzJobExecuteMapper quartzJobExecuteMapper;

    @Override
    @Log(description = "定时任务执行历史列表查询")
    public BaseResult queryQuartzJobExecuteList(QuartzJobExecuteParam param) {
        Page page = new Page(param.getPageNumber() , param.getPageSize());
        Page<QuartzJobExecuteResult> pageFinder = quartzJobExecuteMapper.queryQuartzJobExecutePage(page , param);
        return new SuccessResult<>(pageFinder);
    }

    @Override
    public BaseResult saveQuartzJobExecute(QuartzJobExecute param) {
        super.save(param);
        return new SuccessResult<>("定时任务执行记录保存成功！");
    }

    @Override
    @Log(description = "查看任务执行历史错误明细")
    public String getResultById(Long id) {
        return quartzJobExecuteMapper.getResultById(id);
    }
}
