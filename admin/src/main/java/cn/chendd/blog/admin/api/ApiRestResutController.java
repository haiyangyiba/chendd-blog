package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.api.assist.EnumResponse;
import cn.chendd.blog.admin.system.sysrole.model.SysRole;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
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
@RequestMapping("/api/rest")
@Api(value = "测试 restApi 响应结果集接口" , tags = {"响应结果封装"})
public class ApiRestResutController extends BaseController {

    @GetMapping("/result")
    @ApiOperation(value = "测试响应结果集",notes = "测试响应结果集（<b>无论任何类型结果集，均以 BaseResult 类型包裹</b>）" +
            "<p style=\"color='red'\">必须规则：约定类继承了BaseController，表示为自定义模块</p>" +
            "<ul>" +
            "<ol>可选规则：类被标记有@RestController</ol>" +
            "<ol>可选规则：方法被标记有@Response</ol>" +
            "</ul>" ,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            response = BaseResult.class
    )
    public Object result(EnumResponse response){
        Object result = response.getResult();
        //返回值为Object类型的方法，不能返回为空
        if(result == null){
            return new SuccessResult<>(null);
        }
        return result;
    }

    @GetMapping("/string")
    @ApiOperation(value = "测试响应 String 结果集"/* ,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            response = BaseResult.class*/
    )
    public String string(){

        return (String) EnumResponse.String.getResult();
    }

    @GetMapping(value = "/vo-null" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "测试响应 vo-null 结果集")
    public SysRole voNull(){
        return null;
    }

    @GetMapping(value = "/null-object" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "测试响应 object-null 结果集")
    public Object objectNull(){
        return null;
    }

}
