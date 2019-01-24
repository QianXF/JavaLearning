package com.qianxuefeng.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class DateTimeUtil {

//    private final static Logger log = LoggerFactory.getLogger(DateTimeUtil.class);
//
//    private DateTimeUtil() {
//    }
//
//    public static final long    ONE_HOURS_MILSEC                    = 60 * 60 * 1000;
//    public static final long    FIRST_HOURS_OFFSET_MILSEC           = 60 * 60 * 1000;
//    public static final long    ONE_DAY_SEC                         = 24 * 60 * 60;
//
//    public static final String DATE_PATTERN_MONTH                  = "yyyy-MM";
//    public static final String DATE_PATTERN_MONTH_SHORT            = "yyyyMM";
//    public static final String DATE_PATTERN_SHORT                  = "yyyy-MM-dd";
//    public static final String DATE_PATTERN_CHINESE_SHORT          = "yyyy年MM月dd日";
//    public static final String DATE_COMPRESS_PATTERN               = "yyyyMMdd";
//    public static final String DATE_PATTERN_NOSECOND               = "yyyy-MM-dd HH:mm";
//    public static final String DATE_PATTERN_NOSECOND_COMPRESS      = "yyyyMMddHHmm";
//    public static final String DATE_PATTERN_LONG                   = "yyyy-MM-dd HH:mm:ss";
//    public static final String DATE_PATTERN_LONG_COMPRESS          = "yyyyMMddHHmmss";
//
//    public static final int     DATE_START_INDEX_OF_DEFAULT_PATTERN = 8;
//    private static final String INVALID_PARAM_MSG                   = "The date could not be null!";
//


    public static final long    ONE_DAY_MILSEC                      = 24 * 60 * 60 * 1000;
    public static final long    FIRST_DAY_OFFSET_MILSEC             = 8 * 60 * 60 * 1000;
    public static long dateDayDiff(Date d1, Date d2) {
        long millis1 = d1.getTime() + FIRST_DAY_OFFSET_MILSEC;
        long millis2 = d2.getTime() + FIRST_DAY_OFFSET_MILSEC;
        long day1 = millis1 / ONE_DAY_MILSEC;
        long day2 = millis2 / ONE_DAY_MILSEC;
        return day1 - day2;
    }

    public static final long    ONE_MINUTE_MILSEC                      = 60 * 1000;
    public static long minuteDayDiff(Date d1, Date d2) {
        long minute1 = d1.getTime() / ONE_MINUTE_MILSEC;
        long minute2 = d2.getTime() / ONE_MINUTE_MILSEC;
        return minute1 - minute2;
    }
//
//    public static boolean dateFormatValid(String time) {
//        if (StringUtils.isBlank(time)) {
//            return false;
//        }
//        String format = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss
//        return Pattern.compile(format).matcher(time).matches();
//    }
//
//    /**
//     * 小时差
//     * @param d1
//     * @param d2
//     * @return
//     */
//    public static long dateHoursDiff(Date d1, Date d2) {
//        long millis1 = d1.getTime() + FIRST_HOURS_OFFSET_MILSEC;
//        long millis2 = d2.getTime() + FIRST_HOURS_OFFSET_MILSEC;
//        long hour1 = millis1 / ONE_HOURS_MILSEC;
//        long hour2 = millis2 / ONE_HOURS_MILSEC;
//        return hour1 - hour2;
//    }
//
//    public static long countLoanTerm(Date beginDate, Date endDate) {
//        return DateTimeUtil.dateDayDiff(endDate, beginDate);
//    }
//
//    //n可以向前或者向后（如-1、1分别代表向前一天和向后一天）
//    public static Date getNumDay(Date date, Integer n) {
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.DAY_OF_YEAR, n);
//
//        return c.getTime();
//    }
//
//
//    public static Date getStandardDate(Date param) {
//        if (null == param) {
//            return null;
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(param);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        return calendar.getTime();
//    }
//
//    //n可以向前或者向后（如-1、1分别代表向前一小时和向后一消失）
//    public static Date getNumHour(Date date, Integer n) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.HOUR_OF_DAY, n);
//        return c.getTime();
//    }
//
//    public static String getYMD(Date date) {
//        return getYMD(date, DATE_PATTERN_SHORT);
//    }
//
//    public static String getYMD_Chinese(Date date) {
//        return getYMD(date, DATE_PATTERN_CHINESE_SHORT);
//    }
//
//    public static String getYM(Date date) {
//        return getYMD(date, DATE_PATTERN_MONTH);
//    }
//

    private static final String DATE_PATTERN_LONG_NO_SPLIT = "yyyyMMddHHmmss";
    private static final String DATE_PATTERN_SHORT_NO_SPLIT = "yyyyMMdd";
    private static final String DATE_PATTERN_YMD_HR = "yyyy-MM-dd";
    private static final String DATE_PATTERN_YMDHMS_HR = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN_SHORT_CN = "yyyy年MM月dd日";

    private static final String DATE_PATTERN_LONG_CN = "yyyy年MM月dd日HH时mm分ss秒";

    public static String formatYMDHMS_NO_SPLIT(Date date) {
        return format(date, DATE_PATTERN_LONG_NO_SPLIT);
    }

    public static String formatYMD_NO_SPLIT(Date date) {
        return format(date, DATE_PATTERN_SHORT_NO_SPLIT);
    }

    public static String formatYMD_HR(Date date) {
        return format(date, DATE_PATTERN_YMD_HR);
    }

    public static String formatYMDHMS_HR(Date date) {
        return format(date, DATE_PATTERN_YMDHMS_HR);
    }

    public static String formatShortCN(Date date) {
        return format(date, DATE_PATTERN_SHORT_CN);
    }

    public static String formatLongCN(Date date) {
        return format(date, DATE_PATTERN_LONG_CN).replace("00时00分00秒", "零时").replace("23时59分59秒", "二十四时");
    }

    public static String format(Date date, String pattern) {
        return date != null ? new SimpleDateFormat(pattern).format(date) : null;
    }

    public static Date parseYMDHMS_NO_SPLIT(String timeDate) {
        return parse(timeDate, DATE_PATTERN_LONG_NO_SPLIT);
    }

    public static Date parseYMD_NO_SPLIT(String timeDate) {
        return parse(timeDate, DATE_PATTERN_SHORT_NO_SPLIT);
    }

    public static Date parseYMDHMS_HR(String timeDate) {
        return parse(timeDate, DATE_PATTERN_YMDHMS_HR);
    }

    public static Date parseYMD_HR(String timeDate) {
        return parse(timeDate, DATE_PATTERN_YMD_HR);
    }

    public static Date parse(String timeDate, String pattern) {
        if (StringUtils.isBlank(timeDate)) {
            return null;
        }
        SimpleDateFormat formate = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = formate.parse(timeDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

//
//    public static String parse2String(Date date, String format) {
//        if (null == date || StringUtils.isBlank(format)) {
//            return null;
//        }
//        SimpleDateFormat formate = new SimpleDateFormat(format);
//        String dateStr = formate.format(date);
//        return dateStr;
//    }
//
//    public static Date parse2DateShortPatten(String timeDate) {
//        if (StringUtils.isBlank(timeDate)) {
//            return null;
//        }
//        SimpleDateFormat formate = new SimpleDateFormat(DATE_PATTERN_SHORT);
//        Date date;
//        try {
//            date = formate.parse(timeDate);
//            return date;
//        } catch (ParseException e) {
//            return null;
//        }
//    }
//
//    public static Date parse2DateMonthPatten(String timeDate) {
//        if (StringUtils.isBlank(timeDate)) {
//            return null;
//        }
//        SimpleDateFormat formate = new SimpleDateFormat(DATE_PATTERN_MONTH);
//        Date date;
//        try {
//            date = formate.parse(timeDate);
//            return date;
//        } catch (ParseException e) {
//            return null;
//        }
//    }
//
//    public static Date addSeconds(Date source, int seconds) {
//        return addDate(source, Calendar.SECOND, seconds);
//    }
//
//    public static Date addMinutes(Date source, int minutes) {
//        return addDate(source, Calendar.MINUTE, minutes);
//    }
//
//    public static Date addDays(Date source, int days) {
//        return addDate(source, Calendar.DAY_OF_MONTH, days);
//    }
//
//    public static Date addHours(Date date, int hours) {
//        return org.apache.commons.lang.time.DateUtils.addHours(date, hours);
//    }
//
//    public static Date addMonths(Date source, int months) {
//        return addDate(source, Calendar.MONTH, months);
//    }
//
//    public static Date addYears(Date source, int years) {
//        return addDate(source, Calendar.YEAR, years);
//    }
//
//    public static int calMonthDiff(Date beginDate, Date endDate) {
//
//        Calendar beginCal = Calendar.getInstance();
//        beginCal.setTime(beginDate);
//
//        Calendar endCal = Calendar.getInstance();
//        endCal.setTime(endDate);
//
//        int yearDiff = endCal.get(Calendar.YEAR) - beginCal.get(Calendar.YEAR);
//        int monthDiff = 12 * yearDiff + endCal.get(Calendar.MONTH) - beginCal.get(Calendar.MONTH);
//
//        return monthDiff;
//    }
//
//    public static boolean isLastDayOfMonth(Date date) {
//        Calendar cald = Calendar.getInstance();
//        cald.setTime(date);
//        int today = cald.get(Calendar.DAY_OF_MONTH);
//        int lastDayOfMonth = cald.getActualMaximum(Calendar.DAY_OF_MONTH);
//        return today == lastDayOfMonth;
//
//    }
//
//    public static boolean isFirstDayOfMonth(Date date) {
//        Calendar cald = Calendar.getInstance();
//        cald.setTime(date);
//        int today = cald.get(Calendar.DAY_OF_MONTH);
//        return today == 1;
//
//    }
//
//    /**
//     * 指定日期是否为周日
//     * @param date
//     * @return
//     */
//    public static boolean isSundayOfWeek(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int today = calendar.get(Calendar.SUNDAY);
//        return today == Calendar.SUNDAY;
//    }
//
//    /**
//     * 指定日期是否为周六
//     * @param date
//     * @return
//     */
//    public static boolean isSaturdayOfWeek(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int today = calendar.get(Calendar.SATURDAY);
//        return today == Calendar.SATURDAY;
//    }
//
//    /**
//     * 指定日期是否为双休日
//     * @param date
//     * @return
//     */
//    public static boolean is2Weekend(Date date) {
//        if (isSundayOfWeek(date))
//            return true;
//        else
//            return isSaturdayOfWeek(date);
//    }
//
//    /**
//     * 判断字符是否为有效日期
//     * @param value
//     * @param format
//     * @return
//     */
//    public static boolean isDateValid(String value, String format) {
//        SimpleDateFormat sdf = null;
//        ParsePosition pos = new ParsePosition(0);//指定从所传字符串的首位开始解析
//        try {
//            sdf = new SimpleDateFormat(format);
//            sdf.setLenient(false);
//            Date date = sdf.parse(value, pos);
//            if (date == null) {
//                return false;
//            } else {
//                //严谨日期校验,如2011-03-024/2011-03-33认为不合法
//                return pos.getIndex() <= sdf.format(date).length();
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * ֻ�Ƚ�ʱ���������ڡ� ��2010-01-01 12:00:00 �� 2010-01-02
//     * 11:00:00�ȽϷ���true����Ϊ12���11���?
//     *
//     * @param curDate
//     * @param baseDate
//     * @return
//     */
//    public static boolean laterThanBaseIgnoreDate(Date curDate, Date baseDate) {
//        long millis1 = curDate.getTime() + FIRST_DAY_OFFSET_MILSEC;
//        long millis2 = baseDate.getTime() + FIRST_DAY_OFFSET_MILSEC;
//        long curTime = millis1 % ONE_DAY_MILSEC;
//        long baseTime = millis2 % ONE_DAY_MILSEC;
//
//        return curTime > baseTime;
//    }
//
//    public static Date getDate(int year, int month, int date) {
//
//        if (month < 1 || month > 12) {
//            return null;
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month - 1, date, 0, 0, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        return calendar.getTime();
//    }
//
//    public static short getDayOfMonth(Date date) {
//        if (date == null) {
//            throw new IllegalArgumentException(INVALID_PARAM_MSG);
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//
//        return (short) c.get(Calendar.DAY_OF_MONTH);
//    }
//
//    private static Date addDate(Date date, int calendarField, int amount) {
//        if (date == null) {
//            throw new IllegalArgumentException(INVALID_PARAM_MSG);
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(calendarField, amount);
//        return c.getTime();
//    }
//
//    public static int getDaysInMonth(Date date) {
//
//        if (date == null) {
//            throw new IllegalArgumentException(INVALID_PARAM_MSG);
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
//    }
//
//    public static String getDatesStrInMonth(Date date) {
//        if (date == null) {
//            throw new IllegalArgumentException(INVALID_PARAM_MSG);
//        }
//        String dateStr = DateTimeUtil.getYMD(date);
//        return StringUtils.substring(dateStr, DATE_START_INDEX_OF_DEFAULT_PATTERN);
//
//    }
//
//    public static boolean isBetween(Date date, Date start, Date end) {
//        return countLoanTerm(start, date) > 0 && countLoanTerm(date, end) >= 0;
//    }
//
//    public static Date getLastDate(Date... dates) {
//        List<Date> realDateList = new ArrayList<Date>();
//        for (Date date : dates) {
//            if (null != date) {
//                realDateList.add(date);
//            }
//        }
//
//        Date maxDate = null;
//        if (realDateList.size() != 0) {
//            maxDate = realDateList.get(0);
//            for (Date realDate : realDateList) {
//                if (realDate.after(maxDate)) {
//                    maxDate = realDate;
//                }
//            }
//        }
//        return maxDate;
//    }
//
//    public static Date getMin(Date date1, Date date2) {
//        return countLoanTerm(date1, date2) >= 0 ? date1 : date2;
//    }
//
//    public static Date getMax(Date date1, Date date2) {
//        return countLoanTerm(date1, date2) >= 0 ? date2 : date1;
//    }
//
//    public static Date getYesterday() {
//        Date yesterday = DateTimeUtil.addDays(new Date(), -1);
//        return DateTimeUtil.getStandardDate(yesterday);
//    }
//
//    /**
//     * 功能：取当前服务器时间并以参数指定的格式返回
//     * @param formatStr
//     * @return
//     */
//    public static String getCurrentDateByFormat(String formatStr) {
//        long currentTime = System.currentTimeMillis();
//        java.util.Date date = new java.util.Date(currentTime);
//        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
//        return formatter.format(date);
//    }
//
//    /**
//     * 转化String类型的格式为日期类型
//     * @param date
//     * @return
//     */
//    public static Date parseString(String date) {
//        try {
//            return org.apache.commons.lang.time.DateUtils.parseDate(date, new String[] { "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd",
//                    "yy-MM-dd HH:mm:ss", "yy/MM/dd hh:mm:ss", "yyyyMMdd" });
//        } catch (Exception e) {
//            log.error("DateTimeUtil.parseString异常：", e);
//        }
//        return null;
//    }
//
//    /**
//     *
//     * 判断日期是否过期, 日期格式必须符合parseString
//     *
//     * @param dateTime
//     * @return 大于期望日期，返回true；小于等于期望日期，返回false
//     * @throws Exception
//     */
//    public static boolean isDateTimeExpired(String dateTime) throws Exception {
//        Date date = new Date();
//        Date comp = parseString(dateTime);
//        return date.compareTo(comp) < 1;
//    }
//
//    /**
//     *
//     * 判断日期是否过期, 日期格式必须符合parseString
//     *
//     * @param dateTime
//     * @return 大于期望日期，返回true；小于等于期望日期，返回false
//     * @throws Exception
//     */
//    public static boolean isDateTimeExpired(Date comp) throws Exception {
//        Date date = new Date();
//        return date.compareTo(comp) < 1;
//    }
//
//    /**
//     * 判断两个日期是否是相隔制定的天数，只判断年月日，不关心时分秒
//     * @param greaterDate 较大的日期
//     * @param lessDate 较小的日期
//     * @param days 间隔天数
//     * @return
//     */
//    public static boolean isTwoDateSomeDaysGap(Date greaterDate, Date lessDate, int days) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(greaterDate);
//        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(lessDate);
//
//        return calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
//               && calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH);
//    }
//
//    /**
//     * 根据生日和传入的日期计算年龄
//     *
//     * @param birthDay
//     * @param date
//     * @return
//     */
//    public static int getAge(Date birthDay) {
//        int iAge = 0;
//        Calendar cal = Calendar.getInstance();
//
//        int iCurrYear = cal.get(Calendar.YEAR);
//        int sCurrMonth = cal.get(Calendar.MONTH);
//        int sCurrDay = cal.get(Calendar.DATE);
//        cal.setTime(birthDay);
//        int iBirthYear = cal.get(Calendar.YEAR);
//        int sBirthMonth = cal.get(Calendar.MONTH);
//        int sBirthDay = cal.get(Calendar.DATE);
//        iAge = iCurrYear - iBirthYear;
//
//        if (sCurrMonth < sBirthMonth) {
//            iAge = iAge - 1;
//        } else if (sCurrMonth == sBirthMonth) {
//            if (sCurrDay < sBirthDay) {
//                iAge = iAge - 1;
//            }
//        }
//        return iAge;
//    }
//
//    /**
//     * 计算两个时间之间相差的天数，注意该方法只适用于两个日期是同一年的情况，跨年场景不满足。
//     * @param greaterDate 较大的日期
//     * @param lessDate 较小的日期
//     * @return
//     */
//    public static int calDaysOffset(Date greaterDate, Date lessDate) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(greaterDate);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(lessDate);
//        return calendar.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR);
//    }
//
//    /**
//     * 获取指定日期起始时间
//     *
//     * @param date
//     * @return
//     */
//    public static Date getStartTime(Date date){
//        Calendar todayStart = Calendar.getInstance();
//        todayStart.setTime(date);
//        todayStart.set(Calendar.HOUR_OF_DAY, 0);
//        todayStart.set(Calendar.MINUTE, 0);
//        todayStart.set(Calendar.SECOND, 0);
//        todayStart.set(Calendar.MILLISECOND, 0);
//        return todayStart.getTime();
//    }
//
//    /**
//     * 获取指定日期结束时间
//     *
//     * @param date
//     * @return
//     */
//    public static Date getEndTime(Date date){
//        Calendar todayEnd = Calendar.getInstance();
//        todayEnd.setTime(date);
//        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
//        todayEnd.set(Calendar.MINUTE, 59);
//        todayEnd.set(Calendar.SECOND, 59);
//        todayEnd.set(Calendar.MILLISECOND, 999);
//        return todayEnd.getTime();
//    }
//
//    /**
//     * 把毫秒转化成日期
//     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
//     * @param millSec(毫秒数)
//     * @return
//     */
//    @SuppressWarnings("unused")
//    private String transferLongToDate(String dateFormat, Long millSec){
//    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_LONG);
//    	Date date= new Date(millSec);
//    	return sdf.format(date);
//    }
//
//    /**
//     * 获取指定日期起始时间
//     *
//     * @param date
//     * @return
//     */
//    public static Date getLastTenClock(Date date){
//        Calendar todayStart = Calendar.getInstance();
//        todayStart.setTime(date);
//        if(todayStart.get(Calendar.HOUR_OF_DAY) < 10){
//            todayStart.set(Calendar.DAY_OF_MONTH, todayStart.get(Calendar.DAY_OF_MONTH) - 1);
//        }
//        todayStart.set(Calendar.HOUR_OF_DAY, 10);
//        todayStart.set(Calendar.MINUTE, 0);
//        todayStart.set(Calendar.SECOND, 0);
//        todayStart.set(Calendar.MILLISECOND, 0);
//        return todayStart.getTime();
//    }
//
//    /**
//     * 获取指定日期结束时间
//     *
//     * @param date
//     * @return
//     */
//    public static Date getNextTenClock(Date date){
//        Calendar todayEnd = Calendar.getInstance();
//        todayEnd.setTime(date);
//        if(todayEnd.get(Calendar.HOUR_OF_DAY) >= 10){
//            todayEnd.set(Calendar.DAY_OF_MONTH, todayEnd.get(Calendar.DAY_OF_MONTH) + 1);
//        }
//        todayEnd.set(Calendar.HOUR_OF_DAY, 10);
//        todayEnd.set(Calendar.MINUTE, 0);
//        todayEnd.set(Calendar.SECOND, 0);
//        todayEnd.set(Calendar.MILLISECOND, 0);
//        return todayEnd.getTime();
//    }
    
}
