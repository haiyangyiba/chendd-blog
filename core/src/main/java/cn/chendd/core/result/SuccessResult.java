package cn.chendd.core.result;


import cn.chendd.core.enums.EnumResult;

/**
 * 操作成功结果集
 *
 * @author chendd
 * @date 2019/9/22 17:40
 */
public class SuccessResult<T> extends BaseResult {

    public SuccessResult(){
        super(EnumResult.success.name() , null , null);
    }

    public SuccessResult(T data){
        super(EnumResult.success.name() , null , data);
    }

    public SuccessResult(String message , T data){
        super(EnumResult.success.name() , message , data);
    }

}
