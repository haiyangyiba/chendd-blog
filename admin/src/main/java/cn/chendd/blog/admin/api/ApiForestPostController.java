package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.api.assist.EnumResponse;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.awt.*;

/**
 * 提供Post请求的数据支持
 *
 * @author chendd
 * @date 2020/10/5 21:46
 */
@RestController
@RequestMapping(value = "/examples/forest/post" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Forest Api Post 数据提供" , tags = {"Forest Api Post 数据提供"})
@ApiSort(20)
public class ApiForestPostController extends BaseController {

    @PostMapping("/no-param/entity")
    @ApiOperation(value = "无参 返回Entity" , notes = "Forest Api Post请求构造接口返回数据")
    @ApiOperationSupport(order = 10 , author = "chendd")
    public Point entity(){
        return (Point) EnumResponse.Entity.getResult();
    }

    @PostMapping("/param/more")
    @ApiOperation(value = "多个参数 返回String" , notes = "Forest Api Post请求构造接口返回数据")
    @ApiOperationSupport(order = 20 , author = "chendd")
    public String more(@RequestParam @ApiParam("名称") String name ,
                           @RequestParam @ApiParam("年龄") Integer age){
        return String.format("your name is %s , age is %d" , name , age);
    }

    @PostMapping("/param/entity")
    @ApiOperation(value = "对象参数传递 返回对象" , notes = "Forest Api Post请求构造接口返回数据")
    @ApiOperationSupport(order = 30 , author = "chendd")
    public Param entity(@ApiParam("坐标对象") Param param){
        return param;
    }

    @PostMapping(value = "/param/body" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "对象JSON参数传递 返回对象" , notes = "Forest Api Post请求构造接口返回数据")
    @ApiOperationSupport(order = 40 , author = "chendd")
    public Param body(@RequestBody @ApiParam("坐标对象json") Param param){
        return param;
    }

}
