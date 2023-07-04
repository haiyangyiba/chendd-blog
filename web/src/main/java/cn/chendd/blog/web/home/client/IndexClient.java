package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.blog.web.home.client.vo.ArticleContentResult;
import cn.chendd.blog.web.home.client.vo.SysInfoIntroduce;
import cn.chendd.blog.web.home.client.vo.SysInfoProject;
import cn.chendd.blog.web.home.client.vo.TagManageResult;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * 菜单数据功能client
 *
 * @author chendd
 * @date 2020/10/10 22:14
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface IndexClient {

    /**
     * 获取菜单数据
     * @return 获取菜单数据
     */
    @Get(url = "/v1/system/menu.html" , dataType = "text")
    Object queryMenusText();

    /**
     * 获取菜单数据
     * @return 获取菜单数据
     */
    @Get(url = "/v1/system/menu.html")
    BaseResult<List<Treeview>> queryMenus();

    /**
     * 获取首页介绍信息
     * @return 获取首页介绍信息
     */
    @Get(url = "/v1/system/infocontent/introduce.html")
    BaseResult<List<SysInfoIntroduce>> queryInfoIntroduce();

    /**
     * 获取首页介绍信息
     * @return 获取首页介绍信息
     */
    @Get(url = "/v1/system/infocontent/project.html")
    BaseResult<List<SysInfoProject>> queryInfoProject();

    /**
     * 查询首页推荐标签
     * @return 查询首页推荐标签
     */
    @Get(url = "/v1/blog/tag.html")
    BaseResult<List<TagManageResult>> queryTags();

    /**
     * 查询博客最新N页数据
     * @param pageNumber 页码
     * @return 查询博客最新N页数据
     */
    @Get(url = "/v1/blog/article/page/${pageNumber}.html")
    BaseResult<List<ArticleContentResult>> queryArticleContents(@DataVariable("pageNumber") Integer pageNumber);

    /**
     * 查询最新注册用户数据
     * @return 用户数据
     */
    @Get(url = "/v1/system/user/newest.html")
    BaseResult<List<UserNewestDto>> queryUserNewestList();

    /**
     * 查询最新评论数据
     * @return 评论数据
     */
    @Get(url = "/v1/system/comment/newest.html")
    BaseResult<List<CommentNewestDto>> queryCommentNewestList();

    /**
     * 查询友情链接数据
     * @return 友情链接数据
     */
    @Get(url = "/v1/blog/friendslink/newest.html")
    BaseResult<List<FriendsLinkNewestDto>> queryFriendsLinkNewestList();
}
