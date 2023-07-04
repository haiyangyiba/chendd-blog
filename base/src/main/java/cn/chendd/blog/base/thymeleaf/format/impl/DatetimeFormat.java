package cn.chendd.blog.base.thymeleaf.format.impl;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.utils.DateFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * 时间日期格式化实现类：15分钟、2小时、3天前等
 *
 * @author chendd
 * @date 2020/10/31 23:50
 */
public class DatetimeFormat implements IFormatType {

    @Override
    public String format(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int lens = value.length();
        if (lens == 10) {
            value = value + " " + Constant.TIME_BEGIN;
        }
        return DateFormat.getTimeInterval(value);
    }

}
