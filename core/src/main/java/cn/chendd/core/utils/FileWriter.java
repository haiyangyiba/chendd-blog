package cn.chendd.core.utils;

import cn.chendd.core.enums.EnumImageType;
import org.apache.commons.codec.CharEncoding;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 读取系统文件工具类
 *
 * @author chendd
 * @date 2020/6/20 19:14
 */
@Component
@Scope("prototype")
public class FileWriter {

    /**
     * 写出文件至浏览器
     * @param file 文件
     * @param response 响应
     * @param defaultImageFile 当前为图片时，图片不存在时默认输出
     * @throws IOException IO异常
     */
    public void writeFile(File file , HttpServletResponse response , File defaultImageFile) throws IOException {
        OutputStream os = response.getOutputStream();
        boolean image = EnumImageType.isImage(file.getName());
        //如果当前是图片，且图片不存在时，输出默认的文件（图片）地址
        if(image && ! file.exists() && defaultImageFile != null) {
            this.writeFile(defaultImageFile , os);
            return;
        } else if(! image) {
            //如果当前不是图片，则输出附件相关的信息，下载文件的中文名称及文件大小
            try {
                String fileName = file.getName();

                //文件名称不包括中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, CharEncoding.UTF_8));
                response.setHeader("Content-Length", String.valueOf(file.length()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }


        this.writeFile(file , os);

    }

    public void writeFile(File file , HttpServletResponse response) throws IOException {
        this.writeFile(file , response);
    }

    /**
     * 写出文件至响应流
     * @param file 文件
     * @param os 输出流
     */
    public void writeFile(File file, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte bytes[] = new byte[1024];
            int len = -1;
            while((len = fis.read(bytes)) != -1){
                os.write(bytes, 0, len);
            }
        } finally {
            Close.close(os , fis);
        }
    }

}
