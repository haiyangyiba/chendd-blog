package cn.chendd.third.login.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.exceptions.ValidateDataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方登录 - Sina微博登录
 *
 * @author chendd
 * @date 2019/12/21 21:55
 */
@Api(tags = "Weichat账号登录")
@ApiSort(10)
@RequestMapping(value = "/third-login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LoginWeichatController extends BaseController {

    @PostMapping("/weichat")
    @ApiOperation(value = "Weichat账号登录",notes = "第三方登录 - Weichat账号登录")
    @ApiOperationSupport(order = 10)
    public void login() throws ValidateDataException {
        System.out.println("第三方登录之Weichat账号登录...");
    }

}
