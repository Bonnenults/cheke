package com.autozi.common.web.sso;

import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.web.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: kai.liu Date: 11-11-22 Time: 下午4:34
 */
public class SsoCodec {

	public static final int TWO_WEEKS_S = 1209600;
	
	public static String encode(String username, String password,
			long expirationTime) {
		String innerKey = "{" + username + ":" + expirationTime + ":"
				+ password + "}";
		innerKey = DigestUtils.md5Hex(innerKey.getBytes());
		String key = username + ":" + expirationTime + ":" + innerKey;
		return Base64.encodeBase64URLSafeString(key.getBytes());
	}

	public static SsoInfo decode(String key) {
		String decodedKey = new String(Base64.decodeBase64(key));
		String[] info = decodedKey.split(":");
		if (info.length == 3) {
			return new SsoInfo(info[0], new Date(Long.parseLong(info[1])),
					info[2]);
		} else {
			return null;
		}
	}

	public static String springSecurityKey(String username, String password,
			long time) {
		String data = username + ":" + time + ":" + password + ":SpringSecured";
		String innerKey = DigestUtils.md5Hex(data.getBytes());
		return Base64
				.encodeBase64URLSafeString((username + ":" + time + ":" + innerKey)
						.getBytes());
	}

	public static String urlEncode(String url, String jumpUrl) {
		String com_url = ApplicationProperties.getValue("frontDomain")
				+ "/api.php?m=Login";
		StringBuilder str = new StringBuilder();
		str.append(com_url).append("&url=");
		JSONObject json = new JSONObject();
		json.put("url", HttpClientUtil.getPostUrlAfterKey(url, "&"));
		json.put("jumpUrl", jumpUrl);

		String innerKey = json.toString();
		// System.out.println(innerKey);
		String src = com.autozi.common.utils.util2.Base64.encode(innerKey);
		str.append(src);
		return str.toString();
	}

	/**
	 * @Description: 跳转o2o的链接进行转化(o2o)
	 * @user zhiyun.chen
	 * @dateTime 2014-5-7下午04:34:29
	 */
	public static String urlEncodeForO2O(String loginName, String password,String jumpUrl,Integer menuId) {
		String com_url = ApplicationProperties.getValue("frontDomain")+ "/o2o/api/log.action?type="+ com.autozi.common.utils.util2.Base64.encode("login");
		StringBuilder after_url = new StringBuilder();
		after_url.append(com_url);
		after_url.append("&key=");
		after_url.append(encode(loginName, password, TWO_WEEKS_S));
		after_url.append("&jumpUrl=");
		String after_jumpUrl = com.autozi.common.utils.util2.Base64.encode(ApplicationProperties.getValue("frontDomain") + jumpUrl);// 对跳转jumpUrl进行加密
		after_url.append(after_jumpUrl);
		if(menuId != null){
			after_url.append("&menuId=");
			after_url.append(com.autozi.common.utils.util2.Base64.encode(menuId.toString()));
		}
		return HttpClientUtil.getO2OPostUrlAfterKey(after_url.toString());
	}
	
	/**
	 * @Description: 跳转培训系统的链接进行转化(培训）
	 * @user zhiyun.chen
	 * @dateTime 2014-5-7下午04:34:29
	 */
//	public static String urlEncodeForPX(String loginName, String password,String jumpUrl,Integer menuId) {
//		String com_url = ApplicationProperties.getValue("pxDomain")+ "/o2o/api/log.action?type="+ com.qigou.common.util.Base64.encode("login");
//		StringBuilder after_url = new StringBuilder();
//		after_url.append(com_url);
//		after_url.append("&key=");
//		after_url.append(encode(loginName, password, TWO_WEEKS_S));
//		after_url.append("&jumpUrl=");
//		String after_jumpUrl = com.qigou.common.util.Base64.encode(ApplicationProperties.getValue("pxDomain") + jumpUrl);// 对跳转jumpUrl进行加密
//		after_url.append(after_jumpUrl);
//		if(menuId != null){
//			after_url.append("&menuId=");
//			after_url.append(com.qigou.common.util.Base64.encode(menuId.toString()));
//		}
//		return HttpClientUtil.getO2OPostUrlAfterKey(after_url.toString());
//	}
	
    
    public static String urlEncodeForO2OLogout() throws Exception{
    	String com_url = ApplicationProperties.getValue("frontDomain") + "/o2o/api/log.action?type=" + com.autozi.common.utils.util2.Base64.encode("logout");
		return HttpClientUtil.getO2OPostUrlAfterKey(com_url);
    }
    
    public static String urlEncodeForO2OReportLogout() throws Exception{
    	String com_url = ApplicationProperties.getValue("o2oReportDomain") + "/o2o/api/log.action?type=" + com.autozi.common.utils.util2.Base64.encode("logout");
		return HttpClientUtil.getO2OPostUrlAfterKey(com_url);
    }

