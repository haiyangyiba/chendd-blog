package cn.chendd.blog.base.spring.component;

import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.utils.Base64Image;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * <pre>
 * 结合系统环境应用：包装 base64与图片互转工具类
 *
 * </pre>
 * @author chendd
 * @date 2020/6/20 17:08
 */
@Component
@Slf4j
@Data
public class Base64AndImageComponent {

    @Resource
    private SystemParamComponent systemParam;

    /**
     * @param base64Text base64图片文本
     * @param destFile 输出文件路径，不包含根路径，包含文件名
     * @return 存储为实际图片，并返回相应路径
     */
    public String convert2File(String base64Text , String destFile){
        try {
            String filePath = new File(systemParam.getRootPath() , destFile).getAbsolutePath();
            String file = Base64Image.convert2File(base64Text, filePath);
            return destFile;
        } catch (IOException e) {
            log.error("头像转换出现错误" , e);
            return Constant.DEFAULT_USER_IAMGE;
        }
    }

}
