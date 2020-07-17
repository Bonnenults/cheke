package com.autozi.common.utils.util2;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: DateUtils
 * @Description: 日期辅助类
 * @author 刘佑润 yourun.liu@b2bex.com
 * @date 2011-6-26 下午06:39:10
 *
 */
public class DateUtils {
	private DateUtils() {
	}

	private static Logger log = Logger.getLogger(DateUtils.class);

	/**
	 * 缺省的日期格式
	 */
	private static final String DAFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final String DAFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm";

	/**
	 * 默认日期类型格试.
	 *
	 * @see
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DAFAULT_DATE_FORMAT);

	/**
	 * 缺省的日期时间格式
	 */
	private static final String DAFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式
	 */
	private static String DATETIME_FORMAT = DAFAULT_DATETIME_FORMAT;

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_FORMAT);

	/**
	 * 缺省的时间格式
	 */
	private static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 时间格式
	 */
	private static String TIME_FORMAT = DAFAULT_TIME_FORMAT;

	private static SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

	/**
	 * 获取格式化实例.
	 *
	 * @param pattern
	 *            如果为空使用DAFAULT_DATE_FORMAT
	 * @return
	 */
	public static SimpleDateFormat getFormatInstance(String pattern) {
		if (pattern == null || pattern.length() == 0) {
			pattern = DAFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 格式化Calendar
	 *
	 * @param calendar
	 * @return
	 */
	public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		return dateFormat.format(calendar.getTime());
	}

	public static String formatDateTime(Date d) {
		if (d == null) {
			return "";
		}
		return datetimeFormat.format(d);
	}

	public static String formatDate(Date d) {
		if (d == null) {
			return "";
		}
		return dateFormat.format(d);
	}

	/**
	 * 格式化时间
	 *
	 * @param d
	 * @return
	 */
	public static String formatTime(Date d) {
		if (d == null) {
			return "";
		}
		return timeFormat.format(d);
	}

	/**
	 * 格式化整数型日期
	 *
	 * @param intDate
	 * @return
	 */
	public static String formatIntDate(Integer intDate) {
		if (intDate == null) {
			return "";
		}
		Calendar c = newCalendar(intDate);
		return formatCalendar(c);
	}

	/**
	 * 根据指定格式化来格式日期.
	 *
	 * @param date
	 *            待格式化的日期.
	 * @param pattern
	 *            格式化样式或分格,如yyMMddHHmmss
	 * @return 字符串型日期.
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(pattern)) {
			return formatDate(date);
		}
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			return formatDate(date);
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 取得Integer型的当前日期
	 *
	 * @return
	 */
	public static Integer getIntNow() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前日期
	 *
	 * @return
	 */
	public static Integer getIntToday() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前年份
	 *
	 * @return
	 */
	public static Integer getIntYearNow() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得Integer型的当前月份
	 *
	 * @return
	 */
	public static Integer getIntMonthNow() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	public static String getStringToday() {
		return getIntDate(getNow()) + "";
	}

    public static void main(String[] args) {
        System.out.println(getFistDayOfNow(new Date()));
    }
	/**
	 * 根据年月日获取整型日期
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntDate(int year, int month, int day) {
		return getIntDate(newCalendar(year, month, day));
	}

	/**
	 * 某年月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfMonth(int year, int month) {
		return getIntDate(newCalendar(year, month, 1));
	}
	/**
	 * 某年月的第一天
	 *
	 * @return
	 */
	public static Integer getFirstDayOfThisMonth() {
		Integer year = DateUtils.getIntYearNow();
		Integer month = DateUtils.getIntMonthNow();
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的第一天
	 *
	 * @param date
	 * @return
	 * @time:8-7-4 上午09:58:55
	 */
	public static Integer getFistDayOfMonth(Date date) {
		Integer intDate = getIntDate(date);
		int year = intDate / 10000;
		int month = intDate % 10000 / 100;
		return getIntDate(newCalendar(year, month, 1));
	}
	/**
	 * 某年月的第一天
	 *
	 * @param date
	 * @return Date
	 * @time:8-7-4 上午09:58:55
	 */
	public static Date getFistDayOfNow(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		//int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(year,month,1);
		return c.getTime();
	}

	/**
	 * 某年月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(int year, int month) {
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}
	  public static String getLastDayOfMonth2(int year,int month)
	    {
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String lastDayOfMonth = sdf.format(cal.getTime());

	        return lastDayOfMonth;
	    }

	/**
	 * 根据Calendar获取整型年份
	 *
	 * @param c
	 * @return
	 */
	public static Integer getIntYear(Calendar c) {
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 根据Calendar获取整型日期
	 *
	 * @param c
	 * @return
	 */
	public static Integer getIntDate(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year * 00 + month * +day;
	}

	/**
	 * 根据Date获取整型年份
	 *
	 * @param d
	 * @return
	 */
	public static Integer getIntYear(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntYear(c);
	}

	/**
	 * 根据Date获取整型日期
	 *
	 * @param d
	 * @return
	 */
	public static Integer getIntDate(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntDate(c);
	}

	/**
	 * 根据Integer获取Date日期
	 *
	 * @param n
	 * @return
	 */
	public static Date getDate(Integer n) {
		if (n == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(n / 10000, n / 100 % 100 - 1, n % 100);
		return c.getTime();
	}

	public static Date getDate(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(DAFAULT_DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			log.error("解析[" + date + "]错误！", e);
			return null;
		}
	}

	public static Date getDateTime(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(DAFAULT_DATE_TIME_FORMAT).parse(date);
		} catch (ParseException e) {
			log.error("解析[" + date + "]错误！", e);
			return null;
		}
	}
	public static Date getDateTimeFull(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(DAFAULT_DATETIME_FORMAT).parse(date);
		} catch (ParseException e) {
			log.error("解析[" + date + "]错误！", e);
			return null;
		}
	}

	/**
	 * 根据年份Integer获取Date日期
	 *
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(Integer year) {
		if (year == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(year, 1, 1);
		return c.getTime();
	}

	/**
	 * 根据年月日生成Calendar
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar newCalendar(int year, int month, int day) {
        Calendar ret = Calendar.getInstance();
        if (year < 100)
        {
            year = 2000 + year;
        }
        ret.set(year, month - 1, day);
        return ret;
    }

	/**
	 * 根据整型日期生成Calendar
	 *
	 * @param date
	 * @return
	 */
	public static Calendar newCalendar(int date) {
        int year = date / 10000;
        int month = (date %10000) / 100;
        int day = date % 100;

        Calendar ret = Calendar.getInstance();
        ret.set(year, month - 1, day);
        return ret;
    }




	/**
	 * 取得Date型的当前日期
	 *
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 取得Date型的当前日期
	 *
	 * @return
	 */
	public static Date getToday() {
		return DateUtils.getDate(DateUtils.getIntToday());
	}

	/**
	 * 整数型日期的加法
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateAdd(int date, int days) {
        int year = date / 10000;
        int month = (date % 10000) / 100;
        int day = date % 100;

        day += days;

        return getIntDate(year, month, day);
    }

	/**
	 * 整数型日期的减法
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateSub(int date, int days) {
		return intDateAdd(date, -days);
	}

	/**
	 * 计算两个整型日期之间的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Integer startDate, Integer endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Calendar c1 = newCalendar(startDate);
		Calendar c2 = newCalendar(endDate);

		Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000 / 60 / 60 / 24;
		return lg.intValue();
	}
	/**
	 * 两天之间的间隔天数
	 */
	public static int dayDiff(Date date1, Date date2) {
		long diffMillseconds=date1.getTime()-date2.getTime();
		int diffDay=(int)(diffMillseconds/1000/60/60/24);
		return diffDay;
	}
	/**
	 * 两个时间之间的间隔时长
	 */
	public static int timeDiff(Date date1, Date date2) {
		long diffMillseconds=date1.getTime()-date2.getTime();
		int diffTime=(int)(diffMillseconds/1000/60);
		return diffTime;
	}
	/**
	 * 两个小时之间的间隔时长
	 */
	public static int hourDiff(Date date1, Date date2) {
		long diffMillseconds=date1.getTime()-date2.getTime();
		int diffTime=(int)(diffMillseconds/1000/60/60);
		return diffTime;
	}

	/**
	 * 计算两个整型日期之间的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Boolean compareDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		try {
			startDate = getFormatInstance(DAFAULT_DATE_FORMAT).parse(formatDate(startDate));
			endDate = getFormatInstance(DAFAULT_DATE_FORMAT).parse(formatDate(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate.before(endDate);
	}

	/**
	 * 取得当前日期.
	 *
	 * @return 当前日期,字符串类型.
	 */
	public static String getStringDate() {
		return getStringDate(DateUtils.getNow());
	}

	/**
	 * 根据calendar产生字符串型日期
	 *
	 * @param d
	 * @return eg:07
	 */
	public static String getStringDate(Date d) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}

	public static String getFormatStringDate(Date d) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(d);
	}

	/**
	 * 计算 day 天后的时间
	 *
	 * @param date
	 * @param day
	 * @author lihf
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	@SuppressWarnings("deprecation")
	public static Map<String, Object> queryMapUtil(Map<String, Object> queryMap){
		for(Map.Entry<String, Object> item : queryMap.entrySet()){
			if(item.getKey().endsWith("Time")){
				Date date = (Date) item.getValue();
				 if (item.getKey().toLowerCase().contains("end")) {
					    date.setDate(date.getDate() - 1);
				 }
				queryMap.put(item.getKey(), new SimpleDateFormat("yyyy-MM-dd").format(item.getValue()));
			}
		}
		return queryMap;
	}

	/**
     * 同一天日期比较 B2B AND B2C
     * @param maps
     */
	public static Map<String, Object> String2Date(Map<String, Object> maps){
    	Map<String, Object> newMaps  = new HashMap<String, Object>();
    	for (Map.Entry<String, Object> item : maps.entrySet()) {
    		String key = item.getKey().toLowerCase();
    		if(key.contains("end") && key.contains("time")){
    			String endTime = item.getValue().toString() + " 23:59:59";
					newMaps.put(item.getKey(),endTime);
    		}else if(key.contains("start") && key.contains("time")){
    			String startTime = item.getValue().toString() + " 00:00:00";
					newMaps.put(item.getKey(), startTime);
    		}else{
    			newMaps.put(item.getKey(), item.getValue());
    		}

		}
    	return newMaps;
    }
	/**
	 * 获取前一天或昨天
	 * @return
	 */
	public static Date getYesterday(){

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		return cal.getTime();
	}

	/**
	 *
	 * @param mon
	 * @return
	 *        相减后的date
	 */
	public static String beginMonth(int mon,Date date){
		if(mon<=0|| mon>12){
			throw new RuntimeException("输入的月份有误");
		}
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTime(date);
		calendarNow.set(Calendar.MONTH, calendarNow.get(Calendar.MONTH) - mon);
		return dateFormat.format(calendarNow.getTime());
	}
	/**
	 * 得到指定日期的前面指定天数的日期
	 * @param count
	 * @param time
	 * @return
	 */
	public static Date getBeforeDate(int count, Date time) {
		GregorianCalendar g = new GregorianCalendar();
		if(time == null) {
			time = new Date();
		}
		g.setTime(time);
		if(count == 0) {
			count = -15;
		} else {
			count = 0 -count;
		}
		//日期的DATE减去10  就是往前推10 天 同理 +10 就是往后推十天
        g.add(Calendar.DATE, count);
        return g.getTime();
	}

	/**
	 * 日期转换
	 */
	public static String format(Date date) {
		if (date == null) {
			return "";
		}
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 获取当天零点时间
	 *
	 * @return
	 */
	public static Date getTodayBegin() throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00 E");
		String dateStr = simpleDateFormat.format(new Date());
		Date date = simpleDateFormat.parse(dateStr);
		return date;
	}


    /**
     * 计算两个日期之间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1,Date date2)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**

     *@param date 是为则默认今天日期、可自行设置“2013-06-03”格式的日期

     *@return  返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六

     */
    public static int getDayofweek(String date){
        Calendar cal = Calendar.getInstance();
        //   cal.setTime(new Date(System.currentTimeMillis()));
        if (date.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(getDateByStr2(date).getTime()));
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    private static Date getDateByStr2(String dd) {

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sd.parse(dd);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转换成时间格式
     * @param mill
     * @return
     */
    public static String formatTimestamp(int mill){
        Date date=new Date(mill*1000L);
        String str="";
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DAFAULT_DATE_TIME_FORMAT);
            str=sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
    
    /**
     * 使用预设格式将字符串转为Date 
     * @author shixin.zhang
     * Created on：2017年6月9日
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException  
    {  
        return StringUtils.isBlank(strDate) ? null : parse(strDate,  
        		DAFAULT_DATETIME_FORMAT);  
    } 
    
    /**
     * 使用参数Format将字符串转为Date 
     * @author shixin.zhang
     * Created on：2017年6月9日
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate, String pattern)  
            throws ParseException  
    {  
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
                pattern).parse(strDate);  
    }  

    /**
     * @Description: 计算两个日期间的小时数
     * @author: zhiyun.chen
     * 2017-8-14上午09:31:27
     */
    public static int hoursBetween(Date date1,Date date2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_hours=(time2-time1)/(1000*3600);
        return Integer.parseInt(String.valueOf(between_hours));
    }

}
