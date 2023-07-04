package cn.chendd.blog.admin.system.sysmenu.model;

import cn.chendd.blog.base.enums.EnumMenuType;
import cn.chendd.blog.base.enums.EnumStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

/**
 * 系统菜单表
 * @auth chendd
 * @date 2020/06/09 19:24
 */
@Data
@ApiModel
@TableName("sys_menu")
public class SysMenu implements Serializable {

    @TableField("menuId")
    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "menuId" , type = IdType.ASSIGN_ID)
    private Long menuId;

    @TableField("menuName")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @TableField("menuType")
    @ApiModelProperty(value = "菜单类型，按钮、资源URL")
    private EnumMenuType menuType;

    @TableField("menuUrl")
    @ApiModelProperty(value = "菜单地址")
    private String menuUrl;

    @TableField("menuIcon")
    @ApiModelProperty(value = "菜单显示图标")
    private String menuIcon;

    @TableField("parentId")
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;

    @TableField("menuKey")
    @ApiModelProperty(value = "菜单标识")
    private String menuKey;

    @TableField("requestMethod")
    @ApiModelProperty("请求类型")
    private RequestMethod requestMethod;

    @TableField("menuOrder")
    @ApiModelProperty(value = "菜单排序")
    private String menuOrder;

    @TableField("menuStatus")
    @ApiModelProperty(value = "菜单状态，可用/禁用等")
    private EnumStatus menuStatus;

    @TableField("createDate")
    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @TableField("updateDate")
    @ApiModelProperty(value = "最后更新时间")
    private String updateDate;

    @TableField("remark")
    @ApiModelProperty(value = "备注信息")
    private String remark;

    @TableField("dataStatus")
    @TableLogic
    @JsonIgnore
    @ApiModelProperty(value = "数据状态，可用或禁用" , example = "DISABLE" , hidden = true)
    private String dataStatus;


}
