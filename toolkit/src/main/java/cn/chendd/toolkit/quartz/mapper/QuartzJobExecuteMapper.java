package cn.chendd.toolkit.quartz.mapper;

import cn.chendd.toolkit.quartz.model.QuartzJobExecute;
import cn.chendd.toolkit.quartz.po.QuartzJobExecuteParam;
import cn.chendd.toolkit.quartz.vo.QuartzJobExecuteResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务执行明细Mapper
 *
 * @author chendd
 * @date 2020/7/9 21:10
 */
public interface QuartzJobExecuteMapper extends BaseMapper<QuartzJobExecute> {


    Page<QuartzJobExecuteResult> queryQuartzJobExecutePage(Page page, @Param("param") QuartzJobExecuteParam param);

    String getResultById(Long id);
}
