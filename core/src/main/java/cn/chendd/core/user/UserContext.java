package cn.chendd.core.user;

import cn.chendd.core.common.constant.Constant;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户上下文
 *
 * @author chendd
 * @date 2020/6/26 15:57
 */
public class UserContext {

    public static ServletRequestAttributes getServletRequestAttribute() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if(attributes == null) {
            return null;
        }
        return attributes;
    }

    /**
     * 从session中获取当前登录用户信息
     * @return 用户信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T getCurrentUser(){
        ServletRequestAttributes attributes = getServletRequestAttribute();
        if(attributes == null) {
            return null;
        }
        return (T) attributes.getRequest().getSession().getAttribute(Constant.SYSTEM_CURRENT_USER);
    }

    /**
     * 判断用户是否登录，特地传递session因为全局的session与某个过滤器先后顺序的session不为同一个
     * @param session session
     * @return true ？ 登录 ：未登录
     */
    public static Boolean isLogin(HttpSession session){
        if(session == null) {
            return false;
        }
        return session.getAttribute(Constant.SYSTEM_CURRENT_USER) != null;
    }

    /**
     * 从session中获取当前登录用户信息
     * @param beanClass 转换为用户对象
     * @param <T> 泛型
     * @return 用户信息
     */
    public static <T> T getCurrentUser(Class<T> beanClass){
        JSONObject userInfo = UserContext.getCurrentUser();
        if(userInfo != null) {
            return userInfo.toJavaObject(beanClass);
        }
        return null;
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = getServletRequestAttribute();
        if(attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

}
