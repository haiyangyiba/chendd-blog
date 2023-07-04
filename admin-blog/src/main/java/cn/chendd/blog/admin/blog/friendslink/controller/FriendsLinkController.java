package cn.chendd.blog.admin.blog.friendslink.controller;

import cn.chendd.blog.admin.blog.friendslink.model.FriendsLink;
import cn.chendd.blog.admin.blog.friendslink.po.FriendsLinkParam;
import cn.chendd.blog.admin.blog.friendslink.service.FriendsLinkService;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情链接Controller
 *
 * @author chendd
 * @date 2020/6/27 21:48
 */
@Api(value = "友情链接管理" , tags = "友情链接管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(10)
@RequestMapping(value = "/blog/friendslink")
@Controller
public class FriendsLinkController extends BaseController {

    @Resource
    FriendsLinkService friendsLinkService;

    @GetMapping
    @ApiOperation(value = "友链管理主页面",notes = "跳转至友链管理主页面")
    @ApiOperationSupport(order = 10)
    public String friendsLinkManage() {

        return "/blog/friendslink/friendsLinkManage";
    }

    @PostMapping
    @ApiOperation(value = "友链列表查询",notes = "友链列表查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<FriendsLinkDto> friendsLinkManage(@ApiParam("站点标题") String title) {

        List<FriendsLinkDto> dataList = friendsLinkService.queryFriendsLink(title);
        return dataList;
    }

    @GetMapping("/friendsLink")
    @ApiOperation(value = "友链管理新增修改页面",notes = "跳转至友链管理新增修改页面")
    @ApiOperationSupport(order = 30)
    public String friendsLink() {
        super.addAttribute("status" , EnumStatus.values());
        return "/blog/friendslink/friendsLink";
    }

    @PutMapping
    @ApiOperation(value = "保存友链",notes = "友链新增与修改")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public BaseResult saveFriendsLink(@RequestBody FriendsLinkParam friendsLink) {
        this.validatorData(friendsLink);
        friendsLinkService.saveFriendsLink(friendsLink);
        return new SuccessResult<String>("友情链接保存成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "友链管理修改页面",notes = "跳转至友链管理修改页面")
    @ApiOperationSupport(order = 50)
    public String friendsLink(@PathVariable @ApiParam("id") Long id) {
        FriendsLink friendsLink = friendsLinkService.getById(id);
        friendsLink.setRemark(StringUtils.replace(friendsLink.getRemark() , Constant.HTML_ENTER_CHAR , Constant.JAVA_ENTER_CHAR));
        super.addAttribute("status" , EnumStatus.values()).addAttribute("friendsLink" , friendsLink);
        return "/blog/friendslink/friendsLink";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "友链删除",notes = "友链删除")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public BaseResult saveFriendsLink(@PathVariable @ApiParam("主键ID") Long id) {
        friendsLinkService.removeFriendsLink(id);
        return new SuccessResult<String>("友情链接删除成功！");
    }

    /**
     * 验证参数
     * @param friendsLink 参数
     */
    private void validatorData(FriendsLinkParam friendsLink) {
        String title = friendsLink.getTitle();
        if(StringUtils.isBlank(title)) {
            throw new ValidateDataException("站点标题不能为空！");
        }
        String link = friendsLink.getLink();
        if(StringUtils.isBlank(link)) {
            throw new ValidateDataException("站点地址不能为空！");
        }
        String domainTag = friendsLink.getDomainTag();
        if(StringUtils.isBlank(domainTag)) {
            throw new ValidateDataException("站点标识不能为空！");
        }
        String sortOrder = friendsLink.getSortOrder();
        if(StringUtils.isBlank(sortOrder)) {
            throw new ValidateDataException("站点排序不能为空！");
        }
        EnumStatus status = friendsLink.getStatus();
        if(status == null) {
            throw new ValidateDataException("请选择站点状态！");
        }
        String remark = friendsLink.getRemark();
        if(StringUtils.isBlank(remark)) {
            throw new ValidateDataException("站点描述不能为空！");
        } else {
            friendsLink.setRemark(remark.replace(Constant.JAVA_ENTER_CHAR , Constant.HTML_ENTER_CHAR));
        }
    }

}
