package com.autozi.common.utils.o2o;

import java.util.regex.Pattern;

/**
 *正则验证工具类，验证各种输入是否和法
 * User: denghua
 * Date: 15-11-13
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class PatternUtils {
    //电话验证法则
    private static final String PAT_TELEPHONE =  "^((0[1-9]{3})?(0[12][0-9])?[-])?\\d{6,8}([-]\\d+)?$";
    //手机号验证法则
    private static final String  PAT_PHONE =  "^((1[34578][0-9]{9})|((0\\d{2}-\\d{8})|(0\\d{3}-\\d{7,8})|(0\\d{10,11}))|(((400)-(\\d{3})-(\\d{4}))|((400)-(\\d{4})-(\\d{3}))|((400)-(\\d{5})-(\\d{2}))|(400\\d{7}))|(((800)-(\\d{3})-(\\d{4}))|((800)-(\\d{4})-(\\d{3}))|((800)-(\\d{5})-(\\d{2}))|(800\\d{7})))$";
    //
    private static final String VALIDATE_TIME="^[0-9]{4}$";
    /**
     * 验证手机号或者电话号码是否合法
     * @param phone 手机号或者电话(未做空值判断)
     * @return
     */
    public static boolean checkPhone(String phone){
        if(Pattern.matches(PAT_TELEPHONE,phone)||Pattern.matches(PAT_PHONE, phone)){
            return true;
        }
        return false;
    }

    public static boolean checkTime(String time){
        if(Pattern.matches(VALIDATE_TIME,time)){
            return true;
        }
        return false;
    }


    }
