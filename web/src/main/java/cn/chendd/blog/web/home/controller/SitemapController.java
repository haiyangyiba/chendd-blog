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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * BaseController
 *
 * @author chendd
 * @date 2023/10/25 21:18
 */
@Api(value = "网站地图" , tags = "网站地图")
@ApiSort(10)
@Controller
public class SitemapController {

    @Resource
    private IndexService indexService;

    @GetMapping(value = {"/Sitemap"})
    @ResponseBody
    public void sitemap(HttpServletResponse response) throws IOException {
        final String sitemap = this.indexService.getSitemap();
        response.getWriter().println(sitemap);
    }

}
