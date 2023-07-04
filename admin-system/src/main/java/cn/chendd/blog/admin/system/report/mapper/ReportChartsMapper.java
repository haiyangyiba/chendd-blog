package cn.chendd.blog.admin.system.report.mapper;

import cn.chendd.blog.admin.system.report.vo.NameValueResult;

import java.util.List;

/**
 * 统计图表Mapper接口定义
 * @author chendd
 * @date 2021/6/26 21:47
 */
public interface ReportChartsMapper {

    /**
     * 按用户来源查询统计Mapper方法定义
     * @return 用户来源
     */
    List<NameValueResult> queryUserSourcesCharts();

    /**
     * 按年份查询文章统计Mapper方法定义
     * @return 文章年份
     */
    List<NameValueResult> queryArticleYears();

    /**
     * 按访问梳理查询文章统计Mapper方法定义
     * @return 文章访问
     */
    List<NameValueResult> queryArticleVisits();

    /**
     * 查询文章评论数量
     * @return 数量
     */
    Integer queryArticleCommentCount();

    /**
     * 查询文章点赞数量
     * @return 数量
     */
    Integer queryArticlePraisesCount();

    /**
     * 查询推荐标签列表
     * @return 标签
     */
    List<NameValueResult> queryTagNameList();
}
