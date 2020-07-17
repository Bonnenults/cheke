package com.autozi.common.utils.util2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextFormatter
{
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatToSimpleDate(Date date)
	{
		return simpleDateFormat.format(date);
	}

	private static SimpleDateFormat DateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDateToString(Date date)
	{
		return DateFormatWithoutTime.format(date);
	}
	
	public static Date formatStringToDate(String date) throws ParseException
	{
			return DateFormatWithoutTime.parse(date);
	}
}
