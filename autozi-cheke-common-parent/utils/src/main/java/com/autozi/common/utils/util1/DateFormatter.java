package com.autozi.common.utils.util1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_8 = "yyyyMMdd";

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);

	public static String formatToSimpleDate(Date date) {
		return date == null ? "" : simpleDateFormat.format(date);
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	public static String formatToDate(Date date) {
		return date == null ? "" : dateFormat.format(date);
	}

	public static String formatToDate(Date date, String formatter) {
		SimpleDateFormat format = new SimpleDateFormat(formatter);
		return date == null ? "" : format.format(date);
	}

	public static String Today() {
		return formatToSimpleDate(new Date());
	}
	
	public static Date parseDate(String date,String formatter){
		SimpleDateFormat format = new SimpleDateFormat(formatter);
		Date return_date = Calendar.getInstance().getTime();
		try{
			return_date = format.parse(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return return_date;
	}
}
