package cn.chendd.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Controller返回结果定义
 *
 * @author chendd
 * @date 2019/9/22 17:35
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseResult<T> {

    /**
     * 操作结果，success , error , input 等 EnumResult
     */
    private String result;
    private String message;
    private T data;

}
