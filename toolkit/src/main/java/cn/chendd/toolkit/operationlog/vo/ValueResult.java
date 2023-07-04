package cn.chendd.toolkit.operationlog.vo;

import cn.chendd.toolkit.operationlog.annotions.Log;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 构造数据值对象
 *
 * @author chendd
 * @date 2020/6/26 15:31
 */
@Data
@AllArgsConstructor
public class ValueResult {

    private Log log;

    private Map<String , Object> params;

    private Object returnValue;

}
