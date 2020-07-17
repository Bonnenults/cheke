package com.autozi.common.utils.util1;

import java.math.BigDecimal;

/**
 * 金额类型. 计算工具类.
 * @author LiuYouRun
 * @Version 0.1
 * 2011-04-15
 */
public class BigDecimalTools{

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，再往后的数字四舍五入。
	 * 即表示需要精确到小数点以后几位。
	 */
	public static final int scale = 10;

	/** */
	/**
	 * 这个类不能实例化
	 * 
	 */
	private BigDecimalTools() {
	}
	
	/*---------------------------------double 类型  start------------------------------------*/
	/**
	 * 两个double类型的数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return a1.add(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个double类型的数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2,int sale) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		if(sale<0){
			throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
		}
		return a1.add(a2).setScale(sale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 三个double类型的数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 */
	public static double add(double v1, double v2, double v3) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		BigDecimal a3 = new BigDecimal(Double.toString(v3));
		return a1.add(a2).add(a3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/** */
	/**
	 * 两个double类型的数值相除，当发生除不尽的情况时，默认精确到小数点后10位
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个数的商
	 */
	public static double div(double v1, double v2) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return divide(a1, a2);
	}
	
	/** */
	/**
	 * 两个double类型的数值相除
	 * 
	 * @param v1
	 * @param v2
	 * @param v3   小数点后保留几位
	 * @return 两个数的商
	 */
	public static double div(double v1, double v2, int v3) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return divide(a1, a2, v3);
	}

	/**
	 * 两个double类型的数值相减
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return  a1.subtract(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 三个double类型的数值相减
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return 三个参数的差
	 */
	public static double sub(double v1, double v2, double v3) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		BigDecimal a3 = new BigDecimal(Double.toString(v3));
		return  a1.subtract(a2).subtract(a3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：相减，可以指定四舍五入精度
	 * 
	 * </PRE>
	 * @作者 Lihf
	 * @日期 2012-11-2
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return
	 */
	public static double sub(double v1, double v2,int scale) {
	    if(scale<0){
	        throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
	    }
	    BigDecimal a1 = new BigDecimal(Double.toString(v1));
	    BigDecimal a2 = new BigDecimal(Double.toString(v2));
	    return  a1.subtract(a2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/** */
	/**
	 * 两个double类型的数值相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的乘积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return a1.multiply(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：用于指定精度的乘法
	 * 
	 * </PRE>
	 * @作者 Lihf
	 * @日期 2012-10-30
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return
	 */
	public static double mul(double v1, double v2,int scale){
	    BigDecimal a1 = new BigDecimal(Double.toString(v1));
	    BigDecimal a2 = new BigDecimal(Double.toString(v2));
	    if(scale<0){
	        throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
	    }
	    return a1.multiply(a2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/*---------------------------------double 类型  end------------------------------------*/
	/*---------------------------------String 类型  start------------------------------------*/
	/**
	 * 两个String类型的数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(String v1, String v2) {
		BigDecimal a1 = new BigDecimal(v1);
		BigDecimal a2 = new BigDecimal(v2);
		return a1.add(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个String类型的数值相除，当发生除不尽的情况时，默认精确到小数点后10位
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个数的商
	 */
	public static double div(String v1, String v2) {
		BigDecimal a1 = new BigDecimal(v1);
		BigDecimal a2 = new BigDecimal(v2);
		return divide(a1, a2);
	}
	
	
	/**
	 * 两个String类型的数值相除
	 * 
	 * @param v1
	 * @param v2
	 * @param v3  小数点后保留几位
	 * @return 两个数的商
	 */
	public static double div(String v1, String v2, int v3) {
		BigDecimal a1 = new BigDecimal(v1);
		BigDecimal a2 = new BigDecimal(v2);
		return divide(a1, a2, v3);
	}
	
	/**
	 * 两个String类型的数值相减
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的差
	 */
	public static double sub(String v1, String v2) {
		BigDecimal a1 = new BigDecimal(v1);
		BigDecimal a2 = new BigDecimal(v2);
		return a1.subtract(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个String类型的数值相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的乘积
	 */
	public static double mul(String v1, String v2) {
		BigDecimal a1 = new BigDecimal(v1);
		BigDecimal a2 = new BigDecimal(v2);
		return a1.multiply(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/*---------------------------------String 类型  end------------------------------------*/
	/**
	 * 两个BigDecimal类型变量相除 ,该方法供div方法调用
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个数的商 double
	 */
	private static double divide(BigDecimal v1, BigDecimal v2) {
		return v1.divide(v2, scale,	BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	
	/**
	 * 两个BigDecimal类型变量相除 ,该方法供div方法调用
	 * 
	 * @param v1 
	 * @param v2 
	 * @param v3  精度
	 * @return
	 */
	private static double divide(BigDecimal v1, BigDecimal v2, int v3) {
		if (v3 < 0) {
			throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
		}
		return v1.divide(v2, v3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
