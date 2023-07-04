package cn.chendd.blog.base.thymeleaf.format.impl;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 金额格式化实现类：千分位，保留2位小数，五舍六入
 *
 * @author chendd
 * @date 2020/5/31 22:47
 */
public class AmountFormat implements IFormatType {

    @Override
    public String format(String value) {
        DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String text = decimalFormat.format(NumberUtils.createNumber(value));
        return text;
    }
}
