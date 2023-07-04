package cn.chendd.blog.base.controller;

import cn.chendd.core.common.constant.Constant;
import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Base Controller
 *
 * @author chendd
 * @date 2019/9/18 17:05
 */
public abstract class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected Model model;

    @ModelAttribute
    public void initServletContext(Model model) {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        this.request = attributes.getRequest();
        this.response = attributes.getResponse();
        this.session = request.getSession();
        this.model = model;
    }

    /**
     * 从session中获取当前登录用户信息
     * @return 用户信息
     */
    @SuppressWarnings("unchecked")
    protected <T> T getCurrentUser(Class<T> beanClass){
        JSON json = (JSON) this.session.getAttribute(Constant.SYSTEM_CURRENT_USER);
        if (json == null){
            return null;
        }
        return json.toJavaObject(beanClass);
    }

    /**
     * 从session中判断当前用户是否已登录
     * @return true ? 登录 : 未登录
     */
    protected Boolean getCurrentUser(){
        Object user = (JSON) this.session.getAttribute(Constant.SYSTEM_CURRENT_USER);
        if (user == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    protected Model addAttribute(String name , Object value) {
        this.model.addAttribute(name , value);
        return this.model;
    }

}
