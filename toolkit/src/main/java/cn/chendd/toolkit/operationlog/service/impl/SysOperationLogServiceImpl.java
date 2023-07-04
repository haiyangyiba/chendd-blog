package cn.chendd.toolkit.operationlog.service.impl;

import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.operationlog.mapper.SysOperationLogMapper;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.po.SysOperationLogParam;
import cn.chendd.toolkit.operationlog.service.SysOperationLogService;
import cn.chendd.toolkit.operationlog.vo.SysOperationLogResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能操作日志接口实现
 *
 * @author chendd
 * @date 2019/9/20 16:36
 */
@Service
@Slf4j
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {

    @Resource
    private SysOperationLogMapper operationLogMapper;

    @Override
    @Async
    public void storageBeforeOperationLog(Long id , String beginTime , String ip) {
        SysOperationLog entity = new SysOperationLog(id , ip , beginTime);
        entity.setTableDate(beginTime);
        this.operationLogMapper.insert(entity);
    }

    @Override
    @Async
    public void saveAfterOperationLog(SysOperationLog entity) {
        operationLogMapper.saveAfterOperationLog(entity);
    }

    @Override
    public BaseResult querySysOperationPage(SysOperationLogParam param) {
        IPage page = new Page(param.getPageNumber() , param.getPageSize());
        Page<SysOperationLogResult> pageFinder = operationLogMapper.querySysOperationPage(page , param);
        return new SuccessResult<>(pageFinder);
    }

    @Override
    public String querySysOperationContent(String date, Long id) {
        SysOperationLogParam param = new SysOperationLogParam();
        param.setDate(date);
        param.setId(id);
        return operationLogMapper.querySysOperationContent(param);
    }

}
