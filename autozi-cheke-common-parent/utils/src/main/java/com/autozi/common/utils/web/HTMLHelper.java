/**
 * 
 */
package com.autozi.common.utils.web;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

/**
 * html标签替换
 * 
 * @author lihaifeng
 * 
 */
public class HTMLHelper {
	/**
	 * 忽略了ISO Latin-1 特殊字符以及 &ensp;半个空白位 和&emsp; 一个空白位
	 * 
	 * 加入了 "\ "
	 * 
	 * &lt; < 小于号或显示标记 
	 * &gt; > 大于号或显示标记 
	 * &amp; & 可用于显示其它特殊字符 
	 * &quot; " 引号 
	 * &reg; \u00AE 已注册 
	 * &copy; \u00A9 版权 
	 * &trade; \u2122 商标 
	 * &nbsp; 不断行的空白
	 * &#36; $
	 */
	public final static HashMap<String, String> specialCharSet = new LinkedHashMap<String, String>();
	public final static HashMap<String, String> specialCharUnSet = new LinkedHashMap<String, String>();
	static {
		specialCharSet.put("&", "&amp;");
		specialCharSet.put("<", "&lt;");
		specialCharSet.put(">", "&gt;");
		specialCharSet.put("\"", "&quot;");
		specialCharSet.put("\'", "&acute;");
		specialCharSet.put("\u00AE", "&reg;");
		specialCharSet.put("\u00A9", "&copy;");
		specialCharSet.put("\u2122", "&trade;");
		//specialCharSet.put(" ", "&nbsp;");
		//specialCharSet.put("　", "&nbsp;&nbsp;");
		specialCharSet.put("$", "&#36;");
		
		// 反
		specialCharUnSet.put("&#36;", "$");
		specialCharUnSet.put("&nbsp;&nbsp;", "  ");
		specialCharUnSet.put("&nbsp;", " ");
		specialCharUnSet.put("&trade;", "\u2122");
		specialCharUnSet.put("&copy;", "\u00A9");
		specialCharUnSet.put("&reg;", "\u00AE");
		specialCharUnSet.put("&quot;", "\"");
		specialCharUnSet.put("&acute;", "\'");
		specialCharUnSet.put("&gt;", ">");
		specialCharUnSet.put("&lt;", "<");
	}

	/**
	 * 替换所有的HTML字符
	 * 
	 * @param s
	 * @return
	 */
	public static String substitute(String s) {
		if(StringUtils.isEmpty(s)){
			return "";
		}
		s = s.trim();
		for (Iterator<String> i = specialCharSet.keySet().iterator(); i
				.hasNext();) {
			String repl = (String) i.next();
			String with = (String) specialCharSet.get(repl);
			s = StringUtils.replace(s, repl, with);
		}
		return s;
	}
	/**
	 * 替换所有的HTML字符
	 * 
	 * @param s
	 * @return
	 */
	public static String subUnstitute(String s) {
		if(StringUtils.isEmpty(s)){
			return "";
		}
		s = s.trim();
		for (Iterator<String> i = specialCharUnSet.keySet().iterator(); i
				.hasNext();) {
			String repl = (String) i.next();
			String with = (String) specialCharUnSet.get(repl);
			s = StringUtils.replace(s, repl, with);
		}
		return s;
	}

    /**
     * 对传入的对象进行属性遍历，对String类型的数据进行转义
     * @param obj
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void substituteObject(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class objClass = obj.getClass();
        Field[] objFiles=objClass.getDeclaredFields();
        for(Field field:objFiles){
           if(field.getType().getName().equals("java.lang.String")){ //取得String的属性名字
               String fieldName = field.getName();
               //将属性名首字母转换成大写字母
               String firstLetter = fieldName.substring(0, 1).toUpperCase();
               //获取属性（field）对应get方法名称
               String getMethodName = "get" + firstLetter + fieldName.substring(1);
               //获取属性（field）对应set方法名称
               String setMethodName = "set" + firstLetter + fieldName.substring(1);
               //通过get方法名称获取属性（field）对应get方法  set方法
               Method getMethod = objClass.getMethod(getMethodName);
               Method setMethod = objClass.getMethod(setMethodName,String.class);
               if(getMethod !=null && setMethod !=null){
                   Object value = getMethod.invoke(obj);  //取得属性的值
                   if(value!=null && String.valueOf(value).length()>0){
                       setMethod.invoke(obj,substitute((String)value));
                   }
               }
           }
        }
    }



}
