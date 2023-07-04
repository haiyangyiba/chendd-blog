package cn.chendd.blog.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dual虚表
 *
 * @author chendd
 * @date 2022/2/24 10:07
 */
@Data
@ApiModel
@TableName("dual")
public class Dual {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("dummy")
    @ApiModelProperty(value = "dummy")
    private String dummy;

}
