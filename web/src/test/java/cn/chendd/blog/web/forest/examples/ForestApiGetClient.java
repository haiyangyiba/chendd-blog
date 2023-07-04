package cn.chendd.blog.web.forest.examples;

import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * Forest get请求测试
 *
 * @author chendd
 * @date 2020/9/26 13:23
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface ForestApiGetClient {

    /**
     * @return 无参get请求，返回String类型结果
     */
    @Get(url = "/examples/forest/get/no-param/string")
    BaseResult<String> string();

    /**
     * get请求测试，单个参数，使用${索引号}的方式传递参数
     * @return 无参get请求，返回Entity对象类型
     */
    @Get(url = "/examples/forest/get/no-param/entity")
    BaseResult<Point> entity();

    /**
     * @return 无参get请求，返回List类型结构数据
     */
    @Get(url = "/examples/forest/get/no-param/list")
    BaseResult<List<Point>> list();

    /**
     * @return 含参数get请求，参数采用索引占位符
     */
    @Get(url = "/examples/forest/get/param/sayHello?name=${0}&age=${1}")
    BaseResult<String> sayHelloIndexParam(String name , Integer age);

    /**
     * @return 含参数get请求，路径变量参数，采用索引占位符
     */
    @Get(url = "/examples/forest/get/param/variable/${0}/${1}")
    BaseResult<String> sayHelloVariableParam(String name , Integer age);

    /**
     * @return 含参数get请求，参数采用变量名称替换
     */
    @Get(url = "/examples/forest/get/param/entity?x=${x}&y=${y}")
    BaseResult<Point> sayHelloNameParam(@DataVariable("x") Integer x , @DataVariable("y") Integer y);

    /**
     * @return 含get参数请求，自动传递参数（URL中优雅的传递参数）
     */
    @Get(url = "/examples/forest/get/param/entity")
    BaseResult<Point> autoQueryParam(@Query("x") Integer x , @Query("y") Integer y);

    /**
     * @return 含参数get请求，参数采用对象传递
     */
    @Get(url = "/examples/forest/get/param/entity")
    BaseResult<Point> autoEntityQueryParam(@Query Point point);

    @Get(url = "/examples/forest/get/param/entity")
    BaseResult<Point> mapQueryEntity(@Query Map<String , Integer> params);

    @Get(url = "/examples/forest/get/param/entity" , data = {
         "x=${x}" , "y=${1}"
    })
    BaseResult<Point> dataMapQueryEntity(@Query("x") Integer x , @Query("y") Integer y);

}
