package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.api.assist.ForestUploadParam;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 提供Post请求的数据支持
 *
 * @author chendd
 * @date 2020/10/5 21:46
 */
@RestController
@RequestMapping(value = "/examples/forest/upload" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Forest Api Upload 数据提供" , tags = {"Forest Api Upload 数据提供"})
@ApiSort(30)
public class ApiForestUploadController extends BaseController {

    @PostMapping(value = "/multipart-file" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "基本参数，单文件上传" , notes = "Forest Api Upload 请求构造接口返回数据")
    @ApiOperationSupport(order = 10 , author = "chendd")
    public String uploadFile(@ApiParam("单文件上传") MultipartFile file ,
                         @ApiParam("名称") @RequestParam String name ,
                         @ApiParam("年龄") @RequestParam Integer age){
        return String.format("a total of %d files were uploaded , name is %s , age is %s" , 1 , name , age);
    }

    @PostMapping(value = "/multipart-basic" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "基本参数，多文件上传" , notes = "Forest Api Upload 请求构造接口返回数据")
    @ApiOperationSupport(order = 10 , author = "chendd")
    public String uploadBasicParam(@ApiParam("多文件上传") List<MultipartFile> files ,
                         @ApiParam("名称") @RequestParam String name ,
                         @ApiParam("年龄") @RequestParam Integer age){
        return String.format("a total of %d files were uploaded , name is %s , age is %s" , files.size() , name , age);
    }

    @PostMapping(value = "/multipart-entity" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "参数对象，多文件上传" , notes = "Forest Api Upload 请求构造接口返回数据")
    @ApiOperationSupport(order = 20 , author = "chendd")
    public String uploadEntityParam(@ApiParam("文件上传对象") ForestUploadParam param){
        return String.format("a total of %d files were uploaded , name is %s , age is %s" ,
                param.getFiles().size() , param.getName() , param.getAge());
    }

}
