package cn.chendd.blog.admin.blog.client.tag;

import cn.chendd.blog.admin.blog.tag.service.TagManageService;
import cn.chendd.blog.admin.blog.tag.vo.TagManageResult;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.article.tag.TagArticleDto;
import com.google.common.base.Charsets;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 博客标签Controller
 *
 * @author chendd
 * @date 2020/11/14 11:13
 */
@Api(value = "博客标签管理V1" , tags = "博客标签管理V1" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(11)
@RequestMapping(value = "/blog/tag")
@RestController
@ApiVersion("1")
public class TagClientController extends BaseController {

    @Resource
    private TagManageService tagManageService;

    @GetMapping
    @ApiOperation(value = "推荐标签列表查询",notes = "查询系统推荐标签列表")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public List<TagManageResult> queryStrongTagsList() {
        List<TagManageResult> dataList = this.tagManageService.queryStrongTagsList();
        return dataList;
    }

    @GetMapping("/{tag}")
    @ApiOperation(value = "推荐标签名称查询",notes = "查询指定标签名称列表")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<TagArticleDto> queryStrongTagsListByName(@ApiParam("标签名称") @PathVariable String tag) throws UnsupportedEncodingException {
        tag = URLDecoder.decode(tag , Charsets.UTF_8.name());
        tag = URLDecoder.decode(tag , Charsets.UTF_8.name());
        List<TagArticleDto> dataList = this.tagManageService.queryStrongTagsListByName(tag);
        return dataList;
    }

}
