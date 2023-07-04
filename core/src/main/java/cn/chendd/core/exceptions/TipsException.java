package cn.chendd.core.exceptions;

/**
 * 提示类异常定义
 *
 * @author chendd
 * @date 2020/10/28 21:40
 */
public class TipsException extends RuntimeException {

    public TipsException(String message , Object...params){
        super(String.format(message , params));
    }

    public TipsException(Exception e){
        super(e);
    }

}
