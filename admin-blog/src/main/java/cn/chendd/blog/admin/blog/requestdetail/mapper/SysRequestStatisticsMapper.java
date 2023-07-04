package cn.chendd.blog.admin.blog.requestdetail.mapper;

import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestStatistics;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDateDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestDetailResult;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestMaxDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 按天统计数量汇总
 *
 * @author chendd
 * @date 2020/7/23 19:03
 */
public interface SysRequestStatisticsMapper extends BaseMapper<SysRequestStatistics> {

    /**
     * 查询访问量统计汇总
     * @param page 分页对象
     * @param param 查询条件
     * @return 分页数据
     */
    IPage<SysRequestDetailResult> queryAccessManagePage(Page page, @Param("date") SysRequestDetailParam param);

    /**
     * 查询指定日期的访问量统计汇总
     * @param page 分页对象
     * @param param 查询条件
     * @return 分页数据
     */
    IPage<SysRequestDetail> queryAccessRequestManagePage(Page page, @Param("param") SysRequestDateDetailParam param);

    /**
     * 查询系统单日最大访问量
     * @return 单日最大访问量
     */
    SysRequestMaxDay queryRequestAccessMaxDay();

    /**
     * 查询系统总访问量
     * @return 系统总访问量
     */
    Integer queryRequestAccessTotal();
}
