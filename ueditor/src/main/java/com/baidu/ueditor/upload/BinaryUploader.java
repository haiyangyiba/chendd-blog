package com.baidu.ueditor.upload;

import cn.chendd.core.enums.EnumImageType;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.spring.service.watermark.ImageWatermarkService;
import cn.chendd.core.utils.Http;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

    public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile((String) conf.get("fieldName"));
        if (multipartFile == null) {
            return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
        }

        String savePath = (String) conf.get("savePath");
        String originFileName = multipartFile.getOriginalFilename();
        //获取文件后缀
        String suffix = "." + FilenameUtils.getExtension(originFileName);
        //获取文件名称，不含后缀名
        String fileName = FilenameUtils.getBaseName(originFileName);
        savePath = savePath + suffix;

        long maxSize = (Long) conf.get("maxSize");

        if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
            return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
        }

        savePath = PathFormat.parse(savePath, originFileName);

        String physicalPath = (String) conf.get("rootPath") + savePath;
        try (InputStream is = multipartFile.getInputStream()) {
            State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                //增加title显示原始文件名称，否则默认无此属性则显示为日期数字的文件名称
                storageState.putInfo("title", fileName);
                storageState.putInfo("original", fileName + suffix);

                //图片上传成功，判断是否为图片，如果为图片则增加水印
                if (EnumImageType.isImage(originFileName) && StringUtils.containsNone(originFileName , "含水印")) {
                    //增加图片上传追加水印
                    ImageWatermarkService imageWatermarkService = SpringBeanFactory.getBean(ImageWatermarkService.class);
                    String text = Http.getBasePath(request);
                    if (StringUtils.containsAny(text , "localhost" , "127.0.0.1" , "8888")) {
                        text = "https://www.chendd.cn";
                    }
                    imageWatermarkService.imageWatermark4Ueditor(new File(physicalPath) , text);
                }

            }

            return storageState;
        } catch (IOException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        }
        //return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }
}
