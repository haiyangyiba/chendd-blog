package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.blog.web.home.client.vo.*;
import cn.chendd.blog.web.home.service.IndexService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * BaseController
 *
 * @author chendd
 * @date 2020/9/4 21:12
 */
@Api(value = "主页" , tags = "系统主页")
@ApiSort(10)
@Controller
public class IndexController extends BaseController {

    @Resource
    private IndexService indexService;

    @GetMapping(value = {"/" , "index"})
    public String index() {
        //获取首页介绍信息
        List<SysInfoIntroduce> introduceList = this.indexService.queryInfoIntroduce();
        super.addAttribute("introduceList" , introduceList);
        //获取首页介绍信息
        List<SysInfoProject> projectList = this.indexService.queryInfoProject();
        super.addAttribute("projectList" , projectList);
        //系统推荐标签
        List<TagManageResult> tagList = this.indexService.queryTags();
        super.addAttribute("tagList" , tagList);
        //默认查询第一页博客数据
        List<ArticleContentResult> articleList = this.indexService.queryArticleContents(1);
        super.addAttribute("articleList" , articleList);
        //查询最新用户
        List<UserNewestDto> userNewestList = this.indexService.queryUserNewestList();
        super.addAttribute("userNewsList" , userNewestList);
        //查询最新评论
        List<CommentNewestDto> commentNewestList = this.indexService.queryCommentNewestList();
        super.addAttribute("commentNewestList" , commentNewestList);
        //查询友情链接
        List<FriendsLinkNewestDto> friendsLinkNewestList = this.indexService.queryFriendsLinkNewestList();
        super.addAttribute("friendsLinkNewestList" , friendsLinkNewestList);
        //查询统计图表
        ReportChartHomepage reportChartHomepage = this.indexService.queryHomepageCharts();
        super.addAttribute("reportChartHomepage" , JSON.toJSONString(reportChartHomepage , SerializerFeature.UseSingleQuotes));

        return "/index";
    }

}
