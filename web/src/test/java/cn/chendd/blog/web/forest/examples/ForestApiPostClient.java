package cn.chendd.blog.web.forest.examples;

import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Post;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.tags.Param;

/**
 * ForestApiPostClient
 *
 * @author chendd
 * @date 2020/9/30 21:05
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface ForestApiPostClient {

    /**
     * @return 无参post请求，返回Entity对象类型结果
     */
    @Post(url = "/examples/forest/post/no-param/entity")
    BaseResult noParam();

    /**
     * @return 多个参数post请求，返回String类型结果
     */
    @Post(url = "/examples/forest/post/param/more" , data = {
         "name=${name}" , "age=${age}"
        }
    )
    BaseResult more(@DataVariable("name") String name , @DataVariable("age") Integer age);

    /**
     * @return 使用多个参数传递为对象类型，返回原对象类型结果
     */
    @Post(url = "/examples/forest/post/param/entity" , data = {
            "name=${name}" , "value=${value}"
        }
    )
    BaseResult entity(@DataVariable("name") String name , @DataVariable("value") String value);

    /**
     * @return 直接传递对象类型，返回原对象类型结果
     */
    @Post(url = "/examples/forest/post/param/body" , contentType = MediaType.APPLICATION_JSON_VALUE)
    BaseResult body(@Body Param param);
}
