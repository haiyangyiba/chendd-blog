package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.api.assist.EnumResponse;
import cn.chendd.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api演示响应结果测试类
 *
 * @author chendd
 * @date 2019/9/22 18:35
 */
@RestController
@RequestMapping("/api")
@Api(value = "测试 原始 响应结果集接口" , tags = {"原始响应结果"})
public class ApiResultController {

    @GetMapping("/result")
    @ApiOperation(value = "测试响应结果集",notes = "测试响应结果集（<b>无论任何类型结果集，均以 BaseResult 类型包裹</b>）",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public Object result(EnumResponse response){

        return response.getResult();
    }

    @GetMapping("/string")
    @ApiOperation(value = "测试响应 String 结果集" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public String string(){

        return (String) EnumResponse.String.getResult();
    }

}
