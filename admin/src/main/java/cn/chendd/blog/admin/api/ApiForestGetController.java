package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.api.assist.EnumResponse;
import cn.chendd.blog.base.controller.BaseController;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;


/**
 * 提供Get请求的数据支持
 *
 * @author chendd
 * @date 2020/9/26 22:08
 */
@RestController
@RequestMapping(value = "/examples/forest/get" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Forest Api Get 数据提供" , tags = {"Forest Api Get 数据提供"})
@ApiSort(10)
public class ApiForestGetController extends BaseController {

    @GetMapping("/no-param/string")
    @ApiOperation(value = "无参 返回String" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 10 , author = "chendd")
    public String string(){
        return (String) EnumResponse.String.getResult();
    }

    @GetMapping("/no-param/entity")
    @ApiOperation(value = "无参 返回Entity" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 20 , author = "chendd")
    public Point entity(){
        return (Point) EnumResponse.Entity.getResult();
    }

    @GetMapping("/no-param/list")
    @ApiOperation(value = "无参 返回List" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 30 , author = "chendd")
    public List list(){
        List<Point> list = Lists.newArrayList();
        list.add(new Point(10 , 15));
        list.add(new Point(20 , 25));
        list.add(new Point(30 , 35));
        list.add(new Point(40 , 45));
        list.add(new Point(50 , 55));
        return list;
    }

    @GetMapping("/param/sayHello")
    @ApiOperation(value = "一个参数（参数对象传递） 返回String" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 40 , author = "chendd")
    public String sayHello(@RequestParam @ApiParam("name") String name ,
                           @RequestParam @ApiParam("age") Integer age){
        return String.format("your name is %s , age is %d" , name , age);
    }

    @GetMapping("/param/variable/{name}/{age}")
    @ApiOperation(value = "路径变量传输 返回String" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 50 , author = "chendd")
    public String variable(@PathVariable @ApiParam("name") String name ,
                           @PathVariable @ApiParam("age") Integer age){
        return String.format("your name is %s , age is %d" , name , age);
    }

    @GetMapping("/param/entity")
    @ApiOperation(value = "多个参数 返回Entity" , notes = "Forest Api Get请求构造接口返回数据")
    @ApiOperationSupport(order = 60 , author = "chendd")
    public Point sayHello(@RequestParam @ApiParam("x坐标") Double x ,
                          @RequestParam @ApiParam("y坐标") Double y){
        Point point = new Point();
        point.setLocation(x , y);
        return point;
    }


}
