package cn.chendd.core.result;


import cn.chendd.core.enums.EnumResult;

/**
 * 空结果集类型
 *
 * @author chendd
 * @date 2019/10/27 1:42
 */
public class NoneResult<T> extends BaseResult {

    public NoneResult(){
        super(EnumResult.none.name() , null , null);
    }

    public NoneResult(String message , T data){
        super(EnumResult.none.name() , message , data);
    }

    public NoneResult(String message){
        super(EnumResult.none.name() , message , null);
    }

}
