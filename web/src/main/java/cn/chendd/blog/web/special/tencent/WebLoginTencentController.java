package cn.chendd.blog.web.special.tencent;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.web.home.controller.WebThirdLoginController;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 特殊处理腾讯QQ第三方登录
 * 说明：由于后端服务存在端口，QQ互联管理平台的“网站回调域”参数无法配置端口号，故而跳转到web端，由web端再将请求转发至admin服务端
 * @author chendd
 * @date 2022/3/12 23:21
 */
@Controller
@RequestMapping("/tencent-login")
@Slf4j
public class WebLoginTencentController extends BaseController {

    @Resource
    private WebThirdLoginController webThirdLoginController;

    @GetMapping("/tencent")
    @ApiOperation(value = "Tencent账号登录",notes = "第三方登录 - Tencent账号登录")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public String tencent(
            @ApiParam(value = "朋友站点标识") @RequestParam(required = false) String friend) throws QQConnectException {
        String redrectUrl = new Oauth().getAuthorizeURL(request);
        return redrectUrl;
    }

    @RequestMapping(value = {"/tencentCallback.a" , "/tencentCallback"})
    @ApiOperation(value = "Tencent账号登录回调",notes = "第三方登录 - Tencent账号登录回调")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public void tencentCallback() throws Exception {
        AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
        if (accessTokenObj.getAccessToken().equals("")) {
            //我们的网站CS被RF攻击了或者用户取消了授权
            //做一些数据统计工作
            throw new Exception("没有获取到响应参数");
        }
        String accessToken = accessTokenObj.getAccessToken();
        OpenID openIDObj =  new OpenID(accessToken);
        String openID = openIDObj.getUserOpenID();
        UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
        UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
        if (userInfoBean.getRet() == 0) {
            String nickName = userInfoBean.getNickname();
            String userImage = userInfoBean.getAvatar().getAvatarURL100();
            //处理用户登录后续
            ThirdUserResult userResult = new ThirdUserResult(openID , nickName , userImage , EnumUserSource.Tencent_qq , null);
            webThirdLoginController.handleLogin(request , response , session , JSON.toJSONString(userResult));
            //腾讯QQ赞不提供友链支持
        } else {
            throw new Exception(String.format("未能授权登录成功：[%d-%s]" , userInfoBean.getRet() , userInfoBean.getMsg()));
        }
    }

}
