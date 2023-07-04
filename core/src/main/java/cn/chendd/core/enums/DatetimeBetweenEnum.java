package cn.chendd.core.enums;

import java.util.Calendar;
import java.util.Date;

/**
 * 定义时间时常间隔
 *
 * @author chendd
 * @date 2020/11/1 0:03
 */
public enum DatetimeBetweenEnum {

    /**
     * 5分钟内称为刚刚
     */
    FIVE_MINUTES(0L , 300000L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            return "刚刚";
        }

    },
    /**
     * 不满1小时按分钟算
     */
    ONE_HOUR(300000L , 3600000L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            Double oneMin = 60000.0;
            return (int)(Math.ceil((double)valueDate / oneMin)) + "分钟前";
        }

    },
    /**
     * 不满1天按小时算
     */
    MANY_HOUR(3600000L , 86400000L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            Double oneMin = this.getStart() / 1.0;
            return (int)(Math.round((double)valueDate / oneMin)) + "小时前";
        }

    },
    /**
     * 不满30天为1月的按天算
     */
    ONE_MONETH(86400000L , 2592000000L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            Double oneDay = this.getStart() / 1.0;
            return (int)(Math.round((double)valueDate / oneDay)) + "天前";
        }

    },
    /**
     * 不满12个月为1年的按月算
     */
    MANY_MONTH(18000000L , 31536000000L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            Date current = new Date();
            Calendar serviceCalendar = Calendar.getInstance();
            serviceCalendar.setTime(serviceDate);
            int num = 0;
            while(current.after(serviceCalendar.getTime())){
                serviceCalendar.add(Calendar.MONTH, 1);
                num++;
            }
            //如果当前日期天要大于比较的日期天数，让相距月份 - 1
            int serviceDay = serviceCalendar.get(Calendar.DAY_OF_MONTH);
            Calendar currentCalendar = Calendar.getInstance();
            int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
            if (currentDay > serviceDay) {
                num--;
            }
            return num + "个月前";
        }

    },
    /**
     * 其它情况按照一年365天计算所得=1000L*60*60*24*365
     */
    OTHER(-1L , -1L){
        @Override
        public String formatDate(Long valueDate , Date serviceDate) {
            //按年算
            Date current = new Date();
            Calendar serviceCalendar = Calendar.getInstance();
            serviceCalendar.setTime(serviceDate);
            int num = 0;
            while(current.after(serviceCalendar.getTime())){
                serviceCalendar.add(Calendar.YEAR, 1);
                num++;
            }
            return num + "年前";
        }
    }
    ;

    private Long start , end;

    DatetimeBetweenEnum(Long start , Long end){
        this.start = start;
        this.end = end;
    }

    public String formatDate(Long valueDate , Date serviceDate) {
        throw new UnsupportedOperationException("该方法不能为调用");
    }

    public static String getFormatDate(Long valueDate , Date serviceDate){
        DatetimeBetweenEnum enumDatetimes[] = DatetimeBetweenEnum.values();
        DatetimeBetweenEnum datetimeBetween = null;
        for (DatetimeBetweenEnum between : enumDatetimes) {
            if(valueDate >= between.getStart() && valueDate <= between.getEnd()){
                datetimeBetween = between;
                break;
            }
        }
        if(datetimeBetween == null){
            datetimeBetween = DatetimeBetweenEnum.OTHER;
        }
        return datetimeBetween.formatDate(valueDate, serviceDate);
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }


}
