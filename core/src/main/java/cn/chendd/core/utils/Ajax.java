package cn.chendd.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Ajax工具类
 *
 * @author chendd
 * @date 2019/12/28 22:02
 */
public class Ajax {

    /**
     * 判定一个请求是否为ajax模式
     *
     * @param request
     *            request对象
     * @return true ? ajax : !ajax
     */
    public static boolean isAJAXRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null
                && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

}
