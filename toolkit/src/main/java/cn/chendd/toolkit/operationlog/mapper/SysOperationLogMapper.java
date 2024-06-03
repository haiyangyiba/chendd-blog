package cn.chendd.toolkit.operationlog.mapper;

import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import cn.chendd.toolkit.operationlog.po.SysOperationLogParam;
import cn.chendd.toolkit.operationlog.vo.SysOperationLogResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author chendd
 * @date 2019/9/20 16:40
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    /**
     * 查询操作日志分页列表
     *
     * @param page
     * @param param 查询条件
     * @return 日志分页
     */
    Page<SysOperationLogResult> querySysOperationPage(IPage page, @Param("param") SysOperationLogParam param);

    /**
     * 查询操作日志内容信息
     * @param param 查询参数
     * @return 日志内容信息
     */
    String querySysOperationContent(@Param("param") SysOperationLogParam param);

    /**
     * 根据ID查询数据对象
     * @param param 查询参数
     * @return 日志内容信息
     */
    SysOperationLog querySysOperationById(@Param("param") SysOperationLogParam param);

    /**
     * 更新操作日志具体执行结果
     * @param entity 操作日志
     */
    void saveAfterOperationLog(SysOperationLog entity);
}
