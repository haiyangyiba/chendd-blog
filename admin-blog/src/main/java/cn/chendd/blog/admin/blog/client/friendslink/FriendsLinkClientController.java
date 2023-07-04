package cn.chendd.blog.admin.blog.client.friendslink;

import cn.chendd.blog.admin.blog.friendslink.service.FriendsLinkService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情链接接口
 *
 * @author chendd
 * @date 2021/4/29 17:29
 */
@Api(value = "友情链接管理V1" , tags = "友情链接管理V1" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(10)
@RequestMapping(value = "/blog/friendslink")
@RestController
@ApiVersion("1")
public class FriendsLinkClientController extends BaseController {

    @Resource
    private FriendsLinkService friendsLinkService;

    @GetMapping("/newest")
    @ApiOperation(value = "查询前N条友链数据",notes = "查询排序最靠前N条评论数据")
    @ApiOperationSupport(order = 10)
    public List<FriendsLinkNewestDto> queryFriendsLinkNewest() {

        List<FriendsLinkNewestDto> dataList = friendsLinkService.queryFriendsLinkNewest();
        return dataList;
    }

    @GetMapping
    @ApiOperation(value = "查询全部友链数据",notes = "查询全部友情连接数据，包含启用与禁用的数据")
    @ApiOperationSupport(order = 20)
    public List<FriendsLinkDto> queryFriendsLink() {

        return friendsLinkService.queryFriendsLink();
    }

}
