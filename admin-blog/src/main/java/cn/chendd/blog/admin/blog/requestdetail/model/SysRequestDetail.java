package cn.chendd.blog.admin.blog.requestdetail.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统请求详细记录
 * @auth chendd
 * @date 2020/07/20 19:00
 */
@Data
@ApiModel
@TableName("sys_request_detail")
public class SysRequestDetail {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("url")
    @ApiModelProperty(value = "请求url路径")
    private String url;

    @TableField("referer")
    @ApiModelProperty(value = "请求来源")
    private String referer;

    @TableField("browser")
    @ApiModelProperty(value = "浏览器版本")
    private String browser;

    @TableField("operatingSystem")
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;

    @TableField("isOutLink")
    @ApiModelProperty(value = "是否外站请求")
    private String isOutLink;

    @TableField("ip")
    @ApiModelProperty(value = "ip地址")
    private String ip;

    @TableField("createDate")
    @ApiModelProperty(value = "创建日期")
    private String createDate;

    @TableField("createTime")
    @ApiModelProperty(value = "创建时间")
    private String createTime;


}
