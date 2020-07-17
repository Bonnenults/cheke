package com.autozi.common.utils.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


/**
 * 此版本使用document 对象封装XML，解决发送短信内容包涵特殊字符而出现无法解析，如 短信为：“你好，<%&*&*&><<<>fds测试短信”
 *
 * @author 8373
 */
public class SendMessageToMobileUtils {
    private static final int CONNECTION_TIMEOUT = 3000;
    private static final Logger logger = LoggerFactory.getLogger(SendMessageToMobileUtils.class);
    // ############################此部分参数需要修改############################
    public static String userid = "962626";
    public static String account = "qeegoo";
    public static String password = "1q2w3e4r5t";
    public static int sendType = 1;
    public static String postFixNumber = "1";
    public static String postUrl = "http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend"; //麦讯通

//    http://www.mxtong.net.cn/Services.asmx/DirectGetStockDetails?UserID=962626&Account=qeegoo&Password=1q2w3e4r5t

    public static void main(String[] args) {

        // 发送短信
//		System.out.println("*************发送短信*************");
        directSend("15210897217", "777777（中驰车福网手机验证码，请完成验证），如非本人操作，请忽略本短信。", "");
//		directSend("15210897217","您好，中驰车福网为您开通了会员，请以手机号登录，密码：456788，欢迎前往www.zcpart.com下载手机APP。【中驰车福】","");
    }

    public static boolean sendPhoneValidateCode(String phone,String code){
        return directSend(phone,code+"（中驰车福网手机验证码，请完成验证），如非本人操作，请忽略本短信。","");
    }

    /**
     * @param phones   手机号码，多个用英文分号“;”隔开
     * @param content  发送内容（长度限制：140个字）
     * @param sendTime 发送时间，为空，立即发送
     * @Description: TODO
     * @user zhiyun.chen
     * @dateTime 2014-4-16下午02:56:01
     */
    public static boolean directSend(String phones, String content, String sendTime) {
        boolean result = false;
        try {
            if (content.length() < 134) {
                content += "【中驰车福】";
                StringBuffer params = new StringBuffer();
                params.append("UserId=" + userid);
                params.append("&Account=" + account);
                params.append("&Password=" + password);
                params.append("&Phones=" + phones);
                params.append("&Content=" + URLEncoder.encode(content, "utf-8"));
                params.append("&SendTime=" + sendTime);
                params.append("&SendType=" + sendType + "");
                params.append("&PostFixNumber=" + postFixNumber);
                httpGet(postUrl, params.toString());
                result = true;
            } else {
                throw new Exception("短信内容不能超过140个字符。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public final static String httpGet(String url, String params)
            throws Exception {
        String response = null; // 返回信息
        if (null != params && !params.equals("")) {
            url += "?" + params;
        }
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建GET方法的实例
        GetMethod httpGet = new GetMethod(url);
        // 设置超时时间
        httpGet.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, CONNECTION_TIMEOUT);
        try {
            int statusCode = httpClient.executeMethod(httpGet);
            if (statusCode == HttpStatus.SC_OK) // SC_OK = 200
            {
                InputStream inputStream = httpGet.getResponseBodyAsStream(); // 获取输出流，流中包含服务器返回信息
                response = getData(inputStream);// 获取返回信息
            } else {
                logger.info("Get Method Statuscode : " + statusCode);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            httpGet.releaseConnection();
            httpClient = null;
        }
        return response;
    }

    private final static String getData(InputStream inputStream)
            throws Exception {
        String data = "";
        // 内存缓冲区
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = -1;
        byte[] buff = new byte[1024];
        try {
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
            byte[] bytes = outputStream.toByteArray();
            data = new String(bytes);
        } catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            outputStream.close();
        }
        return data;
    }
}
