package cn.chendd.blog.web.forest.examples;

import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataFile;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Post;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * ForestApiFileUploadOrDownloadClient
 *
 * @author chendd
 * @date 2020/9/30 21:15
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 ,
        contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface ForestApiFileUploadOrDownloadClient {

    /**
     * 基本参数，多文件上传
     */
    @Post(url = "/examples/forest/upload/multipart-file?name=${1}&age=${2}" , /*data = {
            "name=${name}" , "value=${value}"
        },*/ contentType = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    BaseResult uploaFile(@DataFile(value = "files" , fileName = "${1}") String filePath , String name , Integer age);

    /**
     * 基本参数，多文件上传
     */
    @Post(url = "/examples/forest/upload/multipart-basic" , data = {
            "name=${name}" , "value=${value}"
        }, contentType = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    BaseResult uploadBasicParam(@DataFile(value = "files" , fileName = "${1}") byte[] files ,
                                String fileNames ,
                                @DataVariable("name") String name , @DataVariable("age") Integer age);

}
