package com.autozi.common.utils.util3;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.autozi.common.utils.util2.DateUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

/**
 * 容联-云通讯短信发送接口
 */
public class SMSUtils {

    private static final String SERVICE_URL = "app.cloopen.com";  //云通讯正式环境URL
    private static final String SERVICE_PORT = "8883";  //云通讯正式环境端口
    private static final String ACCOUNT_SID = "8a48b5514c2fd22f014c366b3a9e054c";   //云通讯账号SID
    private static final String AUTH_TOKEN = "3b1f61f077594d508cba190de6b08063"; //云通讯账号TOKEN
    private static final String APP_ID = "8aaf0708611b8284016126ffb72406e2";  //云通讯中驰车福应用ID

    public static final String MODEL_ID_CK_ZC = "231986";           //车客注册

    public static void main(String[] args) {
    	//验证码测试
		sendMessage("15201222433", new String[]{"000001"}, "231986");
		
    	//退款测试
		 String[] content = new String[1];
//         content[0] = "(1111111,2222222)";
//         content[1] = "88.88";
//         content[0] = "26";
//         sendMessage("15201222433", content, MODEL_ID_CK_ZC);
//
         //积分清零测试
//         sendMessage("15210897217", new String[]{"1000"}, MODEL_ID_INTEGRAL);
         
         //积分清零测试
//         String[] content = new String[3];
//	     content[0] = "zhangsan";
//	     content[1] = "12345";
//	     sendMessage("15210897217", content, MODEL_ID_TUANGOU_REGISTER);
	}

    /**
     * 发送短信
     *
     * @param phones  电话号码，可以用逗号分隔，一次最多支持100个手机号
     * @param content 发送内容数组，用于替换模板参数
     */
    public static boolean sendMessage(String phones, String[] content,String  modelId) {
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init(SERVICE_URL, SERVICE_PORT);

        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************

        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
        restAPI.setAppId(APP_ID);

        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。																														       *
        //**************************************************************************************************

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(phones, modelId, content);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回
            return true;
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            return false;
        }
    }

	public static void sendMessageAsyn(final String phones, final String[] content, final String modelId) {
		try {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					sendMessage(phones, content, modelId);
				}
			});
			thread.start();
		} catch (Exception e) {
			throw new RuntimeException("短信发送失败");
		}
	}
}
