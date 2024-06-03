package cn.chendd.toolkit.operationlog.service;

import cn.chendd.core.result.BaseResult;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.po.SysOperationLogParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 功能操作日志接口定义
 *
 * @author chendd
 * @date 2019/9/20 16:35
 */
public interface SysOperationLogService extends IService<SysOperationLog> {

    /**
     * 保存操作日志
     * @param id 主键ID
     * @param beginTime 开始时间
     * @param moduleId 模块ID
     * @param ip ip地址
     */
    void storageBeforeOperationLog(Long id , Long beginTime , String moduleId , String ip);

    /**
     * 更新操作日志
     * @param entity 操作日志结果
     */
    void saveAfterOperationLog(SysOperationLog entity);

    /**
     * 操作日志查询分页
     * @param param 查询条件
     * @return 日志分页
     */
    BaseResult querySysOperationPage(SysOperationLogParam param);

    /**
     * 根据操作日志ID 查询内容详细
     *
     * @param date 查询日期
     * @param id 根据操作日志ID
     * @return 内容详细
     */
    String querySysOperationContent(String date, Long id);
}
