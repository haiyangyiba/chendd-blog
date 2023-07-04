package cn.chendd.toolkit.operationlog.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 执行结果枚举
 *
 * @author chendd
 * @date 2021/4/16 19:52
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultEnum {

    /**
     * 执行中
     */
    running("执行中"),
    /**
     * 操作成功
     */
    success("成功"),
    /**
     * 操作失败
     */
    error("错误"),
    ;

    /**
     * 提示文本
     */
    private String text;

    ResultEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return this.name();
    }
}