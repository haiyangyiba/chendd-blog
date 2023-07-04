package cn.chendd.blog.admin.system.sysuser.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户参数配置
 *
 * @author chendd
 * @date 2020/8/22 19:29
 */
@Data
public class SysUserConfig {

    @ApiModelProperty("后台主题")
    private String adminTheme;

}
