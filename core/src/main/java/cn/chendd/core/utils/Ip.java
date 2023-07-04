package cn.chendd.core.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址获取工具类
 *
 * @author chendd
 * @date 2020/6/26 21:07
 */
public class Ip {

    private static final String HEADERS[] = {
        "x-forwarded-for" , "Proxy-Client-IP" , "X-Forwarded-For" , "WL-Proxy-Client-IP" , "X-Real-IP"
    };

    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = null;
        for (String headerName : HEADERS) {
            ip = request.getHeader(headerName);
            if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase("unknown" , ip)) {
                break;
            }
        }
        if(ip == null) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
