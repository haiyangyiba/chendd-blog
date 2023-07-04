package cn.chendd.third.login.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.Close;
import cn.chendd.third.login.config.ThirdLoginConfiguration;
import cn.chendd.third.login.custom.alipay.AlipayConfig;
import cn.chendd.third.login.custom.github.GithubConfig;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 第三方登录的父类
 *
 * @author chendd
 * @date 2019/12/29 18:27
 */
@Slf4j
public class ThirdLoginController extends BaseController {

    @Resource
    private ThirdLoginConfiguration thirdLoginConfiguration;

    protected static final String CALLBACK_NOTES = "第三方登录 - 返回登录请求地址<br/>" +
            "<strong style='bold;color:red;'>" +
            "测试方案：<br/>" +
            "1、请求本接口访问登录地址；<br/>" +
            "2、将返回地址拷贝至浏览器地址栏，进行用户授权，授权成功后调用回调函数；<br/>" +
            "3、回调函数地址需要修改为可以可以访问的地址；<br/>" +
            "</strong>";

    /**
     * link参数为友链接入后的登录跳转
     */
    protected String getLink(String friend) {
        String link = "";
        if(StringUtils.isNotBlank(friend)){
            link = this.getFriends().get(friend);
            if(StringUtils.isEmpty(link)){
                throw new ValidateDataException(String.format("不能识别 %s 参数" , friend));
            }
            try {
                link = URLEncoder.encode(link , Charsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                log.error("url参数 {} 编码错误" , link , e);
            }
        }
        return link;
    }

    protected Map<String , String> getFriends(){
        return this.thirdLoginConfiguration.getFriends();
    }

    protected Map<String , String> getWeibo4jConfig(){
        return this.thirdLoginConfiguration.getWeibo4jConfig();
    }

    protected Map<String , String> getBaiduConfig(){
        return this.thirdLoginConfiguration.getBaiduConfig();
    }

    protected Map<String , String> getGiteeConfig(){
        return this.thirdLoginConfiguration.getGiteeConfig();
    }

    protected GithubConfig getGithubConfig() {
        return this.thirdLoginConfiguration.getGithubConfig();
    }

    protected AlipayConfig getAlipayConfig() {
        return this.thirdLoginConfiguration.getAlipayConfig();
    }

    protected <T> BaseResult consumesResponse(CloseableHttpClient httpClient , HttpGet httpGet , Class<T> clazz){
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            return new SuccessResult<>(JSONObject.parseObject(result , clazz));
        } catch (Exception e) {
            log.error("根据token获取用户信息异常：" , e);
            return new ErrorResult(String.format("根据token获取用户信息异常，参考：%s" , e.getMessage()));
        } finally {
            Close.close(response , httpClient);
        }
    }

    /**
     * 检查是否重定向
     * @param redirect 重定向url
     * @param result 登录结果集
     * @throws IOException 异常处理
     */
    protected void checkRedirect(String redirect , ThirdUserResult result) throws IOException {
        if (StringUtils.isNotEmpty(redirect)) {
            redirect = URLDecoder.decode(redirect , Charsets.UTF_8.name());
            String userResult = URLEncoder.encode(JSONObject.toJSONString(result) , Charsets.UTF_8.name());
            if (StringUtils.contains(redirect , "?")) {
                response.sendRedirect(redirect + "&userResult=" + userResult);
            } else {
                response.sendRedirect(redirect + "?userResult=" + userResult);
            }
        }
    }

}
