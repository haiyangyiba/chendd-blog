package cn.chendd.blog.web.forest.examples;

import cn.chendd.blog.web.BaseBootstrapTest;
import cn.chendd.core.result.BaseResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * ForestApiFileUploadOrDownloadClient
 *
 * @author chendd
 * @date 2020/9/30 21:16
 */
@Slf4j
public class ForestApiFileUploadOrDownloadTest extends BaseBootstrapTest {

    @Resource
    private ForestApiFileUploadOrDownloadClient client;

    protected List<byte[]> uploadFiles = Lists.newArrayList();
    protected List<String> fileNames = Lists.newArrayList();

    /*@Before
    public void init() throws IOException {

        String filePaths[] = {"d:\\数字.pptx" , "d:\\数字.pdf"};
        for (String filePath : filePaths) {
            byte[] bytes;
            File file = new File(filePath);
            try (InputStream is = new FileInputStream(file)){
                bytes = InputStreamUtils.getBytes(is);
            }
            uploadFiles.add(bytes);
            fileNames.add(file.getName());
        }
    }*/
    @Test
    public void testUpload() throws IOException {
        log.debug("======单个文件上传======");
        BaseResult result = this.client.uploaFile("d:\\数字.pdf" , "chendd" , 86);
        log.debug("testUpload result --> {}" , JSON.toJSONString(result));
    }

    /*@Test
    public void testUploadBasicParam() throws IOException {

        BaseResult result = this.client.uploadBasicParam(this.uploadFiles , this.fileNames , "chendd" , 86);
    }*/

}
