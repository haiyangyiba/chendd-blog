package cn.chendd.core.result;

import cn.chendd.core.enums.EnumResult;

/**
 * 操作错误结果集
 *
 * @author chendd
 * @date 2019/9/22 17:43
 */
public class ErrorResult<T> extends BaseResult {

    public ErrorResult(String message , T data){
        super(EnumResult.error.name() , message , data);
    }

    public ErrorResult(String message){
        super(EnumResult.error.name() , message , null);
    }

}
