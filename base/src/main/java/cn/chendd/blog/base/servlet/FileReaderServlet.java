package cn.chendd.blog.base.servlet;

import cn.chendd.blog.base.spring.component.SystemParamComponent;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.FileWriter;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 文件读取Servlet
 *
 * @author chendd
 * @date 2020/8/4 20:50
 */
public class FileReaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String path = StringUtils.substring(uri , "/file".length());
        SystemParamComponent systemParam = SpringBeanFactory.getBean(SystemParamComponent.class);
        File file = new File(systemParam.getRootPath() + path);
        FileWriter fileWriter = SpringBeanFactory.getBean(FileWriter.class);
        fileWriter.writeFile(file , response , this.getDefaultImage());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req , resp);
    }

    /**
     * 获取系统内默认的图片不存在时默认的图片
     */
    private File getDefaultImage() {
        String localFilePath = this.getClass().getClassLoader().getResource("").getPath() + "/statics/images/not_found.jpg";
        try {
            localFilePath = URLDecoder.decode(localFilePath, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {}
        return new File(localFilePath);
    }

}
