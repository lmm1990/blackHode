package com.github.lmm1990.datasourceclient.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * 字符串转换成时间对象
     *
     * @param time   字符串时间
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date parse(Object time, String format) {
        if (time == null) {
            return null;
        }
        return parse(time.toString(), format);
    }

    /**
     * 字符串转换成时间对象
     *
     * @param time   字符串时间
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date parse(String time, String format) {
        if (time == null || "".equals(time)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 时间对象转换成字符串
     *
     * @param time   Date对象
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String formatToString(Date time, String format) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.format(time);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间对象转换成字符串
     *
     * @param time   Date对象
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String formatToString(Object time, String format) {
        if (time == null) {
            return "";
        }
        if(time instanceof Timestamp){
            return formatToString((Timestamp)time,format);
        }
        return formatToString(parse(time.toString(), "yyyy-MM-dd HH:mm:ss"), format);
    }

    /**
     * 获取当前时间是那一周
     *
     * @param time
     * @return
     */
    public static int getDayOfWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            return 7;
        }
        return week;
    }

    /**
     * 获取星期1~7
     *
     * @param calendar
     * @return
     */
    public static int getDayOfWeek(Calendar calendar) {
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            return 7;
        }
        return week;
    }

    /**
     * 获取当前星期1~7
     *
     * @return
     */
    public static int getCurrentDayOfWeek() {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            return 7;
        }
        return week;
    }

    /**
     * 获得日期年份
     *
     * @param time Calendar对象
     * @return int 日期年份
     */
    public static int getYear(Calendar time) {
        return time.get(Calendar.YEAR);
    }

    /**
     * 获得当前年份<br />
     * Author:刘明明<br />
     * CreateTime:2018年1月23日19:23:1
     *
     * @return int 日期年份
     */
    public static int getCurrentYear() {
        return getYear(Calendar.getInstance());
    }

    /**
     * 获得日期年份
     *
     * @param time Date对象
     * @return int 日期年份
     */
    public static int getYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getYear(calendar);
    }

    /**
     * 获得日期月份
     *
     * @param time Calendar对象
     * @return int 日期月份
     */
    public static int getMonth(Calendar time) {
        return time.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得日期月份
     *
     * @param time Date对象
     * @return int 日期月份
     */
    public static int getMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getMonth(calendar);
    }

    /**
     * 获得年份所对应的天
     *
     * @param time Calendar对象
     * @return int 年份所对应的天
     */
    public static int getDayOfYear(Calendar time) {
        return time.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得年份所对应的天
     *
     * @param time Date对象
     * @return int 年份所对应的天
     */
    public static int getDayOfYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getDayOfYear(calendar);
    }

    /**
     * 增加天
     *
     * @param time Calendar对象
     * @param days 要增加的天数
     * @return Calendar Calendar对象
     */
    public static Calendar addDays(Calendar time, int days) {
        time.set(Calendar.DAY_OF_YEAR, time.get(Calendar.DAY_OF_YEAR) + days);
        return time;
    }

    /**
     * 增加天
     *
     * @param time Date对象
     * @param days 要增加的天数
     * @return Calendar Calendar对象
     */
    public static Calendar addDays(Date time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addDays(calendar, days);
    }

    /**
     * 增加月
     *
     * @param time   Calendar对象
     * @param months 要增加的月数
     * @return Calendar Calendar对象
     */
    public static Calendar addMonths(Calendar time, int months) {
        // time.set(Calendar.MONTH, time.get(Calendar.MONTH) + months);
        time.add(Calendar.MONTH, months);
        return time;
    }

    /**
     * 增加月
     *
     * @param time   Date对象
     * @param months 要增加的月数
     * @return Calendar Calendar对象
     */
    public static Calendar addMonths(Date time, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addMonths(calendar, months);
    }

    /**
     * 增加年
     *
     * @param time  Calendar对象
     * @param years 要增加的年数
     * @return Calendar Calendar对象
     */
    public static Calendar addYears(Calendar time, int years) {
        time.set(Calendar.YEAR, time.get(Calendar.YEAR) + years);
        return time;
    }

    /**
     * 增加年
     *
     * @param time  Date对象
     * @param years 要增加的年数
     * @return Calendar Calendar对象
     */
    public static Calendar addYears(Date time, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addYears(calendar, years);
    }

    /**
     * 获取当前日期在一个月中的天数
     */
    public static int getDayOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getDayOfMonth(calendar);
    }

    public static int getDayOfMonth(Calendar time) {
        return time.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期在一个月中的天数
     */
    public static int getMaxDayOfMath(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getMaxDayOfMath(calendar);
    }

    public static int getMaxDayOfMath(Calendar time) {
        return time.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月的的一号
     */
    public static Date getNumberOneOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        // 这是已知的日期
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        // 1号的日期
        date = c.getTime();
        return date;
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getHour(calendar);
    }

    /**
     * 功能描述：返回小时
     * <p>
     * 日期
     *
     * @return 返回小时
     */
    public static int getCurrentHour() {
        return getHour(Calendar.getInstance());
    }

    /**
     * 功能描述：返回小时
     *
     * @param calendar 日期
     * @return 返回小时
     */
    public static int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 功能描述：返回分钟数
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getMinute(calendar);
    }

    /**
     * 功能描述：返回分钟数
     *
     * @param calendar 日期
     * @return 返回小时
     */
    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 功能描述：返回当前分钟
     * <p>
     * 日期
     *
     * @return 返回小时
     */
    public static int getCurrentMinute() {
        return getMinute(Calendar.getInstance());
    }

    /**
     * 获得相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferDays(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获得年份列表
     */
    public static HashSet<Integer> getYearList(String startDate, String endDate) {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            return new HashSet<>();
        }
        return getYearList(parse(startDate, "yyyy-MM-dd"), parse(endDate, "yyyy-MM-dd"));
    }

    /**
     * 获得年份列表
     */
    public static HashSet<Integer> getYearList(Date startDate, Date endDate) {
        int startYear = DateUtil.getYear(startDate);
        int endYear = DateUtil.getYear(endDate);
        HashSet<Integer> yearList = new HashSet<>();
        while (startYear <= endYear) {
            yearList.add(startYear);
            startYear++;
        }
        return yearList;
    }

    //1天的秒数
    public static final long daySecond = 60 * 60 * 24;

    /**
     * 获得短时间
     *
     * @param baseTime 数据库时间
     */
    public static String getIMSmallTime(String baseTime, boolean showHourMinute) {
        if (baseTime.isEmpty()) {
            return "";
        }
        long second;
        Calendar now = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        long currentDaySecond = (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//今天的秒数
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time.setTime(sdf.parse(baseTime));
            second = (now.getTimeInMillis() - time.getTimeInMillis()) / 1000;
        } catch (Exception e) {
            return "";
        }
        int hour = getHour(time);
        String hourStr = "";
        //凌晨:3:00--6:00早晨:6:00---8:00上午:8:00--11:00中午:11:00--13:00下午:13:00--17:00傍晚:17:00--19:00晚上:19:00--23:00深夜:23:00--3:00
        if (hour >= 0 && hour <= 6) {
            hourStr = "凌晨";
        }
        if (hour > 6 && hour <= 12) {
            hourStr = "上午";
        }
        if (hour > 12 && hour <= 13) {
            hourStr = "中午";
        }
        if (hour > 13 && hour <= 18) {
            hourStr = "下午";
        }
        if (hour > 18 && hour <= 24) {
            hourStr = "晚上";
        }
        String hourMinute = DateUtil.formatToString(time.getTime(), "HH:mm");
        if (second < currentDaySecond) {
            return String.format("%s %s", hourStr, hourMinute);
        }
        if (second < (currentDaySecond + daySecond)) {
            return showHourMinute ? String.format("昨天 %s", hourMinute) : "昨天";
        }
        String date = DateUtil.formatToString(time.getTime(), "MM-dd");
        return showHourMinute ? String.format("%s %s", date, hourMinute) : date;
    }

    /**
     * 日期转天数，等同于mysql TO_DAYS
     *
     * @param date 日期
     */
    public static long toDays(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return 719529 + time / 1000 / 60 / 60 / 24;
    }

    /**
     * 减去天数
     *
     * @param date 字符串时间
     * @param pattern 时间格式
     * @param days 天数
     */
    public static Date reduceDays(String date,String pattern,int days){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date time=simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 减去小时
     *
     * @param date 字符串时间
     * @param pattern 时间格式
     * @param ours 小时数
     */
    public static Date reduceOurss(String date,String pattern,int ours){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date time=simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ours);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 减去小时
     *
     * @param date 字符串时间
     * @param pattern 时间格式
     * @param hours 小时数
     */
    public static Date reduceHours(String date,String pattern,int hours){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date time=simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 减去小时
     *
     * @param date 字符串时间
     * @param pattern 时间格式
     * @param mins 小时数
     */
    public static Date reduceMin(String date,String pattern,int mins){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date time=simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - mins);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 天数转日期，等同于mysql FROM_DAYS
     *
     * @param days 天数
     */
    public static Date fromDays(long days) {
        return new Date((days-719528) * 1000 * 60 * 60 * 24);
    }

    /**
     * 获得总月份
     * @param date 日期
     * @return 总月份
     */
    public static int getTotalMonth(Date date){
        //传入日期
        Calendar calder = Calendar.getInstance();
        calder.setTime(date);//设置时间
        int year = calder.get(Calendar.YEAR);//获取年份
        int month=calder.get(Calendar.MONTH);//获取月份
        return year*12+month;
    }

    public static void main(String[] args) {
        long days = toDays(DateUtil.parse("2020-08-12", "yyyy-MM-dd"));
        System.out.println(days);
        System.out.println(DateUtil.formatToString(fromDays(days), "yyyy-MM-dd"));

//        System.out.println(toDays(DateUtil.parse("2020-08-12", "yyyy-MM-dd")));
//        System.out.println(toDays(DateUtil.parse("2020-08-12 00:00:00", "yyyy-MM-dd HH:mm:ss")));
//        System.out.println(toDays(DateUtil.parse("2020-08-06", "yyyy-MM-dd")));
    }
}
