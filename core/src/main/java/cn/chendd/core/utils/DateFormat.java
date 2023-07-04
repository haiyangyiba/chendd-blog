package cn.chendd.core.utils;

import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.enums.DatetimeBetweenEnum;
import cn.chendd.core.exceptions.ParseDateException;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期格式化工具类
 *
 * @author chendd
 * @date 2020/6/7 16:33
 */
public class DateFormat extends org.apache.commons.lang3.time.DateFormatUtils {

    public static final FastDateFormat ISO_EXTENDED_DATETIME_FORMAT
            = FastDateFormat.getInstance(Constant.DATE_TIME_FORMAT_DEFAULT_PATTERN);

    /**
     * 格式化日期-时间，默认包含日期和时间
     */
    public static String formatDatetime(Long date) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    /**
     * 格式化日期-时间，默认包含日期和时间
     */
    public static String formatDatetime(Date date) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    /**
     * 格式化日期-时间
     */
    public static String formatDatetime(Long date , String format) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , format);
    }

    public static String formatDatetime(Date date , String format) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , format);
    }

    /**
     * 格式化日期-时间，默认包含日期和时间
     */
    public static String formatDatetime() {
        return formatDatetime(new Date());
    }

    /**
     * 格式化为-分割的日期
     */
    public static String formatDate(Date date) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
    }

    /**
     * 格式化为-分割的日期
     */
    public static String formatDate(Long date) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
    }

    /**
     * 格式化为-分割的日期
     */
    public static String formatDate() {
        return formatDate(new Date());
    }

    /**
     * 格式化为:分割的时间
     */
    public static String formatTime(Date date) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date , ISO_8601_EXTENDED_TIME_FORMAT.getPattern());
    }

    /**
     * 格式化为:分割的时间
     */
    public static String formatTime() {
        return formatTime(new Date());
    }

    /**
     * @param date 日期
     * @return 转换为日期
     */
    public static Date parseDate(String date) {
        return parseDate(date , ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
    }

    public static Date parseDatetime(String date) {
        return parseDate(date , ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    /**
     * @param date 日期
     * @param fmt 日期格式
     * @return 转换为日期
     */
    public static Date parseDate(String date , String fmt) {
        Date newDate;
        try {
            newDate = DateUtils.parseDate(date, fmt);
        } catch (ParseException e) {
            throw new ParseDateException(date , fmt , e);
        }
        return newDate;
    }


    /**
     * 获取日期距离现在的时刻，如：5分钟内为刚刚、30分钟内、1天前等等
     * @param serviceDate 日期类型
     * @return 间隔时常描述
     */
    public static String getTimeInterval(Date serviceDate){
        Long valueDate = System.currentTimeMillis() - serviceDate.getTime();
        String value = DatetimeBetweenEnum.getFormatDate(valueDate, serviceDate);
        return value;
    }

    /**
     * 获取日期距离现在的时刻，如：5分钟内为刚刚、30分钟内、1天前等等
     * @param time 日期类型
     * @return 间隔时常描述
     */
    public static String getTimeInterval(String time){
        return getTimeInterval(DateFormat.parseDatetime(time));
    }

}
