package cn.chendd.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * http请求工具类
 *
 * @author chendd
 * @date 2020/7/20 21:23
 */
public class Http {

    /**
     * 获取http跟路径，可以参考org.apache.catalina.util.RequestUtil.getRequestURL方法实现
     * @param request reqest
     * @return 项目根路径
     */
    public static String getBasePath(HttpServletRequest request) {
        StringBuilder basePathBuilder = new StringBuilder();
        String portValue = "";
        int port = request.getServerPort();
        if(port != 80 && port != 443) {
            portValue = ":" +  port;
        }
        basePathBuilder.append(request.getScheme()).append("://")
                .append(request.getServerName()).append(portValue)
                .append(request.getContextPath());
        return basePathBuilder.toString();
    }

    public static String getRealPath(HttpServletRequest request) {
        return getRealPath(request, "/");
    }

    public static String getRealPath(HttpServletRequest request , String realPath) {
        return request.getServletContext().getRealPath(realPath);
    }

    /**
     * 获取html内容的Text属性，等同于前端使用的innerText属性获取的值
     * @param htmlContent html内容
     * @return 解析后的innerText
     */
    public static String getHtmlInnerText(String htmlContent){
        String content = null;
        if(StringUtils.isEmpty(htmlContent)){
            return htmlContent;
        }
        Document document = Jsoup.parseBodyFragment(htmlContent);
        String text = document.text();
        //以下替换逻辑参考与百度ueditor的getContentTxt获取纯文本
        text = text.replace("\u200B", "").replace("\ufeff", "").replace("\u00a0", "").trim();
        return text;
    }

    /**
     * 输出response流信息
     * @param response response
     * @param text 文本
     * @throws IOException 异常
     */
    public static void responseText(HttpServletResponse response , String text) throws IOException {
        response.setContentType(MediaType.TEXT_HTML + ";charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try(PrintWriter out = response.getWriter()) {
            out.print(text);
            out.flush();
        }
    }

}
