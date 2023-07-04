package cn.chendd.blog.admin.blog.tag.controller;

import cn.chendd.blog.admin.blog.tag.model.Tag;
import cn.chendd.blog.admin.blog.tag.po.TagManageParam;
import cn.chendd.blog.admin.blog.tag.po.TagParam;
import cn.chendd.blog.admin.blog.tag.service.TagManageService;
import cn.chendd.blog.admin.blog.tag.vo.TagManageResult;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumWhether;
import cn.chendd.core.exceptions.ValidateDataException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标签管理Controller
 *
 * @author chendd
 * @date 2020/7/15 10:37
 */
@Api(value = "博客标签管理" , tags = "博客标签管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(11)
@RequestMapping(value = "/blog/tag")
@Controller
public class TagManageController extends BaseController {

    @Resource
    private TagManageService tagManageService;

    @GetMapping
    @ApiOperation(value = "标签管理主页面",notes = "跳转至标签管理主页面")
    @ApiOperationSupport(order = 10)
    public String tagManage() {
        super.addAttribute("whetheres" ,  EnumWhether.values());
        return "/blog/tag/tagManage";
    }

    @PostMapping
    @ApiOperation(value = "标签列表查询",notes = "标签列表查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<TagManageResult> queryTagsPage(@ApiParam("标签查询") TagManageParam param) {

        Page<TagManageResult> pageFinder = tagManageService.queryTagManagePage(param);
        return pageFinder;
    }

    @GetMapping("/tag")
    @ApiOperation(value = "标签管理主页面",notes = "跳转至标签管理主页面")
    @ApiOperationSupport(order = 30)
    public String tag() {
        super.addAttribute("whetheres" ,  EnumWhether.values());
        return "/blog/tag/tag";
    }

    @PutMapping
    @ApiOperation(value = "标签管理新增或修改",notes = "标签管理新增或修改")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public String tag(@RequestBody TagParam param) {
        this.validatorTag(param);
        tagManageService.saveTag(param);
        return "标签保存成功！";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "标签管理修改页面",notes = "跳转至标签管理修改页面")
    @ApiOperationSupport(order = 50)
    public String editor(@ApiParam("主键ID") @PathVariable Long id) {
        Tag tag = tagManageService.getById(id);
        super.addAttribute("whetheres" ,  EnumWhether.values())
             .addAttribute("tag" , tag);
        return "/blog/tag/tag";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "标签管理删除",notes = "标签管理删除")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public String tag(@ApiParam("主键ID") @PathVariable Long id) {

        tagManageService.removeTag(id);
        return "标签删除成功！";
    }

    private void validatorTag(TagParam param) {
        if(StringUtils.isBlank(param.getTag())) {
            throw new ValidateDataException("请输入标签名称！");
        }
        if(StringUtils.isBlank(param.getSortOrder())) {
            throw new ValidateDataException("请输入排序！");
        }
        if(param.getStrong() == null) {
            throw new ValidateDataException("请选择是否推荐！");
        }
    }

}
