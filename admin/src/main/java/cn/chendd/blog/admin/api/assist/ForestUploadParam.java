package cn.chendd.blog.admin.api.assist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Forest文件上传参数实体
 *
 * @author chendd
 * @date 2020/10/9 17:27
 */
@Data
public class ForestUploadParam {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("文件对象List")
    private List<MultipartFile> files;

}
