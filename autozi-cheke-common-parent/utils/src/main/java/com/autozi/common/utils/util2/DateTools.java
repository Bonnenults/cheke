package com.autozi.common.utils.util2;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 日期辅助类
 * @Description:
 * @version     V1.0
 */
public class DateTools {
	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond
	/**
	 * 要用到的DATE Format的定义
	 */
	public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // 年/月/日
	public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年/月/日
	public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateTools.DATE_FORMAT_DATETIME);
	// Global SimpleDateFormat object
	public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateTools.DATE_FORMAT_DATEONLY);
	public static final SimpleDateFormat SHORTDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	// ADD BY lihaifeng@UMP: START 2010-12-29 下午02:20:22  BUG号:添加yyyy-MM类型的格式
	public static final SimpleDateFormat SHORT_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
	// ADD BY lihaifeng@UMP: END 2010-12-29 下午02:20:22 BUG号:
	public static final SimpleDateFormat YM_DATE = new SimpleDateFormat("yyyyMM");
	public static final SimpleDateFormat YYYYMM_DATE = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	public static final String DATE_TIME = new String("yyMMddHHmmss");
	public static final String FULL_DATE_TIME = new String("yyyyMMddHHmmss");
	public static final SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat(FULL_DATE_TIME);
	// ---START--- Nov 18, 2010 何金云 Add{新的日期或时间格式}
	public static final String SHORT_TIME = new String("HH:mm");
	public static final SimpleDateFormat ROLL_DATE = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat FULL_DATE = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final String FORMAT_DATE_ZN_CH1 = "MM月dd日HH时mm分";
	public static final SimpleDateFormat SHORT_DATE_FORMAT_ZN_CH1 = new SimpleDateFormat(FORMAT_DATE_ZN_CH1);
	public static final String FORMAT_DATE_ZN_CH = "yyyy年MM月dd日";
	public static final SimpleDateFormat SHORT_DATE_FORMAT_ZN_CH = new SimpleDateFormat(FORMAT_DATE_ZN_CH);
	public static final String FORMAT_DATE_LONG = "yyyy/MM/dd HH:mm";
	public static final SimpleDateFormat LONG_DATE_FORMAT_SET = new SimpleDateFormat(FORMAT_DATE_LONG);
	public static final SimpleDateFormat YYMMDD = new SimpleDateFormat("yyMMdd");
	public static final SimpleDateFormat MM_DD_HH_MM = new SimpleDateFormat("MM-dd HH:mm");
	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
	// ---END---

	// ---END---
		public static Date dateFormat(String dateStr, String dateFormat){
			Date formatDate = null;
			try {
				formatDate = new SimpleDateFormat(dateFormat).parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return formatDate;
		}

	public static Date parseDate(String date,String hour){
		Date return_date = Calendar.getInstance().getTime();
		try{
			return_date = LONG_DATE_FORMAT.parse(date+" "+hour);
		}catch(Exception e){
			e.printStackTrace();
		}
		return return_date;
	}
	/**
	 * 获得当前的日期毫秒
	 * @return
	 */
	public static long nowTimeMillis(){
		return System.currentTimeMillis();
	}
	/**
	 * 获得当前的时间戳
	 * @return
	 */
	public static Timestamp nowTimeStamp() {
		return new Timestamp(nowTimeMillis());
	}
	/**
	 * yyyyMMdd 当前日期
	 * 
	 */
	public static String getReqDate() {
		return SHORTDATEFORMAT.format(new Date());
	}
	/**
	 * HHmmss 当前时间
	 * 
	 */
	public static String getCurrrentReqTime(){
		return TIME_FORMAT.format(new Date());
	}
	/**
	 * yyyy-MM-dd 传入日期
	 * @param date
	 * @return
	 */
	public static String getReqDate(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}
	/**
	 * yyyy-MM-dd  传入的时间戳
	 * @param date
	 * @return
	 */
	public static String TimestampToDateStr(Timestamp tmp) {
		return SHORT_DATE_FORMAT.format(tmp);
	}
	// ADD BY lihaifeng@UMP: START 2010-12-29 下午02:21:33  BUG号:
	public static String getShortMonthStr(Timestamp tmp){
		return SHORT_MONTH_FORMAT.format(tmp);
	}
	// ADD BY lihaifeng@UMP: END 2010-12-29 下午02:21:33 BUG号:
	
	/**
	 * HH:mm:ss 当前时间
	 * @return
	 */
	public static String getReqTime() {
		return HMS_FORMAT.format(new Date());
	}
	/**
	 * 得到时间戳格式字串 
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStampStr(Date date) {
		return LONG_DATE_FORMAT.format(date);
	}
	/**
	 * 得到长日期格式字串
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getLongDateStr() {
		return LONG_DATE_FORMAT.format(new Date());
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String getLongDateStr(Timestamp time) {
		return LONG_DATE_FORMAT.format(time);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getLongDateStr(Date date) {
		return LONG_DATE_FORMAT.format(date);
	}
	/**
	 * 得到短日期格式字串
	 * yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String getShortDateStr(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public static String getShortDateStr() {
		return SHORT_DATE_FORMAT.format(new Date());
	}
	/**
	 * 计算 minute 分钟后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	/**
	 * 计算 hour 小时后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}
	/**
	 * 得到day的起始时间点。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 得到day的终止时间点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	/**
	 * 计算 day 天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	/**
	 * 	
	 * <PRE>	
	 * 	
	 * 方法描述: 计算几个月后的日期
	 * 
	 * </PRE>
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	
	/**
	 * 得到month的起始时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 得到month的终止时间点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取本周的起点日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekStart(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
	
	/**
	 * 获取本周终点日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_WEEK, 1);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    calendar.add(Calendar.WEEK_OF_MONTH, 1);
	    calendar.add(Calendar.MILLISECOND, -1);
	    return calendar.getTime();
	}
	
	/**
	 * 	
	 * <PRE>	
	 * 	
	 * 方法描述: 年份累加
	 * 
	 * </PRE>
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 365*year);
		return calendar.getTime();
	}
	
	/**
	 * 	
	 * <PRE>	
	 * 	
	 * 方法描述: 获取指定日期的时间
	 * 如：传入25，则返回本月的25日
	 * 
	 * </PRE>
	 * @param day
	 * @return
	 */
	public static Date getAppointDate(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return getDayEnd(calendar.getTime());
	}
	
	public static Timestamp strToTimestamp(String dateStr){
		return Timestamp.valueOf(dateStr);
	}
	public static Timestamp strToTimestamp(Date date){
        return Timestamp.valueOf(formatTimestamp.format(date)); 
	}
	public static Timestamp getCurTimestamp(){
        return Timestamp.valueOf(formatTimestamp.format(new Date())); 
	}

	/**
	 * 取得两个日期之间的日数
	 * 
	 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	 */
	public static long daysBetween(Timestamp t1, Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}
	/**
	 * 返回java.sql.Timestamp型的SYSDATE
	 *
	 * @return java.sql.Timestamp型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getSysDateTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
	 *
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateTools.DATE_FORMAT_DATEONLY.length()) {
			return null;
		}
		return toSqlTimestamp(sDate, DateTools.DATE_FORMAT_DATEONLY);
	}
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
	 *
	 * @param sDate
	 *            Date string
	 * @param sFmt
	 *            Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		System.out.println("sDate = " + sDate);
		System.out.println("sFmt  = " + sFmt);
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateTools.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateTools.DATE_FORMAT_DATEONLY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION )){
			// //Format: 200009301230
			// temp =
			// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
			// temp += " " + sDate.substring(8,10) + ":" +
			// sDate.substring(10,12) + ":00.000000000";
		} else {
			return null;
		}
		System.out.println("Temp = " + temp);
		// java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
		return Timestamp.valueOf(temp);
	}
	/**
	 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
	 *
	 * @return 系统日期时间
	 * @since 1.0
	 * @history
	 */
	public static String getSysDateTimeString() {
		return toString(new Date(System.currentTimeMillis()),
				DateTools.sdfDateTime);
	}
	/**
	 * 根据指定的Format转化java.util.Date到String
	 *
	 * @param dt
	 *            java.util.Date instance
	 * @param sFmt
	 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(Date dt, String sFmt) {
		// add by SJNS/zq 03/16
		if (dt == null) {
			return "";
		}
		if (sFmt.equals(DateTools.DATE_FORMAT_DATETIME)) { // "YYYY/MM/DD
			// HH24:MI:SS"
			return toString(dt, DateTools.sdfDateTime);
		} else { // Default , YYYY/MM/DD
			return toString(dt, DateTools.sdfDateOnly);
		}
	}

	/**
	 * 利用指定SimpleDateFormat instance转换java.util.Date到String
	 *
	 * @param dt
	 *            java.util.Date instance
	 * @param formatter
	 *            SimpleDateFormat Instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	private static String toString(Date dt, SimpleDateFormat formatter) {
		String sRet = null;

		try {
			sRet = formatter.format(dt).toString();
		} catch (Exception e) {
			e.printStackTrace();
			sRet = null;
		}

		return sRet;
	}
	/**
	 * 转换java.sql.Timestamp到String，格式为YYYY-MM-DD HH24:MI
	 *
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString2(Timestamp dt) {
		if (dt == null) {
			return null;
		}
		String temp = toSqlTimestampString(dt, DateTools.DATE_FORMAT_DATETIME);
		return temp.substring(0, 16);
	}
	public static String toString(Timestamp dt) {
		return dt == null ? "" : toSqlTimestampString2(dt);
	}
	/**
	 * 根据指定的格式转换java.sql.Timestamp到String
	 *
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @param sFmt
	 *            Date
	 *            格式，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/DATE_FORMAT_SESSION
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString(Timestamp dt, String sFmt) {
		String temp = null;
		String out = null;
		if (dt == null || sFmt == null) {
			return null;
		}
		temp = dt.toString();
		if (sFmt.equals(DateTools.DATE_FORMAT_DATETIME) || // "YYYY/MM/DD
				// HH24:MI:SS"
				sFmt.equals(DateTools.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			temp = temp.substring(0, sFmt.length());
			out = temp.replace('/', '-');
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION ) ){
			// //Session
			// out =
			// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
			// out += temp.substring(12,14) + temp.substring(15,17);
		}
		return out;
	}

	//得到当前日期的星期
	public static int getWeek(){
	    Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK);
		return w;
	}

	// 得到当前日期时间
	// 当前时间日期格式:yyMMddHHmmss
	public static String getDateAndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateTools.DATE_TIME);
		return sdf.format(new Date());
	}

	// 得到当前日期时间
	// 当前日期时间格式:yyyyMMddHHmmss
	public static String getFullDateAndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateTools.FULL_DATE_TIME);
		return sdf.format(new Date());
	}

	// 得到今天回滚n天的日期
	// 回滚n天日期时间格式:yyyy-MM-dd
	// if amount > 0 日期>今天日期
	// if amount < 0 日期<今天日期
	public static String getRollCurrentDate(int amount){
		Calendar date = Calendar.getInstance();

		date.add(Calendar.DAY_OF_MONTH, amount);
		Date currentDate = date.getTime();

		return SHORT_DATE_FORMAT.format(currentDate);
	}

	// ADD BY lihaifeng@UMP: START 2010-10-17 上午09:31:06  BUG号:满足不同格式日期的需求
	/**
	 *  得到今天回滚n天的日期
	 *	回滚n天日期时间格式:yyyy-MM-dd
	 *	if amount > 0 日期>今天日期
	 *	if amount < 0 日期<今天日期
	 *
	 * @param amount
	 * @param sf
	 * @return
	 */
	public static String getRollCurrentDate(int amount,String sf){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, amount);
		return formatDateForMore(date.getTime(), sf);
	}
	/**
	 *  得到今天回滚n天的日期
	 *	回滚n天日期时间格式:yyyy-MM-dd
	 *	if amount > 0 日期>今天日期
	 *	if amount < 0 日期<今天日期
	 *
	 * @param date
	 * @param amount
	 * @param sf
	 * @return
	 */
	public static String getRollCurrentDate(Date date,int amount,String sf){
		Date currentDate = date;
		Calendar _date = Calendar.getInstance();
		if(currentDate!=null){
			_date.setTime(currentDate);
		}
		_date.add(Calendar.DAY_OF_MONTH, amount);
		return formatDateForMore(_date.getTime(), sf);
	}
	/**
	 * 取得当前时间的前几天或者后几天
	 *
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date getRollCurrentDate(Date date,int amount){
		Calendar current = Calendar.getInstance();
		current.setTime(date);
		current.add(Calendar.DAY_OF_MONTH, amount);
		return current.getTime();
	}
	/**
	 * 格式化日期
	 *
	 * @param currentDate
	 * @param sf
	 * @return
	 */
	public static String formatDateForMore(Date currentDate,String sf){
		String formatDate = "";
		if (null != currentDate) {
			if(SHORTDATEFORMAT.toPattern().equals(sf)){
				formatDate = SHORTDATEFORMAT.format(currentDate.getTime());
			}else if(SHORT_DATE_FORMAT.toPattern().equals(sf)){
				formatDate =  SHORT_DATE_FORMAT.format(currentDate.getTime());
			}else if(LONG_DATE_FORMAT.toPattern().equals(sf)){
				formatDate =  LONG_DATE_FORMAT.format(currentDate.getTime());
			}else if(formatTimestamp.toPattern().equals(sf)){
				formatDate =  formatTimestamp.format(currentDate.getTime());
			}else if(SHORT_DATE_FORMAT_ZN_CH.toPattern().equals(sf)){
				formatDate =  SHORT_DATE_FORMAT_ZN_CH.format(currentDate.getTime());
			}else if(SHORT_DATE_FORMAT_ZN_CH1.toPattern().equals(sf)){
				formatDate =  SHORT_DATE_FORMAT_ZN_CH1.format(currentDate.getTime());
			}else if(LONG_DATE_FORMAT_SET.toPattern().equals(sf)){
				formatDate =  LONG_DATE_FORMAT_SET.format(currentDate.getTime());
			}else if(FULL_DATE_TIME_FORMAT.toPattern().equals(sf)){
				formatDate =  FULL_DATE_TIME_FORMAT.format(currentDate.getTime());
			}else if(MM_DD_HH_MM.toPattern().equals(sf)){
				formatDate =  MM_DD_HH_MM.format(currentDate.getTime());
			}
		}
		return formatDate;
	}
	/**
	 * 取得当前日期
	 * @return
	 */
	public static Date getCurrentDate(){
		Calendar date = Calendar.getInstance();
		return date.getTime();
	}
	
	/**
	 * 传入指定格式[yyyy-MM-dd]的日期，转换成Date格式
	 *
	 * @param date
	 * @return
	 */
	public static Date getDateForAppointDate(String date){
		try {
			return SHORT_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	
	/**
	 * 简单的转换 把中文日期yyyy年MM月dd转换成yyyy-MM-dd的
	 * @param dateBeginStr
	 */
	public static String getSimpleChinesefordate(String dateBeginStr) {
		String[] yeah = dateBeginStr.split("年");
		 String[] month=yeah[1].split("月");
		 String[] day=month[1].split("日");
		 return yeah[0]+"-"+month[0]+"-"+day[0];
	}
    /**
    * 传入指定格式[yyyy-MM-dd HH:mm:ss]的日期，转换成Date格式
    *
    * @param date
    * @return
    */
    public static Date strDateToLongDate(String date) {
        try {
            return LONG_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getCurrentDate();
    }
	
	/**
	 * 传入指定格式[yyyyMMdd]的日期，转换成Date格式
	 *
	 * @param date
	 * @return
	 */
	public static Date getDateFromYYYYMMDD(String date){
		try {
			return SHORTDATEFORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	
	/**
	 * 对指定日期{具有指定格式}进行处理
	 *
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date getDateForAppointDate(String date,int amount,String sf){
		try {
			Calendar c = Calendar.getInstance();
			if(sf.equals(SHORT_DATE_FORMAT.toPattern())){
				c.setTime(SHORT_DATE_FORMAT.parse(date));
			} else if(sf.equals(SHORTDATEFORMAT.toPattern())){
				c.setTime(SHORTDATEFORMAT.parse(date));
			} else if(sf.equals(FULL_DATE_TIME_FORMAT.toPattern())){
				c.setTime(FULL_DATE_TIME_FORMAT.parse(date));
			}
			c.add(Calendar.DAY_OF_MONTH, amount);
			return c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getCurrentDate();
	}
	// ADD BY lihaifeng@UMP: END 2010-10-17 上午09:31:06 BUG号:

	/**
	 * Timestamp 格式转换成yyyyMMdd
	 */
	public static String timestampToShortDate(Timestamp timestamp){
		return SHORTDATEFORMAT.format(timestamp);
	}

	// ADD BY lihaifeng@UMP: START 2010-12-13 下午02:51:09  BUG号:
	/**
	 * Timestamp 格式转换成HHmmss
	 */
	public static String timestampToShortTime(Timestamp timestamp){
		return TIME_FORMAT.format(timestamp);
	}
	// ADD BY lihaifeng@UMP: END 2010-12-13 下午02:51:09 BUG号:

	// ---START--- Nov 18, 2010 何金云 Add{新的日期或时间格式}
	/**
	 * 时间格式:HH:MM
	 */
	public static String getShortTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DateTools.SHORT_TIME);
		return sdf.format(new Date());
	}

	/**
	 * 获取当前日期前n日,或者后n日
	 * 日期格式:yyyyMMdd
	 */
	public static String getRollDate(int amount){
		Calendar date = Calendar.getInstance();

		date.add(Calendar.DAY_OF_MONTH, amount);
		Date currentDate = date.getTime();

		return ROLL_DATE.format(currentDate);
	}

	/**
	 *  日期格式:yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String getDate(String date){
		Date currentDate = null;
		try {
			currentDate = SHORT_DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			currentDate = Calendar.getInstance().getTime();
		}

		return getDate(currentDate);
	}

	/**
	 *  日期格式: yyyMMdd
	 * @param date
	 * @return
	 */
	public static String getDate(Date date){
		return SHORTDATEFORMAT.format(date);
	}

	// 日期格式: yyyyMMddHHmmss
	public static String getFullDateAndTime(Timestamp tmp){
		return FULL_DATE.format(tmp);
	}
	// ---END---

	// ADD BY lihaifeng@UMP: START 2010-11-22 下午04:17:11  BUG号:添加共同方法
	/**
	 * 取得当前时间之前或之后某几个月的日期
	 *
	 */
	public static String getDiffMonth(String curTime,int iMonth){
		if(curTime!=null && !curTime.trim().equals("")){
			try {
				Date date = SHORT_DATE_FORMAT.parse(curTime);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.MONTH, iMonth);
				return getShortDateStr(c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return getDiffMonth(iMonth);
	}
	/**
	 * 取得当前时间之前或之后某几个月的日期
	 * @param curTime
	 * @param iMonth
	 * @return
	 */
	public static String getDiffMonth(Date curTime,int iMonth){
		Calendar c = Calendar.getInstance();
		c.setTime(curTime);
		c.add(Calendar.MONTH, iMonth);
		return getShortDateStr(c.getTime());
	}
	/**
	 * 取得当前日期的前后月份
	 * iMonth>0  几个月后的某一天
	 * iMonth<0  几个月前的某一天
	 *
	 * @param iMonth
	 * @return
	 */
	public static String getDiffMonth(int iMonth){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, iMonth);
		return getShortDateStr(c.getTime());
	}
	/**
	 * 比较两个日期的大小
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareDate(String startDate,String endDate){
		try {
			Date start = SHORT_DATE_FORMAT.parse(startDate);
			Date end = SHORT_DATE_FORMAT.parse(endDate);
			return compareDate(start, end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		return compareDate(c.getTime(), c.getTime());
	}
	/**
	 * 比较两个日期的大小
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareDate(Date startDate,Date endDate){
		if(startDate.compareTo(endDate)>=0){
			return true;
		}
		return false;
	}
	/**
	 * 比较两个日期的大小
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareTwoDate(Date startDate,Date endDate){
		try{
			startDate = SHORT_DATE_FORMAT.parse(SHORT_DATE_FORMAT.format(startDate));
			endDate = SHORT_DATE_FORMAT.parse(SHORT_DATE_FORMAT.format(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(startDate.compareTo(endDate)>0){
			return true;
		}
		return false;
	}

	/**
	 * 和系统时间比较，看是否大于指定的天数
	 *
	 * @author lihaifeng
	 * @param startDate
	 * @param days
	 * @return
	 */
	public static boolean compareToSystime(Date startDate,int days){
		startDate  = DateTools.getDayStart(startDate);
		Date systemDate = DateTools.getDayStart(DateTools.getCurrentDate());
		long startTime = startDate.getTime();
		long systemTime = systemDate.getTime();
		if((systemTime-startTime)>(days*24*60*60*1000)){
			return true;
		}
		return false;
	}
	// ADD BY lihaifeng@UMP: END 2010-11-22 下午04:17:11 BUG号:

//	public static void main(String[] args) {
//		
//		System.out.println(getRollCurrentDate(-1));
//	}

	/**
	 * Timestamp 格式转换成yyyy-MM-dd
	 * timestampToSql(Timestamp 格式转换成yyyy-MM-dd)
	 * @param   timestamp 时间
	 * @return createTimeStr  yyyy-MM-dd 时间
	 * @Exception 异常对象
	 * @since   V1.0
	 */
	public static String timestampToStringYMD(Timestamp  timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat(DateTools.DATE_FORMAT_DATEONLY);
		String createTimeStr = sdf.format(timestamp);
		return createTimeStr;
	}
	
	/**
     * 同一天日期比较 B2B AND B2C
     * @param maps
     */
	public static Map<String, Object> String2Date(Map<String, Object> maps){
    	Map<String, Object> newMaps  = new HashMap<String, Object>();
    	for (Map.Entry<String, Object> item : maps.entrySet()) {
    		String key = item.getKey().toLowerCase();
    		if(key.contains("end") && key.contains("time") 
    				&& key.substring(key.length()-3).equals("end")){
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
	 * 获取YYMMDD格式的日期
	 * 
	 * @return
	 */
	public static String getYYMMDD(){
	    return YYMMDD.format(DateTools.getCurrentDate());
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：获取YYYY-MM日期格式
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2016-3-23
	 * @param date
	 * @return
	 */
	public static String getYYYYMM(Date date){
		return SHORT_MONTH_FORMAT.format(date);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：获取年份
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2016-3-24
	 * @param date
	 * @return
	 */
	public static String getYYYY(Date date){
		return YYYY.format(date);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：获取当前是哪个季度
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2016-3-24
	 * @return
	 */
	public static String getCurQuarter(){
        int month = DateUtils.getIntMonthNow();
        String quarter;
        if (month < 4) {
        	quarter = "1";
        } else if (month >= 4 && month < 7) {
        	quarter = "2";
        } else if (month >= 7 && month < 10) {
        	quarter = "3";
        } else {
        	quarter = "4";
        }
        return quarter;
	}
	
	public static String getYYYYMMDDHHMMSS(Date date){
		return FULL_DATE_TIME_FORMAT.format(date);
	}
	
	public static Date convertYYYYMMDDHHMMSSToDate(String dateStr){
		try {
			return FULL_DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 将String转换成Date
     * @param pattern yyyy-MM-dd
     * @param dateString 
     * @return Date
     */
    public static Date convertStringToDate(String pattern, String dateString) {
        Date newDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            newDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;

    }
	
	/**
	 * 获取YYYY-MM-DD格式的日期
	 * @param date
	 * @return
	 */
	public static String getYYYY_MM_DD(Date date){
		return SHORT_DATE_FORMAT.format(date);
	}

	public static String getYYYYMM(){
			return YM_DATE.format(DateTools.getCurrentDate());
		}

	/**
	 * 获取YYMMDD格式的日期
	 *
	 * @return
	 */
	public static String getYYYYMMDD(){
	    return YYYYMMDD.format(DateTools.getCurrentDate());
	}
}
