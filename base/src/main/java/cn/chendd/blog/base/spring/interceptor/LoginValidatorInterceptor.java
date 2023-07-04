package cn.chendd.blog.base.spring.interceptor;

import cn.chendd.blog.base.spring.component.InterceptorConfiguration;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.utils.Ajax;
import cn.chendd.core.utils.Http;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断是否登录的拦截器
 *
 * @author chendd
 * @date 2021/2/14 19:30
 */
@Slf4j
public class LoginValidatorInterceptor extends HandlerInterceptorAdapter {


    private InterceptorConfiguration config;

    public LoginValidatorInterceptor() {
        super();
    }

    public LoginValidatorInterceptor(InterceptorConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("请求地址 {}" , request.getRequestURI());
        Object object = request.getSession().getAttribute(Constant.SYSTEM_CURRENT_USER);
        if (object == null) {
            boolean isAjax = Ajax.isAJAXRequest(request);
            String basePath = Http.getBasePath(request);
            if (isAjax) {
                Http.responseText(response , JSONObject.toJSONString(new ErrorResult<String>("用户未登录或已失效")));
            } else {
                //未登录页面
                response.sendRedirect(basePath + "/blog/frame/login.html");
            }
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
