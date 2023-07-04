package cn.chendd.blog.base.enums;

/**
 * 系统异常类型定义
 *
 * @author chendd
 * @date 2019/10/26 22:55
 */
public enum EnumExceptionType {

    NullPointerException("系统内部空异常"),
    ;

    private String message;

    EnumExceptionType(String message){
        this.message = message;
    }

    public static EnumExceptionType getExceptionType(String name){
        for (EnumExceptionType exceptionType : EnumExceptionType.values()) {
            if(exceptionType.name().equals(name)){
                return exceptionType;
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }
}
