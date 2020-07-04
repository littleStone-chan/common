package com.chen.tools.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {

    // date转化成String
    public static String date2Str(Date date) {
        if (date==null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 转化到天的
     * @param date
     * @return
     */
    public static String date2Str4dd(Date date) {
        if (date==null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 转化到月
     * @param date
     * @return
     */
    public static String date2Str4MM(Date date) {
        if (date==null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }



    public static Date nextMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    //String 转化成date
    public static java.util.Date str2Date(String strDate) {
        if (strDate==null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    //获取指定的时间
    public static java.util.Date getDay(int year,int month,int day) {
        Calendar cal = new GregorianCalendar();
        cal.set(year,month+1,day);
        return cal.getTime();
    }

    //获取当天的开始时间
    public static java.util.Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //获取当天的结束时间
    public static java.util.Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    //获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    //获取昨天的结束时间
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    //获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }
    //获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    //获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        return getBeginDayOfWeek(new Date());
    }

    /**
     * 获取周的开始时间
     * @param date
     * @return
     */
    public static Date getBeginDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek(){
        return getEndDayOfWeek(getBeginDayOfWeek());
    }

    /**
     * 获取
     * @return
     */
    public static Date getEndDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }
    //获取上周的结束时间
    public static Date getEndDayOfLastWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }
    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取月的开始时间
     * @param date
     * @return
     */
    public static Date getBeginDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        calendar.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        return getEndDayOfMonth(new Date());
    }

    /**
     * 获取 月的结束时间
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        int day = calendar.getActualMaximum(5);
        calendar.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), day);
        return getDayEndTime(calendar.getTime());
    }

    //获取上月的开始时间
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }
    //获取上月的结束时间
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }
    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        return getBeginDayOfYear(new Date());
    }

    /**
     * 获取年的开始时间
     * @param date
     * @return
     */
    public static Date getBeginDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }


    //获取去年的开始时间
    public static Date getBeginDayOfLastYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear()-1);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }
    //获取本年的结束时间
    public static java.util.Date getEndDayOfYear() {
        return  getEndDayOfYear(new Date());
    }

    /**
     * 获取年的结束时间
     * @param date
     * @return
     */
    public static java.util.Date getEndDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }
    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }
    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }
    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();
        return days;
    }





    //两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }
    //获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }
    //获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }
    //返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }
    //返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }
    //返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }


    //两个日期是否为同一天
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }


    //获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }
    //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }


    private static int invalidAge = -1;//非法的年龄，用于处理异常。

    /**
     *根据身份证号码计算年龄
     * @param idNumber 考虑到了15位身份证，但不一定存在
     */
    public static int getAgeByIDNumber(String idNumber) {

        String dateStr;
        if (idNumber.length() == 15) {
            dateStr = "19" + idNumber.substring(6, 12);
        } else if (idNumber.length() == 18) {
            dateStr = idNumber.substring(6, 14);
        } else {//默认是合法身份证号，但不排除有意外发生
            return invalidAge;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date birthday = simpleDateFormat.parse(dateStr);
            return getAgeByDate(birthday);
        } catch (ParseException e) {
            return invalidAge;
        }
    }


    /**
     *根据生日计算年龄
     * @param dateStr 这样格式的生日 1990-01-01
     */

    public static int getAgeByDateString(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthday = simpleDateFormat.parse(dateStr);
            return getAgeByDate(birthday);
        } catch (ParseException e) {
            return -1;
        }
    }


    public static int getAgeByDate(Date birthday) {
        Calendar calendar = Calendar.getInstance();

        //calendar.before()有的点bug
        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
            return invalidAge;
        }


        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(birthday);


        int yearBirthday = calendar.get(Calendar.YEAR);
        int monthBirthday = calendar.get(Calendar.MONTH);
        int dayOfMonthBirthday = calendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirthday;

        if (monthNow <= monthBirthday && monthNow == monthBirthday && dayOfMonthNow < dayOfMonthBirthday || monthNow < monthBirthday) {
            age--;
        }

        return age;
    }





    /*
     * 将毫秒换算成天、时、分、秒格式
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

    /*
     * 将毫秒换算成HH:mm:ss的格式
     */
    public static String formatTime2(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();

        if(hour > 0 || day > 0) {
            Long dayAddHour=day*24+hour;
            if (dayAddHour>0 && dayAddHour>=10){
                sb.append(dayAddHour);
                sb.append(":");
            }else if (dayAddHour > 0){
                sb.append("0");
                sb.append(dayAddHour);
                sb.append(":");
            }
        }else {
            sb.append("00:");
        }
        if(minute > 0 && minute >=10) {
            sb.append(minute);
            sb.append(":");
        }else if (minute >0){
            sb.append("0");
            sb.append(minute);
            sb.append(":");
        }else  {
            sb.append("00:");
        }
        if(second > 0 && second >=10) {
            sb.append(second);
        }else if (second > 0){
            sb.append("0");
            sb.append(second);
        }else {
            sb.append("00");
        }

        return sb.toString();
    }

    /**
     * 获取之前的时间
     * @param year 年数 1表示去年
     * @return
     */
    public static Date getBeforeDate(Integer year){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -year);
        return c.getTime();
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(date2Str(getBeforeDate(1)));
    }



}