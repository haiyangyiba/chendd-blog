package cn.chendd.blog.base.servlet;

import ch.qos.logback.core.util.CloseUtil;
import org.apache.commons.codec.CharEncoding;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 互联网的图片加载，由于在https的站点无法直接加载http的图片资源
 * 比如在本站https://www.chendd.cn/index.html页面无法引用http://www.baidu.com/logo.png，因为https协议默认会将发出去的http请求改成https，
 *     也就是说实际代码是http，发送出去的请求却成了https，需要对方的服务器对于https提供支持，否则404
 *
 * @author chendd
 * @date 2023/1/30 18:36
 */
public class InternetLogoReaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imagePath = request.getParameter("url");
        this.writeInternetImage(imagePath , response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req , resp);
    }

    /**
     * 输出网络上的图片文件，若出现问题，输出本地图片
     */
    private void writeInternetImage(String imagePath , OutputStream outputStream) {
        try {
            URL url = new URL(imagePath);
            try (InputStream inputStream = url.openStream()) {
                StreamUtils.copy(inputStream , outputStream);
            }
            return ;
        } catch (Exception ignore) {ignore.printStackTrace();}
        InputStream inputStream = null;
        String localFilePath = this.getClass().getClassLoader().getResource("").getPath() + "/statics/images/logo.jpg";
        try {
            localFilePath = URLDecoder.decode(localFilePath, CharEncoding.UTF_8);
            inputStream = new FileInputStream(localFilePath);
            StreamUtils.copy(inputStream , outputStream);
        } catch (Exception ignore) {}
        finally {
            CloseUtil.closeQuietly(inputStream);
        }
    }

}
