package com.backinfile.cardRouge;

import com.backinfile.cardRouge.SysException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Time {
    public static final long SEC = 1000;
    public static final long MIN = 60 * SEC;
    public static final long HOUR = 60 * MIN;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;
    public static final long MONTH = 30 * DAY;
    public static final long YEAR = 12 * MONTH;

    public static final int TIME_ZONE_OFFSET = TimeZone.getDefault().getRawOffset();
    private static final long GMT_WEEK_OFFSET = 3 * DAY; // GMT第一天是星期四

    public static final String dataFormatter = "yyyy-MM-dd HH:mm:ss";
    public static final String dataFormatter2 = "yyyy-MM-dd HH:mm:ss";
    public static final String dayFormatter = "yyyy-MM-dd";
    public static final String dayFormatter2 = "yyyyMMdd";

    private static final Map<String, SimpleDateFormat> formatterMap = new HashMap<>();

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getCurMillis() {
        return currentTimeMillis();
    }


    public static String formatTime() {
        return formatTime(currentTimeMillis(), dataFormatter);
    }

    public static String formatTime(long time, String pattern) {
        SimpleDateFormat format = getFormatter(pattern);
        return format.format(new Date(time));
    }

    public static long parseData(String dataStr) {
        return parseData(dataStr, dataFormatter, false);
    }

    /**
     * 解析时间
     *
     * @param exception 如果解析失败是否抛出异常
     */
    public static long parseData(String dataStr, String pattern, boolean exception) {
        try {
            SimpleDateFormat format = getFormatter(pattern);
            return format.parse(dataStr).getTime();
        } catch (Exception e) {
            if (exception) {
                throw new SysException(e);
            } else {
                return -1;
            }
        }
    }

    public static int getYYYYMMDD(long time) {
        return Integer.parseInt(formatTime(time, dayFormatter2));
    }

    private static SimpleDateFormat getFormatter(String pattern) {
        return formatterMap.computeIfAbsent(pattern, SimpleDateFormat::new);
    }

    public static int currentDay(long time) {
        return (int) ((time + TIME_ZONE_OFFSET) / DAY);
    }

    public static boolean isSameDay(long time1, long time2) {
        return currentDay(time1) == currentDay(time2);
    }

    /**
     * 返回两个时间的天数差（>=0）
     */
    public static int getDiffDay(long time1, long time2) {
        if (time1 > time2) {
            return currentDay(time1) - currentDay(time2);
        }
        return currentDay(time2) - currentDay(time1);
    }

    public static int currentWeek(long time) {
        return (int) ((time + TIME_ZONE_OFFSET + GMT_WEEK_OFFSET) / WEEK);
    }

    /**
     * 获取当天时间零点
     */
    public static long getDayFirstMills(long time) {
        return time - (time + TIME_ZONE_OFFSET) % DAY;
    }

    public static long getWeekFirstMills(long time) {
        return time - (time + TIME_ZONE_OFFSET + GMT_WEEK_OFFSET) % WEEK;
    }

    /**
     * 当前时间是周几
     */
    public static long currentWeekDay(long time) {
        return (int) ((time - getWeekFirstMills(time)) / DAY) + 1;
    }

    public static long getNextMinTime(long time) {
        return time - time % MIN + MIN;
    }

    public static long getNextHourTime(long time) {
        return time - time % HOUR + MIN;
    }

    public static long getNextDayTime(long time) {
        return time - (time + TIME_ZONE_OFFSET) % DAY + DAY;
    }
}