package cn.chendd.blog.admin.api;


import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api演示异常抛出示例
 *
 * @author chendd
 * @date 2019/9/22 22:25
 */
@RestController
@RequestMapping("/api/rest")
@Api(value = "测试 Api 响应异常链结果" , tags = {"响应异常类封装"})
public class ApiExceptionController {

    @GetMapping("/runtime")
    @ApiOperation(value = "测试运行时异常" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public String runtime(){
        int value = 1/0;
        return null;
    }

    @GetMapping("/classCast")
    @ApiOperation(value = "测试类型转换异常" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public Integer classCast(){
        String url = "https://www.chendd.cn";
        return Integer.valueOf(url);
    }

    @GetMapping("/throwException")
    @ApiOperation(value = "抛出定义异常" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public Integer throwException() throws ValidateDataException {
        throw new ValidateDataException("用户名或密码不匹配");
    }

    @GetMapping("/noException")
    @ApiOperation(value = "非Rest请求时没有异常时")
    @ResponseBody
    public String noException() throws ValidateDataException {
        return "ApiExceptionController.noException";
    }

}
