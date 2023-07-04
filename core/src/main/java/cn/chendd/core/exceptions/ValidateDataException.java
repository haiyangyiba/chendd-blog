package cn.chendd.core.exceptions;

/**
 * 逻辑校验异常抛出
 *
 * @author chendd
 * @date 2019/9/19 15:31
 */
public class ValidateDataException extends RuntimeException {

    public ValidateDataException(String message){
        super(message);
    }

    public ValidateDataException(Exception e){
        super(e);
    }

    public ValidateDataException(String message , Exception e) {
        super(message , e);
    }
}