package cn.chendd.toolkit.operationlog.service.impl;

import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.enums.ResultEnum;
import cn.chendd.toolkit.operationlog.mapper.SysOperationLogMapper;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.po.SysOperationLogParam;
import cn.chendd.toolkit.operationlog.service.SysOperationLogService;
import cn.chendd.toolkit.operationlog.vo.SysOperationLogResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public void storageBeforeOperationLog(Long id , Long beginTime , String moduleId , String ip) {
        final String datetime = DateFormat.formatDatetime(beginTime);
        SysOperationLog entity = new SysOperationLog(id , ip , datetime);
        entity.setTableDate(datetime);
        entity.setResultEnum(ResultEnum.running);
        //设置保存参数
        JSONObject userInfo = UserContext.getCurrentUser();
        if(userInfo != null) {
            JSONObject account = userInfo.getJSONObject("account");
            JSONObject user = userInfo.getJSONObject("user");
            entity.setUserId(user.getLong("userId")).setUserName(account.getString("username"));
        }
        entity.setModuleId(moduleId);
        this.operationLogMapper.insert(entity);
    }

    @Override
    @Async
    public void saveAfterOperationLog(SysOperationLog entity) {
        SysOperationLogParam param = new SysOperationLogParam();
        param.setId(entity.getId());
        param.setDate(StringUtils.substringBefore(entity.getEndTime() , StringUtils.SPACE));
        final SysOperationLog sysOperationLog = this.operationLogMapper.querySysOperationById(param);
        sysOperationLog.setContent(entity.getContent());
        sysOperationLog.setDescription(entity.getDescription());
        sysOperationLog.setEndTime(entity.getEndTime());
        sysOperationLog.setRunTime(entity.getRunTime());
        sysOperationLog.setResultEnum(entity.getResultEnum());
        sysOperationLog.setTableDate(entity.getEndTime());
        operationLogMapper.saveAfterOperationLog(sysOperationLog);
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
