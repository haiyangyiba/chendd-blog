package cn.chendd.toolkit.dbproperty.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据库配置参数实体
 *
 * @author chendd
 * @date 2019/9/12 16:09
 */
@TableName("sys_db_value")
@Data
public class SysDbValue {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    private Long id;

    @TableField("`key`")
    private String key;

    @TableField("value")
    private String value;

    @TableField("`group`")
    private String group;

    @TableField("remark")
    private String remark;

}
