package cn.chendd.blog.base.controller;

import cn.chendd.blog.base.spring.component.SystemParamComponent;
import cn.chendd.core.utils.FileWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 文件读取实现（包含图片文件等其它具体文件）
 * 不推荐使用，已经使用FileReaderServlet替换
 *
 * @author chendd
 * @date 2020/6/20 18:16
 */
@Api(value = "读取文件管理" , tags = "全局文件读取，将相对路径转换为磁盘文件读取")
@ApiSort(20)
@RequestMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//@Controller
@Deprecated
public class FileReaderController extends BaseController {

    @Resource
    private SystemParamComponent systemParam;
    @Resource
    private FileWriter fileWriter;

    @GetMapping("/file/*")
    @ApiOperation(value = "文件下载",notes = "支持图片及各种文档的读取与下载")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public void download() throws IOException {
        String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String path = StringUtils.substring(uri , "/file".length());
        File file = new File(systemParam.getRootPath() + path);
        fileWriter.writeFile(file , response , this.getDefaultImage());
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
