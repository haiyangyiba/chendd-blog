package cn.chendd.core.spring.service.watermark;

import cn.chendd.core.enums.EnumImageType;
import cn.chendd.core.utils.GifImageUtil;
import cn.chendd.core.utils.ImageUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;

/**
 * 图片水印
 *
 * @author chendd
 * @date 2020/8/16 20:24
 */
@Service
public class ImageWatermarkService {

    @Async
    public void imageWatermark4ArticleCover(File imageFile , String text ) {

        final String imageFileName = imageFile.getName();
        Font font = new Font("宋体", Font.PLAIN, 22);
        if (StringUtils.endsWithIgnoreCase(imageFileName , EnumImageType.gif.getSuffix())) {
            GifImageUtil.watermarkGif(imageFile , imageFile , font , Color.BLACK , text , ImageUtil.TextPositions.CENTER , 0.3f);
        } else {
            ImageUtil.watermark(imageFile , imageFile , font , Color.BLACK , text , ImageUtil.TextPositions.CENTER , 0.3f);
        }
        //将不同的图片格式统一转换为jpg格式，出现了一些问题：
        // 1、简单测试发现格式转换后图片明显变大；
        // 2、处理png透明图片格式时，发现将图片透明背景转换为黑色了
        /// ImageUtil.outputFormatType(imageFile);
        // 删除图片对应同名文件（一个文章的封面图片只有一个，可能由用户多次上传导致存在多个文件，只是后缀不同）
        final String imageFileBaseName = FilenameUtils.getBaseName(imageFileName);
        File[] files = imageFile.getParentFile().listFiles((file, fileName) -> StringUtils.startsWithIgnoreCase(fileName, imageFileBaseName));
        assert files != null;
        for (File file : files) {
            if (! StringUtils.equalsIgnoreCase(imageFileName , file.getName())) {
                file.delete();
            }
        }
    }

    /**
     * @Async(value = "simplePoolExecutor")
     * ueditor上传图片时，不使用异步处理
     */
    public void imageWatermark4Ueditor(File imageFile , String text) {
        final String imageFileName = imageFile.getName();
        Font font = new Font("宋体", Font.PLAIN, 22);
        if (StringUtils.endsWithIgnoreCase(imageFileName , EnumImageType.gif.getSuffix())) {
            GifImageUtil.watermarkGif(imageFile , imageFile , font , Color.BLACK , text , ImageUtil.TextPositions.CENTER , 0.3f);
        } else {
            ImageUtil.watermark(imageFile , imageFile , font , Color.BLACK , text , ImageUtil.TextPositions.CENTER , 0.3f);
        }

    }

}
