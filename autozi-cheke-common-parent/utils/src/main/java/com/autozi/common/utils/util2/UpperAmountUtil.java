/**
 * 文件名称 : UpperAmountUtil.java
 * 项目名称 : B2BV2.0
 * 创建日期 : 2012-9-20
 * 作    者 : LITONGKE
 *
 * Copyright (C) 2012 启购时代 版权所有.
 */
package com.autozi.common.utils.util2;

/** 
 * <PRE> 
 *  
 * 中文描述: 
 *  
 * </PRE> 
 * @version 1.0.0, 2012-9-20 
 * 
 */
public class UpperAmountUtil {

	/**
	 * 
	 * <PRE> 
	 *  
	 * 方法描述: 千亿级金额 小写转大写最大值999999999999.99
	 * 作    者 : LITONGKE
	 * </PRE>
	 * @param value
	 * @return
	 */
	public static String upperAmount(String value){
		if (value == null) {
			return "错误金额";
		}
        Double v = -1d;
        try {
        	v = Double.parseDouble(value);
        }
        catch (Exception e) {
        	return "金额非法";
        }
        if (v == 0 ) {
        	return "零元";
        }
        if (v <= 0 || v > 999999999999.99) {
        	return "错误金额";
        }
        else if (v >= 1 && value.charAt(0) == '0') {
        	return "错误金额";
        }        
        else if (value.charAt(0) == '+') {
        	return "错误金额";
        }
        if (value.indexOf("e") >=0 || value.indexOf("E") >=0) {
        	return "错误金额";
        }
       	int pointIndex = value.indexOf(".");
        if (value.length()-pointIndex > 3) {     //小数点后面超过两位提示
        	return "错误金额";
        }else if(value.length()==pointIndex+1){
        	value = value+"00";
        }else if(value.length()==pointIndex+2){
        	value = value+"0";
        }
        value = value.replace(".", "");
        String upperValue = "";
        int valLen = value.length();
        for(int i = valLen-1; i>=0;i--){
        	switch(valLen-i){
        	case 1:upperValue = "分" + upperValue;break;
        	case 2:upperValue = "角" + upperValue;break;
        	case 3:upperValue = "元" + upperValue;break;
        	case 4:upperValue = "拾" + upperValue;break;
        	case 5:upperValue = "佰" + upperValue;break;
        	case 6:upperValue = "仟" + upperValue;break;
        	case 7:upperValue = "万" + upperValue;break;
        	case 8:upperValue = "拾" + upperValue;break;
        	case 9:upperValue = "佰" + upperValue;break;
        	case 10:upperValue = "仟" + upperValue;break;
        	case 11:upperValue = "亿" + upperValue;break;
        	case 12:upperValue = "拾" + upperValue;break;
        	case 13:upperValue = "佰" + upperValue;break;
        	case 14:upperValue = "仟" + upperValue;break;
        	}
        	char c = value.charAt(i);
        	switch(c){
        	case '1':upperValue = "壹" + upperValue;break;
        	case '2':upperValue = "贰" + upperValue;break;
        	case '3':upperValue = "叁" + upperValue;break;
        	case '4':upperValue = "肆" + upperValue;break;
        	case '5':upperValue = "伍" + upperValue;break;
        	case '6':upperValue = "陆" + upperValue;break;
        	case '7':upperValue = "柒" + upperValue;break;
        	case '8':upperValue = "捌" + upperValue;break;
        	case '9':upperValue = "玖" + upperValue;break;
        	case '0':
        		if(upperValue.startsWith("元")||upperValue.startsWith("万")||upperValue.startsWith("亿")){
//        			upperValue = "零" + upperValue;break;
        		}else{
        			upperValue = "零" + upperValue.substring(1);break;
        		}
        	}
        }
        while(upperValue.contains("零元")){
        	upperValue = upperValue.replace("零元", "元");
        }
        while(upperValue.contains("零万")){
        	upperValue = upperValue.replace("零万", "万");
        }
        while(upperValue.contains("零亿")){
        	upperValue = upperValue.replace("零亿", "亿");
        }
        if(upperValue.contains("元零零")){
        	upperValue = upperValue.replace("元零零", "元整");
        }
        if(upperValue.contains("亿万元")){
        	upperValue = upperValue.replace("亿万元", "亿元");
        }
        if(upperValue.contains("角零")){
        	upperValue = upperValue.replace("角零", "角零分");
        }
        while(upperValue.contains("零零")){
        	upperValue = upperValue.replace("零零", "零");
        }
        if(upperValue.startsWith("元零")){
        	upperValue = upperValue.substring(2);
        }
        if(upperValue.startsWith("元")){
        	upperValue = upperValue.substring(1);
        }
        return upperValue;
	}
	
}
