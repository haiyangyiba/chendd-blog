package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.blog.web.home.client.HomepageChartClient;
import cn.chendd.blog.web.home.client.IndexClient;
import cn.chendd.blog.web.home.client.SitemapClient;
import cn.chendd.blog.web.home.client.vo.*;
import cn.chendd.blog.web.home.service.IndexService;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.utils.RandomUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页Service接口实现
 *
 * @author chendd
 * @date 2020/10/10 22:16
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexClient indexClient;
    @Resource
    private HomepageChartClient chartClient;
    @Resource
    private SitemapClient sitemapClient;

    @Override
    public List<Treeview> queryMenus() {
        BaseResult<List<Treeview>> result = this.indexClient.queryMenus();
        return result.getData();
    }

    @Override
    public List<SysInfoIntroduce> queryInfoIntroduce() {
        BaseResult<List<SysInfoIntroduce>> result = this.indexClient.queryInfoIntroduce();
        return result.getData();
    }

    @Override
    public List<SysInfoProject> queryInfoProject() {
        BaseResult<List<SysInfoProject>> result = this.indexClient.queryInfoProject();
        return result.getData();
    }

    @Override
    public List<TagManageResult> queryTags() {
        BaseResult<List<TagManageResult>> baseResult = this.indexClient.queryTags();
        return baseResult.getData();
    }

    @Override
    public List<ArticleContentResult> queryArticleContents(Integer pageNumber) {
        BaseResult<List<ArticleContentResult>> baseResult = this.indexClient.queryArticleContents(pageNumber);
        return baseResult.getData();
    }

    @Override
    public List<UserNewestDto> queryUserNewestList() {
        BaseResult<List<UserNewestDto>> baseResult = this.indexClient.queryUserNewestList();
        return baseResult.getData();
    }

    @Override
    public List<CommentNewestDto> queryCommentNewestList() {
        BaseResult<List<CommentNewestDto>> baseResult = this.indexClient.queryCommentNewestList();
        return baseResult.getData();
    }

    @Override
    public List<FriendsLinkNewestDto> queryFriendsLinkNewestList() {
        BaseResult<List<FriendsLinkNewestDto>> baseResult = this.indexClient.queryFriendsLinkNewestList();
        return baseResult.getData();
    }

    @Override
    public ReportChartHomepage queryHomepageCharts() {
        BaseResult<ReportChartHomepage> baseResult = chartClient.queryHomepageCharts();
        return baseResult.getData();
    }

    @Override
    public String getSitemap() {
        BaseResult<String> baseResult = this.sitemapClient.getSitemap();
        return baseResult.getData();
    }
}
