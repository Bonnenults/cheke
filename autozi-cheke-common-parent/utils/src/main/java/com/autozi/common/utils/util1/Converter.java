package com.autozi.common.utils.util1;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class Converter {
	
	
	public static String toString(Object val) {
		String rtn = "";
		try {
		
			 rtn = val.toString();
			
		} catch (Exception ex) {

		}
		return rtn;
	}
	
	public static Integer toInteger(Object val) {
		Integer rtn = null;
		try {
			rtn = Integer.valueOf(val.toString());
		} catch (Exception ex) {
            
		}
		return rtn;
	}

	public static Long toLong(Object val) {
		Long rtn = null;
		try {
			rtn = Long.valueOf(val.toString());
		} catch (Exception ex) {

		}
		return rtn;
	}
	
	public static Double toDouble(Object val) {
		Double rtn = null;
		try {
			rtn = Double.valueOf(val.toString());
		} catch (Exception ex) {

		}
		return rtn;
	}
	public static Date toDate(Object val) {
		Date rtn = null;
		try {
			rtn = (Date) new DateConverter().convert(Date.class, val);
		} catch (Exception ex) {

		}
		return rtn;
	}
	public static Date toDate(Object val,String format) {
		Date rtn = null;
		try {
			rtn = (Date) new DateConverter(format).convert(Date.class, val);
		} catch (Exception ex) {

		}
		return rtn;
	}
	public static String toDateStr(Date val) {
		String rtn = null;
		try {
			rtn = (String) new DateConverter().convert(String.class, val);
		} catch (Exception ex) {

		}
		return rtn;
	}
	
	public static Boolean toBoolean(Object val) {
		Boolean rtn = null;
		try {
			rtn = Boolean.valueOf(val.toString());
		} catch (Exception ex) {

		}
		return rtn;
	}
	@SuppressWarnings("unchecked")
	public static Map<String,String> objMaptoStrMap(Map val) {
		Map<String,String> rtn = new TreeMap<String,String>(new Comparator() {
	        public int compare(Object o1, Object o2) {
	             if (o1 == null || o2 == null)
	                   return 0; 
	             return Converter.toLong(o1).compareTo( Converter.toLong(o2));             
	     }
	   });
		try {
			for(Object key:val.keySet()){
				rtn.put(Converter.toString(key), Converter.toString(val.get(key)));
				
			}
		} catch (Exception ex) {

		}
		return rtn;
	}
	/**
	 * 空字符串转换成NULl
	 * 
	 * @author lihf
	 * @param str
	 * @return
	 */
	public static String convertEmptyStrToNull(String str){
		if(StringUtils.isBlank(str)){
			return null;
		}
		return str.trim();
	}
	
	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormat(BigDecimal money){
		DecimalFormat format = new DecimalFormat("###,##0.00");
		return format.format(money);
	}
	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormat(Double money){
		DecimalFormat format = new DecimalFormat("###,##0.00");

		return format.format(money);
	}
	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public static Double moneyFormat(Float money){
		BigDecimal decimal = new BigDecimal(String.valueOf(money));
	
		return decimal.doubleValue();
	}
	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public static Double moneyToDouble(String money){
		DecimalFormat format = new DecimalFormat("###,###.00");
	
		try {
			return Converter.toDouble(format.parse(money));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 金额格式化-不带风格符号
	 * 
	 * @author lihf
	 * @param money
	 * @return
	 */
	public static String moneyFormatLong(BigDecimal money){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(money);
	}
	/**
	 * 金额格式化-不带风格符号
	 * 
	 * @author lihf
	 * @param money
	 * @return
	 */
	public static String moneyFormatLong(Double money){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(money);
	}
	/**
	 * 元到分的转换，12位长度，左补0
	 * @author lihf
	 * @param money
	 * @return
	 */
	public static String moneyYuanToFen(BigDecimal money){
		BigDecimal fen_money = money.multiply(new BigDecimal(100));
		return String.format("%012d", fen_money.toBigInteger());
	}
	/**
	 * 交易流水号补零-16位
	 * 
	 * @param settleId
	 * @return
	 */
	public static String getTradeId(Long settleId){
		return String.format("%016d", settleId);
	}
	/**
	 * 从流水号获取我方平台的结算单ID
	 * 
	 * @param orderNo
	 * @return
	 */
	public static Long getUnTradeId(String orderNo){
		int index = 0;
		char[]array = orderNo.toCharArray();
		for (char c : array) {
			if(c>'0'){
				index++;
				break;
			}
		}
		String orderId = orderNo.substring(index);
		return Long.valueOf(orderId);
	}
	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String values[], Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(values, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	
	/**
	 * 将金额左补0,共15位
	 * @param money
	 */
	public static String converDoubleToString15(Double money){
		StringBuffer buffer = new StringBuffer() ;
		buffer.append("") ;
		
		if(null != money){
			int totalLength = 15 ;
			DecimalFormat format = new DecimalFormat("0.00");		
			String moneyStr  = format.format(money) ;
			int   moneyLength = moneyStr.length() ;
			int   tmp = totalLength  - moneyLength ;
			
			for(int i = 0 ; i < tmp ; i++ ){
				buffer.append("0") ;
			}
			
			buffer.append(moneyStr) ;
			if(buffer.toString().length()>15){
				return buffer.toString().substring(0, 15) ;
			}
		}
		
		return  buffer.toString() ;
	}
	
	
	/**
	 * 
	 * @param money
	 * @return
	 */
	public static Long converDoubleToLong(Double money){
		Long tmp = 0L ;
		if(null != money ){			
			BigDecimal aa = new BigDecimal(money);  
		    BigDecimal bb = aa.setScale(2,BigDecimal.ROUND_HALF_UP) ;
		    BigDecimal cc = new BigDecimal("100") ;
		    BigDecimal result = bb.multiply(cc) ;		    
		    tmp = result.longValue() ;
		}	    
	    // System.out.println(result.longValue()); 
			
       return  tmp ;
	}
	
	
}
