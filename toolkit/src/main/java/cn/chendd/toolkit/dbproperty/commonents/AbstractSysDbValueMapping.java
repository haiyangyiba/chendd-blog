package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 系统参数映射对象
 *
 * @author chendd
 * @date 2019/9/13 18:14
 */
@Data
public abstract class AbstractSysDbValueMapping {

    /**
     * 所有参数集合
     */
    @JSONField(serialize = false)
    private List<SysDbValue> allList;

}
