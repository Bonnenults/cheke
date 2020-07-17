package com.autozi.common.utils.o2o;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: long.jin
 * Date: 14-11-25
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class NumberTools {

    private static DecimalFormat normalFormat = new DecimalFormat("0.00");
    private static DecimalFormat douHaoFormat = new DecimalFormat("#,##0.00");

    public static String normalFormatToString(Double number){
      return normalFormat.format(number);
    }

    public static String douHaoFormatToString(Double number){
        return douHaoFormat.format(number);
    }
    /**
     * 判断一个字符串是否是整数（不能加-(负) +（正））
     * @param input
     * @return
     */
    public static boolean isInteger(String input){  
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);  
        return mer.find();  
    }  
    public static void main(String[] args){
        System.out.println(isInteger("0")) ;
    }
}
