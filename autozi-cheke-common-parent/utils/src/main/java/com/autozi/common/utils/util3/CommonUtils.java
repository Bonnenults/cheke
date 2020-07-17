package com.autozi.common.utils.util3;

import com.autozi.common.core.page.Page;
import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.autozi.common.core.page.Page;
import com.autozi.common.core.utils.ApplicationPropertiesHelper;

public class CommonUtils {
	
	public static Date newDate()
	{
		return new Date();
	}
	/**
	 * 
	 * @param entityo
	 * @param entityc
	 * @param map
	 * @return
	 */
	private static Map<String ,Object> getMap(final Object entityo,final Class entityc,Map<String, Object> map){		
		for(Field field : entityc.getDeclaredFields()){
			if(!field.getName().equalsIgnoreCase("serialVersionUID")){
				field.setAccessible(true);
				String pname = field.getName();
				Object value = null;
				try {
					value = field.get(entityo);
					if(!(null==value))
					map.put(pname, value);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return map;	
	}
	/**
	 * 
	 * @param map   转化得到的map项将被加至此map中
	 * @param entity 需要转化成map的entity
	 * @return map
	 */
	public static Map<String, Object> objectToMap(Map<String, Object> map,Object entity){
		Class  classnow = entity.getClass();
		while(classnow!=Object.class){
			getMap(entity,classnow,map);
			classnow = classnow.getSuperclass();
		}
		return map;
	}
	
	/**
	 *
	 * 功能描述：将decimal转换成long。
	 *
	 * @param object
	 * @return
	 */
	public static Long convertDecimalToLong(Object object){
		if(object != null){
			BigDecimal decimal = (BigDecimal)object;
			return decimal.longValue();
		}else{
			return null;
		}
	}
	
	/**
	 *
	 * 功能描述：将decimal转换成Integer
	 *
	 * @param object
	 * @return
	 */
	public static Integer convertDecimalToInteger(Object object){
		if(object != null){
			BigDecimal decimal = (BigDecimal)object;
			return decimal.intValue();
		}else{
			return null;
		}
	}
	
	/**
	 * 用于poPage与voPage之间的分页参数转换，在每个方法中需要调用两次
	 * @param sourcePage
	 * @param targetPage
	 */
	public static void pageConversion (Page sourcePage, Page targetPage){
		targetPage.setPageSize(sourcePage.getPageSize());
		targetPage.setPageNo(sourcePage.getPageNo());
		targetPage.setTotalCount(sourcePage.getTotalCount());
        if(!StringUtils.isEmpty(sourcePage.getOrder())){
            targetPage.setOrder(sourcePage.getOrder());
        }
        if(!StringUtils.isEmpty(sourcePage.getOrderBy())){
            targetPage.setOrderBy(sourcePage.getOrderBy());
        }
	}
	
	
	
	
	
	/**
	 *
	 * 功能描述：将decimal转换成long。
	 *
	 * @param object
	 * @return
	 */
	public static Double convertDecimalToDouble(Object object){
		if(object != null){
			BigDecimal decimal = (BigDecimal)object;
			return decimal.doubleValue();
		}else{
			return null;
		}
	}
	public static Date convertDateFormat(Date date) throws Exception{		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dates = sdf.format(date);		
		return sdf.parse(dates);
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
	 * 转换成Date
	 * @param pattern yyyy-MM-dd
	 * @param date 
	 * @return Date
	 */
	public static Date formatDate(String pattern, Date date) {
		Date newDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			newDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
		
	}
	
	
	/**
	 * 返回日期格式为2011-03-08
	 * @param trialTime 为null时,返回系统当前时间
	 * @return string
	 */
	public static String getDate(Date trialTime) {
		Calendar calendar = new GregorianCalendar();
		if(trialTime==null){
			trialTime=new Date(System.currentTimeMillis());
		}
		String ret="";
		
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		ret=year + "-" ;
		if(month<10){
			ret=ret+"0"+month+"-";
		}else{
			ret=ret+month+"-";
		}
		if(day_of_month<10){
			ret=ret+"0"+day_of_month;
		}else{
			ret=ret+day_of_month;
		}
		return ret;
	}
	/**
	 * 返回日期格式为YYYY-MM-DD HH:MM:SS
	 * @param trialTime 为null时,返回系统当前时间
	 * @return string
	 */
	public static String getFullDate(Date trialTime) {
		String ret="";
		if(trialTime==null){
			trialTime=new Date(System.currentTimeMillis());
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		ret=year + "-" ;
		if(month<10){
			ret=ret+"0"+month+"-";
		}else{
			ret=ret+month+"-";
		}
		if(day_of_month<10){
			ret=ret+"0"+day_of_month;
		}else{
			ret=ret+day_of_month;
		}
		ret=ret+ " ";
		if(hour<10){
			ret=ret+"0"+hour+"-";
		}else{
			ret=ret+hour+":";
		}
		if(minute<10){
			ret=ret+"0"+minute+":";
		}else{
			ret=ret+minute+":";
		}
		if(second<10){
			ret=ret+"0"+second;
		}else{
			ret=ret+second;
		}
		return ret;
	}
	/**
	 * 返回日期格式为YYYYMMDDHHMMSS
	 * @param trialTime 为null时,返回系统当前时间
	 * @return string
	 */
	public static String getSimpleDate(Date trialTime) {
		String ret="";
		if(trialTime==null){
			trialTime=new Date(System.currentTimeMillis());
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		ret=year + "" ;
		if(month<10){
			ret=ret+"0"+month+"";
		}else{
			ret=ret+month+"";
		}
		if(day_of_month<10){
			ret=ret+"0"+day_of_month;
		}else{
			ret=ret+day_of_month;
		}
		ret=ret+ "";
		if(hour<10){
			ret=ret+"0"+hour+"";
		}else{
			ret=ret+hour+"";
		}
		if(minute<10){
			ret=ret+"0"+minute+"";
		}else{
			ret=ret+minute+"";
		}
		if(second<10){
			ret=ret+"0"+second;
		}else{
			ret=ret+second;
		}
		return ret;
	}
	
	public static List<Long> intersectionOfTwoLongList(List<Long> idList_1, List<Long> idList_2){
		List<Long> idList = new ArrayList<Long>();
		for(Long id_1 : idList_1){
			for(Long id_2 : idList_2){
				if(id_1==id_2||id_1.equals(id_2))
					idList.add(id_1);
			}
		}
		return idList;
	}
	
	/**
	 * 图片路径设定
	 * 
	 * @param sImgFileName
	 * @return
	 */
	public static String getImgURL(String sImgFileName){
		String sImgURL = null;
		if ((sImgFileName!=null)&&(sImgFileName.trim()!="")){
			sImgURL=ApplicationPropertiesHelper.getImgServerUrl()+sImgFileName;
		}else{
			sImgURL=ApplicationPropertiesHelper.getImgServerUrl()+ApplicationPropertiesHelper.getImgDefaultFileName();
		}
		return sImgURL;
	}


	private static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";
   private static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm";
	public static Date formatShortDate(String dateString){
        Date newDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_PATTERN);
        try {
            newDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    public static Date formatLongDate(String dateString){
        Date newDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_PATTERN);
        try {
            newDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
	}
}
