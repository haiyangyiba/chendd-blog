package cn.chendd.toolkit.quartz.mapper;

import cn.chendd.toolkit.quartz.model.QuartzJobExecute;
import cn.chendd.toolkit.quartz.po.QuartzManageParam;
import cn.chendd.toolkit.quartz.vo.QuartzManageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务管理Mapper
 *
 * @author chendd
 * @date 2020/7/4 23:10
 */
public interface QuartzManageMapper extends BaseMapper<QuartzJobExecute> {

    List<QuartzJobExecute> queryList();

    List<QuartzManageResult> queryQuartzList(@Param("param") QuartzManageParam param);

}