	/**
      * @Description: 跳转b2c的链接进行转化
      * @user zhiyun.chen
      * @dateTime 2014-5-7下午04:34:29
     */
    public static String urlEncodeForB2cLogin(String loginName, String password, String jumpUrl){
    	String com_url = ApplicationProperties.getValue("b2cDomain") + "/o2o/api/log.action?type=" + com.autozi.common.utils.util2.Base64.encode("login");
//    	String com_url = "http://localhost:8000" + "/o2o/api/log.api?type=" + com.qeegoo.o2o.wxby.common.utils.Base64.encode("login");
    	StringBuilder after_url = new StringBuilder();
    	after_url.append(com_url);
    	after_url.append("&key=");
    	after_url.append(encode(loginName, password,TWO_WEEKS_S));
    	after_url.append("&jumpUrl=");
    	String after_jumpUrl = com.autozi.common.utils.util2.Base64.encode(ApplicationProperties.getValue("b2cDomain") + jumpUrl);//对跳转jumpUrl进行加密
//    	String after_jumpUrl = com.qeegoo.o2o.wxby.common.utils.Base64.encode("http://localhost:8000/index.action");//对跳转jumpUrl进行加密
    	after_url.append(after_jumpUrl);
		return HttpClientUtil.getPostUrlAfterKey(after_url.toString(),"");
    }
    
    /**
     * @Description: 拆车件单点登录转化
     * @author: zhiyun.chen
     * 2016-8-11上午09:36:59
     */
    public static String urlEncodeForCCJ(String loginName, String password,String jumpUrl,Integer menuId) {
		String com_url = ApplicationProperties.getValue("chachejianDomain")+ "/o2o/api/log.action?type="+ com.autozi.common.utils.util2.Base64.encode("login");
		StringBuilder after_url = new StringBuilder();
		after_url.append(com_url);
		after_url.append("&key=");
		after_url.append(encode(loginName, password, TWO_WEEKS_S));
		after_url.append("&jumpUrl=");
		String after_jumpUrl = com.autozi.common.utils.util2.Base64.encode(ApplicationProperties.getValue("chachejianDomain") + jumpUrl);// 对跳转jumpUrl进行加密
		after_url.append(after_jumpUrl);
		if(menuId != null){
			after_url.append("&menuId=");
			after_url.append(com.autozi.common.utils.util2.Base64.encode(menuId.toString()));
		}
		return HttpClientUtil.getO2OPostUrlAfterKey(after_url.toString());
	}
    
    /**
     * @Description: 积压件单点登录转化
     * @author: zhiyun.chen
     * 2016-8-11上午09:36:59
     */
    public static String urlEncodeForJYJ(String loginName, String password,String jumpUrl,Integer menuId) {
		String com_url = ApplicationProperties.getValue("jyjDomain")+ "/o2o/api/log.action?type="+ com.autozi.common.utils.util2.Base64.encode("login");
		StringBuilder after_url = new StringBuilder();
		after_url.append(com_url);
		after_url.append("&key=");
		after_url.append(encode(loginName, password, TWO_WEEKS_S));
		after_url.append("&jumpUrl=");
		String after_jumpUrl = com.autozi.common.utils.util2.Base64.encode(ApplicationProperties.getValue("jyjDomain") + jumpUrl);// 对跳转jumpUrl进行加密
		after_url.append(after_jumpUrl);
		if(menuId != null){
			after_url.append("&menuId=");
			after_url.append(com.autozi.common.utils.util2.Base64.encode(menuId.toString()));
		}
		return HttpClientUtil.getO2OPostUrlAfterKey(after_url.toString());
	}

	public static String urlEncodeForCCJLogout() throws Exception{
	    	String com_url = ApplicationProperties.getValue("chachejianDomain") + "/o2o/api/log.action?type=" + com.autozi.common.utils.util2.Base64.encode("logout");
			return HttpClientUtil.getO2OPostUrlAfterKey(com_url);
	    }
	
	public static String urlEncodeForJYJLogout() throws Exception{
    	String com_url = ApplicationProperties.getValue("jyjDomain") + "/o2o/api/log.action?type=" + com.autozi.common.utils.util2.Base64.encode("logout");
		return HttpClientUtil.getO2OPostUrlAfterKey(com_url);
    }
    /**
     * @Description: M站单点登录转化
     * @author: guangyao.lu
     * 2017-4-26上午10:53:59
     */
    public static String urlEncodeForMsite(String loginName, String password,String jumpUrl,Integer menuId) {
		String com_url = ApplicationProperties.getValue("msiteDomain")+ "/o2o/api/log.action?type="+ com.autozi.common.utils.util2.Base64.encode("login");
		StringBuilder after_url = new StringBuilder();
		after_url.append(com_url);
		if(StringUtils.isNotBlank(password)){
			after_url.append("&key=");
			after_url.append(encode(loginName, password, TWO_WEEKS_S));
		}
		after_url.append("&jumpUrl=");
		String after_jumpUrl = com.autozi.common.utils.util2.Base64.encode(ApplicationProperties.getValue("msiteDomain") + jumpUrl);// 对跳转jumpUrl进行加密
		after_url.append(after_jumpUrl);
		if(menuId != null){
			after_url.append("&menuId=");
			after_url.append(com.autozi.common.utils.util2.Base64.encode(menuId.toString()));
		}
		return HttpClientUtil.getO2OPostUrlAfterKey(after_url.toString());
	}
}
