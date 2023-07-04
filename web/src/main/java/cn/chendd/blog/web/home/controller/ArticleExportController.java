package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.web.home.service.ArticleExportService;
import cn.chendd.core.utils.DownloadNameUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 文章导出Controller
 *
 * @author chendd
 * @date 2022/4/2 14:53
 */
@Api(value = "文章导出" , tags = "文章导出")
@ApiSort(60)
@Controller
@RequestMapping("/blog/export")
public class ArticleExportController extends BaseController {

    @Resource
    private ArticleExportService articleExportService;

    @Value("${server.http-port}")
    private Integer port;

    /**
     * 导出Pdf
     */
    @GetMapping("/pdf/{id}")
    @ApiOperation(value = "导出PDF" , notes = "导出文章内容数据")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public void exportPdf(@ApiParam(name = "id" , value = "文章id") @PathVariable Long id) throws IOException {
        ServletOutputStream outputStream = super.response.getOutputStream();
        ArticleSingleContentResult article = this.articleExportService.getArticleContent(id);
        try {
            String articleContent = article.getEditorContent();
            ///处理html代码中路径不为http开头，使用html2pdf组件自带的根路径处理方式解决
            /*{
                String basePath = Http.getBaseHttpPath(request);
                String filePrefix = "=\"/";
                //1.处理相关地址中的图片地址，把/开头的地址增加项目访问前缀
                articleContent = StringUtils.replace(articleContent , filePrefix , "=\"" + basePath + "/");
            }*/
            String fileName = DownloadNameUtil.browserDownloadName(article.getTitle() , request) + ".pdf";
            String attachment = ContentDisposition.builder("attachment").filename(fileName).build().toString();
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri(this.getBaseHttpPath(request));
            converterProperties.setFontProvider(new DefaultFontProvider(true, true, true));
            converterProperties.setCharset(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION , attachment);
            ///注意：此处未生成落地本地磁盘文件，故无法拿到准确的下载文件地址，除非落地本地文件，再定时清理
            ///response.setHeader(HttpHeaders.CONTENT_LENGTH , String.valueOf(articleContent.getBytes().length));
            HtmlConverter.convertToPdf(articleContent , outputStream , converterProperties);
        } catch (Exception e) {
            response.reset();
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            throw e;
        }
    }

    /**
     * 获取http协议下的路径
     * @param request request
     * @return http路径
     */
    private String getBaseHttpPath(HttpServletRequest request) {
        StringBuilder basePathBuilder = new StringBuilder();
        String portValue = "";
        if(port != 80) {
            portValue = ":" +  port;
        }
        basePathBuilder.append("https://")
                .append(request.getServerName()).append(portValue)
                .append(request.getContextPath());
        return basePathBuilder.toString();
    }

}
