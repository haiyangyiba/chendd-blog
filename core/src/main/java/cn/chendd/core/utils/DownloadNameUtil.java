package cn.chendd.core.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件下载名称中文处理
 *
 * @author chendd
 * @date 2022/4/2 19:58
 */
public final class DownloadNameUtil {

    private DownloadNameUtil() {

    }

    /**
     * 末@Description :将字符串转换为UTF8格式*@param s
     * <p>
     * 字符串
     *
     * @return String转换后的字符串
     */
    public static String toUtf8String(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append('%').append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * <pre>
     * 设置浏览器下载文件名*< / pre>
     * @param s 文件名
     * @param request request请求@return编码后的字符串
     */
    public static String browserDownloadName(String s, HttpServletRequest request) throws IOException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return browserDownloadName(s, userAgent);
    }

    /**
     * <pre>
     * 设置浏览器下载文件名*< / pre>
     * @param s 文件名
     * @param userAgent 浏览器header中的userAgent，如果为null，则返回toUtf8String结果@return
     */
    public static String browserDownloadName(String s, String userAgent) throws IOException {
        String name;
        if (StringUtils.isEmpty(userAgent) || StringUtils.isEmpty(s)) {
            return toUtf8String(s);
        }//firefox或chrome浏览器
        if (StringUtils.indexOf(userAgent, "finefox") != -1 || StringUtils.indexOf(userAgent, "webkit") != -1) {
            name = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (StringUtils.indexOf(userAgent, "msie") != -1) {
            //ie8-ie10浏览器
            name = URLEncoder.encode(s, StandardCharsets.UTF_8.name());
        } else {
            //ie11或其他浏览器
            name = toUtf8String(s);
        }
        return name;
    }

}
