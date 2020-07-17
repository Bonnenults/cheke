package com.autozi.common.utils.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.utils.util1.MD5;


/**
 * @Description: HttpClientUtil
 * @author zhiyun.chen
 * @dateTime 2013-4-18下午06:18:44
 */
public class HttpClientUtil {
	private static final int CONNECTION_TIMEOUT = 3000;
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	public static final int URL_TYPE_O2O = 1; // o2o请求
	public static final int URL_TYPE_B2C = 2; // b2c请求

	/**
	 * @Description: get请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:03:49
	 */
	public final static String httpGet(String url, String params)
			throws Exception {
		url = getO2OPostUrlAfterKey(url);// 加上时间截与key
		String response = null; // 返回信息
		if (null != params && !params.equals("")) {
			url += "&" + params;
		}
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod httpGet = new GetMethod(url);
		// 设置超时时间
		httpGet.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				CONNECTION_TIMEOUT);
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

	/**
	 * @Description: post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:05:41
	 */
	public final static String httpPost(String url, Map<String, String> params)
			throws Exception {
		url = getO2OPostUrlAfterKey(url);// 加上时间截与key
		String response = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod httpPost = new PostMethod(url);
		// Post方式我们需要配置参数
		httpPost.addParameter("Connection", "Keep-Alive");
		// httpPost.addParameter("Charset", "UTF-8");
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				CONNECTION_TIMEOUT);
		if (null != params && params.size() != 0) {
			// 设置需要传递的参数，NameValuePair[]
			httpPost.setRequestBody(buildNameValuePair(params));
		}
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode == HttpStatus.SC_OK) {
				// UPDATE BY LIHF@QEEGOO 2014-6-4 上午12:15:29 START BUG描述：乱码问题修改
//				InputStreamReader inputStreamReader = new InputStreamReader(httpPost.getResponseBodyAsStream());
//				String encoding = inputStreamReader.getEncoding();
//				BufferedReader reader = new BufferedReader(inputStreamReader);
//				String line = null;
//				StringBuffer sb = new StringBuffer();
//				while((line=reader.readLine())!=null){
//					sb.append(line);
//				}
//				response = new String(sb.toString().getBytes(encoding),"utf-8");
				InputStream inputStream = httpPost.getResponseBodyAsStream();
				response = getData(inputStream);
				// UPDATE BY LIHF@QEEGOO 2014-6-4 上午12:15:29 END
			} else {
				logger.info("Post Method Statuscode : " + statusCode);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return response;
	}

	/**
	 * @Description:组装数据
	 * @param params
	 * @return
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:03:25
	 */
	private final static NameValuePair[] buildNameValuePair(
			Map<String, String> params) {
		int size = params.size();
		NameValuePair[] pair = new NameValuePair[size];
		int i = 0;
		for (String key : params.keySet()) {
			pair[i] = new NameValuePair(key, params.get(key));
			i++;
		}
		return pair;
	}

	/**
	 * @Description: 获取返回数据
	 * @param inputStream
	 * @return
	 * @throws Exception
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:00:06
	 */
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
			data = new String(bytes,"utf-8");
		} catch (IOException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			outputStream.close();
		}
		return data;
	}

	/**
	 * @Description: postUrl加上时间截与key
	 * @param postUrl
	 * @user zhiyun.chen
	 * @dateTime 2013-7-25上午10:23:26
	 */
	public static String getO2OPostUrlAfterKey(String postUrl) {
		long nowTime = System.currentTimeMillis() / 1000;
		if(postUrl.indexOf("?") == -1){
			postUrl = postUrl + "?qeegooTime=" + nowTime;// 加时间截
		}else{
			postUrl = postUrl + "&qeegooTime=" + nowTime;
		}
		String qeegooKey = MD5.getMD5(postUrl + URLCheckFilter.key);
		postUrl = postUrl + "&qeegooKey=" + qeegooKey;// 加key
		return postUrl;
	}

	/**
	 * @Description: postUrl加上时间截与key（b2c用，目前b2c报表接口用）
	 * @param postUrl
	 * @user zhiyun.chen
	 * @dateTime 2013-8-5上午10:23:26
	 */
	public static String getB2CPostUrlAfterKey(String postUrl) {
		long nowTime = System.currentTimeMillis() / 1000;
		if (postUrl.contains("?")) {
			postUrl = postUrl + "&qeegooTime=" + nowTime;// 加时间截
		} else {
			postUrl = postUrl + "?" + "qeegooTime=" + nowTime;// 加时间截
		}
		String qeegooKey = MD5.getMD5(postUrl + URLCheckFilter.key);
		postUrl = postUrl + "&qeegooKey=" + qeegooKey;// 加key
		return postUrl;
	}

	/**
	 * @Description: postUrl加上时间截与key（b2c用，目前b2c报表接口用）
	 * @param postUrl
	 * @user zhiyun.chen
	 * @dateTime 2013-8-5上午10:23:26
	 */
	public static String getXMZPostUrlAfterKey(String postUrl,String jsonName,String jsonparam) {
		long nowTime = System.currentTimeMillis() / 1000;
		if (postUrl.contains("?")) {
			postUrl = postUrl + "&qeegooTime=" + nowTime;// 加时间截
		} else {
			postUrl = postUrl + "?" + "qeegooTime=" + nowTime;// 加时间截
		}
		String qeegooKey = MD5.getMD5(postUrl + URLCheckFilter.key);
		postUrl = postUrl + "&qeegooKey=" + qeegooKey;// 加key
		//postUrl = postUrl + "&"+jsonName+"=" +jsonparam;
		return postUrl;
	}

	/**
	 * @Description: postUrl加上时间截与key
	 * @param postUrl
	 * @user zhiyun.chen
	 * @dateTime 2013-7-25上午10:23:26
	 */
	public static String getPostUrlAfterKey(String postUrl, String connector) {
		long nowTime = System.currentTimeMillis() / 1000;
		if (StringUtils.isNotBlank("connector")
				&& connector.equalsIgnoreCase("&")) {
			postUrl = postUrl + "&qeegooTime=" + nowTime;// 加时间截
		} else {
			postUrl = postUrl + "/qeegooTime/" + nowTime;// 加时间截
		}

		String qeegooKey = MD5.getMD5(postUrl + URLCheckFilter.key);
		if (StringUtils.isNotBlank("connector")
				&& connector.equalsIgnoreCase("&")) {
			postUrl = postUrl + "&qeegooKey=" + qeegooKey;// 加key
		} else {
			postUrl = postUrl + "/qeegooKey/" + qeegooKey;// 加key
		}
		return postUrl;
	}

	/**
		 * @Description: postUrl加上时间截与key（b2c用，目前b2c报表接口用）
		 * @param postUrl
		 * @user zhiyun.chen
		 * @dateTime 2013-8-5上午10:23:26
		 */
		public static String getPostUrlAfterKey(String postUrl) {
			long nowTime = System.currentTimeMillis() / 1000;
			if (postUrl.contains("?")) {
				postUrl = postUrl + "&qeegooTime=" + nowTime;// 加时间截
			} else {
				postUrl = postUrl + "?" + "qeegooTime=" + nowTime;// 加时间截
			}
			String qeegooKey = MD5.getMD5(postUrl + URLCheckFilter.key);
			postUrl = postUrl + "&qeegooKey=" + qeegooKey;// 加key
			return postUrl;
		}

	/**
	 * 重构Post请求，需要传参数UrlType(默认为请求o2o)
	 * 
	 * @user zhiyun.chen
	 * @dateTime 2013-8-5下午05:43:46
	 */
	public final static String httpPost(String url, Map<String, String> params,
			int urlType) throws Exception {
		if (urlType == URL_TYPE_B2C) {
			url = getB2CPostUrlAfterKey(url);// 加上时间截与key
		} else {
			url = getO2OPostUrlAfterKey(url);// 加上时间截与key
		}
		String response = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod httpPost = new PostMethod(url);
		// Post方式我们需要配置参数
		httpPost.addParameter("Connection", "Keep-Alive");
		// httpPost.addParameter("Charset", "UTF-8");
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				CONNECTION_TIMEOUT);
		if (null != params && params.size() != 0) {
			// 设置需要传递的参数，NameValuePair[]
			httpPost.setRequestBody(buildNameValuePair(params));
		}
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = httpPost.getResponseBodyAsStream();
				response = getData(inputStream);
			} else {
				logger.info("Post Method Statuscode : " + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return response;
	}

	/**
	 * 	StringBuilder soapRequest = new StringBuilder();
		soapRequest.append("<?xml version='1.0' encoding='utf-8'?>")
				.append("<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>")

				.append("<soap:Body>")
				.append("<GetCXInfoByVIN xmlns='http://tempuri.org/'>")

				.append("<vin>LSJW26H35BS147599</vin>")
				.append("</GetCXInfoByVIN>").append("</soap:Body>")
				.append("</soap:Envelope>");

		String serviceEpr = "http://58.221.57.103:8088/webService/TCIDCXService.asmx";
		String contentType = "text/xml; charset=utf-8";
	 * @param soapRequest
	 * @param serviceEpr
	 * @param contentType
	 * @return
	 */
	public static String callAspWebService(String soapRequest, String serviceEpr,
			String contentType) {
		// UPDATE BY LIHF@QEEGOO 2015-6-8下午5:30:46 START BUG描述：资源释放
		PostMethod postMethod = new PostMethod(serviceEpr);
		// 设置POST方法请求超时
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 3000);
		InputStream inputStream = null;
		HttpClient httpClient = null;
		try {
			byte[] b = soapRequest.getBytes("utf-8");
			inputStream = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(inputStream,b.length, contentType);
			postMethod.setRequestEntity(re);

			httpClient = new HttpClient();
			HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(3000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3000);
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK){
				throw new IllegalStateException("调用webservice错误 : "
						+ postMethod.getStatusLine());
			}

			String soapRequestData = postMethod.getResponseBodyAsString();
			return soapRequestData;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "errorMessage 1: " + e.getMessage();
		} catch (HttpException e) {
			e.printStackTrace();
			return "errorMessage 2: " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "3";//ip不正确或者连接超时在这儿报错，可以通过它判断使用其他地址重连
		} finally {
			postMethod.releaseConnection();
			if(inputStream!=null){
				try{
					inputStream.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		// UPDATE BY LIHF@QEEGOO 2015-6-8下午5:30:46 END   BUG描述：
	}
	
	/**
	 * @Description: post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:05:41
	 */
	public final static String httpPost(String url, String jsonparam)
			throws Exception {
		url = getB2CPostUrlAfterKey(url);// 加上时间截与key
		String response = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod httpPost = new PostMethod(url);
		// Post方式我们需要配置参数
		httpPost.addParameter("Connection", "Keep-Alive");
		// httpPost.addParameter("Charset", "UTF-8");
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				CONNECTION_TIMEOUT);
//		if (null != params && params.size() != 0) {
//			// 设置需要传递的参数，NameValuePair[]
//			httpPost.setRequestBody(buildNameValuePair(params));
//		}
		byte b[] = jsonparam.getBytes("utf-8");
		//byte b[] = jsonparam.getBytes();//把字符串转换为二进制数据  
        RequestEntity requestEntity = new ByteArrayRequestEntity(b);  
		httpPost.setRequestEntity(requestEntity);// 设置数据 
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = httpPost.getResponseBodyAsStream();
				response = getData(inputStream);
			} else {
				logger.info("Post Method Statuscode : " + statusCode);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return response;
	}

	/**
	 * @Description: post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 * @user zhiyun.chen
	 * @dateTime 2013-4-18下午06:05:41
	 */
	public final static String xmzHttpPost(String url, String jsonparam,String jsonName)
			throws Exception {
//		url = getXMZPostUrlAfterKey(url,jsonName,jsonparam);// 加上时间截与key
		String response = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod httpPost = new PostMethod(url);
		// Post方式我们需要配置参数
		httpPost.addParameter("Connection", "Keep-Alive");
		// httpPost.addParameter("Charset", "UTF-8");
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				CONNECTION_TIMEOUT);

		httpPost.getParams().setParameter(jsonName,jsonparam);
		httpPost.addParameter(jsonName,jsonparam);

		Map<String, String> params = new HashMap<String, String>();
		params.put(jsonName,jsonparam);
		if (null != params && params.size() != 0) {
			// 设置需要传递的参数，NameValuePair[]
			httpPost.setRequestBody(buildNameValuePair(params));
		}
		byte b[] = jsonparam.getBytes("utf-8");
		//byte b[] = jsonparam.getBytes();//把字符串转换为二进制数据
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);
//		httpPost.setRequestEntity(requestEntity);// 设置数据
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = httpPost.getResponseBodyAsStream();
				response = getData(inputStream);
			} else {
				logger.info("Post Method Statuscode : " + statusCode);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return response;
	}
	
	/**
	 * 获取客户端IP
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	  * 获取MAC地址
	  *
	  * @return 返回MAC地址
	  */
	 public static String getMacAddress(String ip){
		 String os = getOSName();
		 String macAddress = "";
		 if(os.startsWith("windows")){
			 macAddress = getMacInWindows(ip).trim();
		 }else if(os.startsWith("linux")){
			 macAddress = getMacInLinux(ip).trim();
		 }
	     return macAddress;
	 }
	 
	 /**
	 * @param ip  目标ip
	 * @return Mac Address
	 */

	public static String getMacInWindows(final String ip){
	   String result = "";
	   String[] cmd = {"cmd","/c","ping " + ip};
	   String[] another = {"cmd","/c","arp -a"};
	   String cmdResult = callCmd(cmd,another);
	   result = filterMacAddress(ip,cmdResult,"-");
	   return result;
	}
	
	public static String callCmd(String[] cmd) {
		  String result = "";
		  String line = "";
		    try {
		        Process proc = Runtime.getRuntime().exec(cmd);
		        InputStreamReader is = new InputStreamReader(proc.getInputStream());
		        BufferedReader br = new BufferedReader (is);
		        while ((line = br.readLine ()) != null) {
		             result += line;
		        }
		   }catch(Exception e) {
		        e.printStackTrace();
		   }
		      return result;
		}
	/**
	 * @param cmd
	 *            第一个命令
	 * @param another
	 *            第二个命令
	 * @return 第二个命令的执行结果
	 */

	public static String callCmd(String[] cmd,String[] another) {
	   String result = "";
	   String line = "";
	   try {
	      Runtime rt = Runtime.getRuntime();
	      Process proc = rt.exec(cmd);
	      proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
	      proc = rt.exec(another);
	      InputStreamReader is = new InputStreamReader(proc.getInputStream());
	      BufferedReader br = new BufferedReader (is);
	      while ((line = br.readLine ()) != null) {
	         result += line;
	      }
	   }catch(Exception e) {
	        e.printStackTrace();
	   }
	      return result;
	}
	 /**
	  * @param ip
	  *            目标ip
	  * @return Mac Address
	  */
	 public static String getMacInLinux(final String ip){ 
	     String result = ""; 
	     String[] cmd = {"/bin/sh","-c","ping " +  ip + " -c 2 && arp -a" }; 
	     String cmdResult = callCmd(cmd); 
	     result = filterMacAddress(ip,cmdResult,":"); 
	     return result; 
	 }  
	 
	 /**
	 * @param ip  目标ip,一般在局域网内
	 * @param sourceString 命令处理的结果字符串
	 * @param macSeparator mac分隔符号
	 * @return mac地址，用上面的分隔符号表示
	 */

	public static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) {
	   String result = "";
	   String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
	   Pattern pattern = Pattern.compile(regExp);
	   Matcher matcher = pattern.matcher(sourceString);
	   while(matcher.find()){
	     result = matcher.group(1);
	     if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
	        break; // 如果有多个IP,只匹配本IP对应的Mac.
	     }
	   }
	    return result;
	}
	
	/**
	 * 获取客户端操作系统
	 */
	public static String getRemoteOS(HttpServletRequest request) {
		StringBuilder userAgent = new StringBuilder("[");
	    userAgent.append(request.getHeader("User-Agent"));
	    userAgent.append("]");
	    int indexOfMac = userAgent.indexOf("Mac OS X");
	    int indexOfWindows = userAgent.indexOf("Windows NT");
	    boolean isMac = indexOfMac > 0;
	    boolean isWindows = indexOfWindows > 0;
	    boolean isLinux = userAgent.indexOf("Linux") > 0;
	    String os = "";
	    if (isMac) {
	        os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxx".length());
	    } else if (isLinux) {
	        os = "Linux";
	    } else if (isWindows) {
	        os = "Windows ";
	        String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
	                + "Windows NTx.x".length());
	        if ("5.0".equals(version.trim())) {
	            os += "2000";
	        } else if ("5.1".equals(version.trim())) {
	            os += "XP";
	        } else if ("5.2".equals(version.trim())) {
	            os += "2003";
	        } else if ("6.0".equals(version.trim())) {
	            os += "Vista";
	        } else if ("6.1".equals(version.trim())) {
	            os += "7";
	        } else if ("6.2".equals(version.trim())) {
	            os += "8";
	        } else if ("6.3".equals(version.trim())) {
	            os += "8.1";
	        }
	    }
	    return os;
	}
	
	/**
	 * 获取客户端浏览器
	 */
	public static String getRemoteBrowser(HttpServletRequest request) {
		StringBuilder userAgent = new StringBuilder("[");
	    userAgent.append(request.getHeader("User-Agent"));
	    userAgent.append("]");
	    int indexOfWindows = userAgent.indexOf("Windows NT");
	    int indexOfIE = userAgent.indexOf("MSIE");
	    int indexOfIE11 = userAgent.indexOf("rv:");
	    int indexOfFF = userAgent.indexOf("Firefox");
	    int indexOfSogou = userAgent.indexOf("MetaSr");
	    int indexOfChrome = userAgent.indexOf("Chrome");
	    int indexOfSafari = userAgent.indexOf("Safari");
	    boolean isWindows = indexOfWindows > 0;
	    boolean containIE = indexOfIE > 0 || (isWindows && (indexOfIE11 > 0));
	    boolean containFF = indexOfFF > 0;
	    boolean containSogou = indexOfSogou > 0;
	    boolean containChrome = indexOfChrome > 0;
	    boolean containSafari = indexOfSafari > 0;
	    String browser = "";
	    if (containSogou) {
	        if (containIE) {
	            browser = "搜狗" + userAgent.substring(indexOfIE, indexOfIE + "MSIE x.x".length());
	        } else if (containChrome) {
	            browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
	        }
	    } else if (containChrome) {
	        browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
	    } else if (containSafari) {
	        int indexOfSafariVersion = userAgent.indexOf("Version");
	        browser = "Safari "
	                + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x".length());
	    } else if (containFF) {
	        browser = userAgent.substring(indexOfFF, indexOfFF + "Firefox/xx".length());
	    } else if (containIE) {
	        if (indexOfIE11 > 0) {
	            browser = "MSIE 11";
	        } else {
	            browser = userAgent.substring(indexOfIE, indexOfIE + "MSIE x.x".length());
	        }
	    }
	    return browser;
	}
	
	/**
	 * 获取客户端浏览器(true:是移动设备，false：不是)
	 */
	public static boolean isMobileDevice(HttpServletRequest request)
	{
		boolean flag = false;
		String agent = request.getHeader("user-agent");
		String[] keywords = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };
		 
		//排除 Windows 桌面系统
		if (!agent.contains("Windows NT") || (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;"))){
		//排除 苹果桌面系统
			if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
				for(String item : keywords){
					if (agent.contains(item))
					{
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
//	public static void main(String[] args) throws Exception {
//		StringBuilder soapRequest = new StringBuilder();
//		soapRequest.append("<?xml version='1.0' encoding='utf-8'?>")
//				.append("<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>")
//
//				.append("<soap:Body>")
//				.append("<GetCXInfoByVIN xmlns='http://tempuri.org/'>")
//
//				.append("<vin>LSJW26H35BS147599</vin>")
//				.append("</GetCXInfoByVIN>").append("</soap:Body>")
//				.append("</soap:Envelope>");
//
//		String serviceEpr = "http://58.221.57.103:8088/webService/TCIDCXService.asmx";
//		String contentType = "text/xml; charset=utf-8";
//
//		System.out.println(callAspWebService(soapRequest.toString(), serviceEpr,
//				contentType));
//
//	}
}
