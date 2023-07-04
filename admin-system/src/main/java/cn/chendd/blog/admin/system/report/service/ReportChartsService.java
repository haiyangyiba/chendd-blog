package cn.chendd.blog.admin.system.report.service;

import cn.chendd.blog.admin.system.report.vo.HomepageChartsResult;

/**
 * 统计图表Service接口定义
 * @author chendd
 * @date 2021/6/26 21:34
 */
public interface ReportChartsService {

    /**
     * web首页图表查询
     * @return 集合数据
     */
    HomepageChartsResult queryHomepageCharts();


}
