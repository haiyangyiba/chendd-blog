package cn.chendd.blog.base.thymeleaf.format.impl;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.NumberFormat;

/**
 * 数字百分比
 *
 * @author chendd
 * @date 2020/5/31 22:54
 */
public class PercentageFormat implements IFormatType {

    @Override
    public String format(String value) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        String text = numberFormat.format(NumberUtils.createNumber(value));
        return text;
    }
}
