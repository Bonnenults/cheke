package com.autozi.common.utils.util2;

import org.apache.commons.lang.xwork.StringUtils;

import com.autozi.common.utils.web.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Converter {
	
	  public static Map<String, Object> covertToMap(Object bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
          Map<String, Object> result = new HashMap<String, Object>();
          Class<?> clazz = bean.getClass();
          BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
          PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
          for (PropertyDescriptor descriptor : descriptors) {
              Method getter = descriptor.getReadMethod();
              Object returnValue = getter.invoke(bean);
              if (returnValue != null) {
              	if("-1".equals(returnValue)){
              		continue;
              	}
                  if (returnValue.getClass() == String.class) {
                      if (StringUtils.isNotBlank(returnValue.toString())) {
                          result.put(descriptor.getName(), returnValue.toString().trim());
                      }
                  } else {
                      result.put(descriptor.getName(), returnValue);
                  }
              }
          }

          return result;
      }

  /**
   * 同一天日期比较 B2B AND B2C
   * @param maps
   */
  public static void forDates(Map<String, Object> maps){
  	for (Map.Entry<String, Object> item : maps.entrySet()) {
  		String key = item.getKey().toLowerCase();
  		if(key.contains("end")){
				String value = item.getValue().toString();
				String valueEnd = (Integer.valueOf(value.substring(value.length()-1)) + 1) + "";
				maps.put(key, value.substring(0, value.length()-1) + valueEnd);
  		}
		}
  }

  public static <T> T convertToBean(Map<String, Object> properties, Class<T> clazz) throws IllegalAccessException, InstantiationException {
      T instance = clazz.newInstance();
      BeanUtils.copyProperties(instance, properties);
      return instance;
  }

  public static void convertToBean(Map<String, Object> properties, Object bean) {
      BeanUtils.copyProperties(bean, properties);
  }
	
	
	public static Integer toInteger(Object val) {
		Integer rtn = null;
		try {
			rtn = Integer.valueOf(val.toString());
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
	 * @param money
	 * @return
	 */
	public static String moneyFormatLong(BigDecimal money){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(money);
	}
	
	/**
	 * 元到分的转换，12位长度，左补0
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
}
