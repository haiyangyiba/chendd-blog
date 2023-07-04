package cn.chendd.core.exceptions;

/**
 * 转换为日期格式异常类型定义
 *
 * @author chendd
 * @date 2020/7/12 22:32
 */
public class ParseDateException extends RuntimeException {

    public ParseDateException(String date , String fmt , Throwable e) {
        super(String.format("转换日期 %s 为 %s 出现错误" , date , fmt) , e);
    }

}
