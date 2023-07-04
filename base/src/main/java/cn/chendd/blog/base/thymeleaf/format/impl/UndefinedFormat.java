package cn.chendd.blog.base.thymeleaf.format.impl;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;

/**
 * 默认未定义的格式化
 *
 * @author chendd
 * @date 2020/5/31 23:10
 */
public class UndefinedFormat implements IFormatType {

    @Override
    public String format(String value) {
        return value;
    }
}
