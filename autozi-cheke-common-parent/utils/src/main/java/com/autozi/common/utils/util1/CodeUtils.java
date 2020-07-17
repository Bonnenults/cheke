package com.autozi.common.utils.util1;

import org.apache.commons.lang.xwork.StringUtils;

/**
 * Author: clover_4l
 * Date: 11-5-21
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class CodeUtils {

    /**
     * 获得下一级Code 110000 -> 120000, 110100 -> 110200
     * @param codeNumber 原始code
     * @return 下一级code
     */
    public static long nextCode(long codeNumber) {
        String code = String.valueOf(codeNumber);
        int originalCodeLength = code.length();
        long nextCodeNumber = Long.parseLong(StringUtils.stripEnd(code,"0")) + 1;
        return Long.parseLong(StringUtils.rightPad(String.valueOf(nextCodeNumber), originalCodeLength, "0"));
    }

    public static long nextCode(long codeNumber, int sectionLength) {
        String code = String.valueOf(codeNumber);
        int originalCodeLength = code.length();
        long simplified = simplify(codeNumber, sectionLength) + 1;
        return Long.parseLong(StringUtils.rightPad(String.valueOf(simplified), originalCodeLength, "0"));
    }

    /**
     * 将Code简化, 110000 -> 11
     * @param codeNumber 原始code
     * @return 简化code
     */
    public static long simplify(long codeNumber) {
        String striped = StringUtils.stripEnd(String.valueOf(codeNumber), "0");
        return Long.parseLong(striped);
    }

    public static long simplify(long codeNumber, int sectionLength) {
        String striped = StringUtils.stripEnd(String.valueOf(codeNumber), "0");
        int t = striped.length() % sectionLength;
        int length = (striped.length() / sectionLength + (t == 0 ? 0 : 1)) * sectionLength;
        return Long.parseLong(StringUtils.rightPad(striped, length, "0"));
    }

    public static void main(String[] args) {
        System.out.println(nextCode(110000000000L, 3));
    }
}
