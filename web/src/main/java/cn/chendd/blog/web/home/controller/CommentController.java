package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.web.captcha.po.CaptchaParam;
import cn.chendd.blog.web.captcha.service.CaptchaService;
import cn.chendd.blog.web.home.po.CommentPutParam;
import cn.chendd.blog.web.home.service.CommentService;
import cn.chendd.blog.web.logger.WebLogger;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.utils.Html;
import cn.chendd.core.utils.Ip;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论管理Controller
 *
 * @author chendd
 * @date 2021/2/22 20:45
 */
@Api(value = "评论管理" , tags = "评论管理")
@ApiSort(10)
@Controller
@RequestMapping("/blog/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;
    @Resource
    private CaptchaService captchaService;

    @GetMapping("/{targetId}/{pageNumber}")
    @ApiOperation(value = "数据查询" , notes = "按数据位置查询评论数据列表")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult<Page<CommentQueryResult>> queryComment(@ApiParam("数据对象ID") @PathVariable Long targetId ,
                                                                  @ApiParam("分页编号") @PathVariable Long pageNumber) {
        return this.commentService.queryComment(targetId , pageNumber);
    }

    @PutMapping
    @ApiOperation(value = "数据保存" , notes = "保存评论数据，返回保存后的主键ID")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public Long saveComment(@ApiParam("评论对象") @RequestBody CommentPutParam param ,
                            @ApiParam("验证码ticket") String ticket ,
                            @ApiParam("验证码randstr") String randstr) {
        CaptchaParam captchaParam = new CaptchaParam();
        captchaParam.setTicket(ticket);
        captchaParam.setRandstr(randstr);
        String ipAddress = Ip.getIpAddress(request);
        captchaParam.setUserIp(ipAddress);
        WebLogger.getLogger().info("验证码参数：{}" , captchaParam);
        final String validate = (String) session.getAttribute(Constant.LOGIN_VALIDATE_KEY);
        if (validate == null) {
            throw new ValidateDataException("验证码校验失败：请刷新获取新验证码！");
        }
        session.removeAttribute(Constant.LOGIN_VALIDATE_KEY);
        if (!StringUtils.equalsIgnoreCase(validate , ticket)) {
            throw new ValidateDataException(String.format("验证码校验失败：应为 [%s]，实为 [%s] ！" , validate , ticket));
        }
        String editorContent = param.getEditorContent();
        String[] codes = {
             "<a" , "<img" , "<image" , "<style" , "<script" , "<link"
        };
        if (StringUtils.containsAny(StringUtils.lowerCase(editorContent) , codes)) {
            editorContent = Html.getSimpleHtml(editorContent);
            param.setEditorContent(editorContent);
        }

        SysUserResult userResult = super.getCurrentUser(SysUserResult.class);
        param.setCreateUserId(userResult.getUser().getUserId());
        param.setIp(ipAddress);
        return this.commentService.saveComment(param);
    }

}
