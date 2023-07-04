package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.blog.web.home.client.vo.*;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.result.BaseResult;

import java.util.List;

/**
 * 首页Service接口定义
 *
 * @author chendd
 * @date 2020/10/10 22:15
 */
public interface IndexService {

    /**
     * 查询首页菜单
     * @return 菜单列表
     */
    List<Treeview> queryMenus();

    /**
     * 查询首页介绍信息
     * @return 介绍信息列表
     */
    List<SysInfoIntroduce> queryInfoIntroduce();

    /**
     * 查询首页介绍信息
     * @return 介绍信息列表
     */
    List<SysInfoProject> queryInfoProject();

    /**
     * 查询首页推荐标签
     * @return 查询首推荐标签
     */
    List<TagManageResult> queryTags();

    /**
     * 查询博客最新N页数据
     * @param pageNumber 页码
     * @return 查询博客最新的第N页数据
     */
    List<ArticleContentResult> queryArticleContents(Integer pageNumber);

    /**
     * 查询最新用户
     * @return 用户信息列表
     */
    List<UserNewestDto> queryUserNewestList();

    /**
     * 查询最新评论
     * @return 评论信息列表
     */
    List<CommentNewestDto> queryCommentNewestList();

    /**
     * 查询友情链接
     * @return 友情链接列表
     */
    List<FriendsLinkNewestDto> queryFriendsLinkNewestList();

    /**
     * 查询首页统计图表
     * @return 统计图表
     */
    ReportChartHomepage queryHomepageCharts();

}
