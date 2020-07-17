package com.autozi.common.utils.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.autozi.common.utils.util2.XmlUtil;

public class VinUtil {
	private static Logger logger = LoggerFactory.getLogger("VIN");

	/**
	 * 验证是否是vin码,17位，不含I,Q,O,中间没有空格等特殊字符
	 * 
	 * @param vin
	 * @return
	 */
	public static boolean isVin(String vin) {
		if (StringUtils.isNotBlank(vin)) {
			vin = vin.trim();
			if (!length17(vin)) {
				return false;
			}
			if (!informal(vin)) {
				return false;
			}
			if(specifyLetters(vin)){
				return false;
			}
			if(!area(vin)){
				return false;
			}
//			if(!isUpperCase(vin)){
//				return false;
//			}

			return true;// 是正确vin码
		}

		return false;
	}

	/**
	 * 通过第一个字母判断是否是正确的vin码
	 * @param str
	 * @return true/false
	 * 
	 * 1 美国 J 日本 S 英国
	 * 2 加拿大 K 韩国 T 瑞士
	 * 3 墨西哥 L 中国 V 法国
	 * R 台湾 W 德国
	 * 6 澳大利亚 Y 瑞典
	 * 9 巴西 Z 意大利
	 */
	private static boolean area(String str){
		String regEx = "[1JS2KT3LVRW6Y9Z]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str.substring(0,1));
		return m.find();
	}
	/**
	 * 判断是否有不规则字符存在
	 * 
	 * @param str
	 * @return 存在返回true
	 */
	private static boolean informal(String str) {
		// 只允许字母和数字
		String regEx = "^[A-Za-z0-9]+$";

		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）_-——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否含有指定的字母
	 * @param str
	 * @return
	 */
	private static boolean specifyLetters(String str){
		//vin码中不含有这三个字母
		String regEx = "[iqoIQO]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
	/**
	 * 判断vin码是否是17位
	 * 
	 * @param vin
	 * @return
	 */
	private static boolean length17(String vin) {
		if (vin.trim().length() == 17) {
			return true;
		}
		return false;
	}

	/**
	 * 拆分出每一个字母判断是否是大写，任何一个字母不是大写就作为非vin码处理
	 * @param vin
	 * @return是返回true否则false
	 */
	private static boolean isUpperCase(String vin){
		String [] letters = splitStr(vin);
		for(int i=0;i<letters.length;i++){
			if(Character.isLowerCase(letters[i].charAt(0))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 拆分出字母
	 * @param vin
	 * @return
	 */
	private static String[] splitStr(String vin){
		List<String> letters = new ArrayList<String>();
		for(int i=0;i<vin.length();i++){
			String s = vin.substring(i, i+1);
			if(!NumberUtils.isNumber(s)){
				letters.add(s);
			}
		}
		return letters.toArray(new String[letters.size()]);
	}
	
	public static List<String> getVinIds(String vin) throws Exception{
		logger.info("============#VinUtil.getVinIds============VIN【"+vin+"】==START==========");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.info("计时开始，本次查询VIN为："+vin);
		if(isVin(vin)){
			List<String> ips = new ArrayList<String>();
//			ips.add("58.221.57.103");
			ips.add("115.159.106.149");
			// DELETE BY LIHF@QEEGOO 2015-9-26上午9:57:25 START BUG描述：经过与力洋技术沟通，删除的IP都为无用的服务器
//			ips.add("180.150.165.73");
//			ips.add("118.126.9.189");
//			ips.add("101.226.241.105");
			// DELETE BY LIHF@QEEGOO 2015-9-26上午9:57:25 END   BUG描述：
			StringBuilder soapRequest = new StringBuilder();
			soapRequest.append("<?xml version='1.0' encoding='utf-8'?>")
					.append("<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>")

					.append("<soap:Body>")
					.append("<GetCXInfoByVIN xmlns='http://tempuri.org/'>")

					.append("<vin>"+vin+"</vin>")
					.append("</GetCXInfoByVIN>").append("</soap:Body>")
					.append("</soap:Envelope>");

			String contentType = "text/xml; charset=utf-8";
			for(int i=0;i<ips.size();i++){
				logger.info("========本次向IP【"+ips.get(i)+"】，发起查询=========开始========");
				logger.info("本次【IP："+ips.get(i)+"】查询报文为："+soapRequest.toString());
				StopWatch stopWatch2 = new StopWatch();
				stopWatch2.start();
				String serviceEpr = "http://"+ips.get(i)+":8088/webService/TCIDCXService.asmx";
				String rs = HttpClientUtil.callAspWebService(soapRequest.toString(), serviceEpr,contentType);
				stopWatch2.stop();
				logger.info("本次【IP："+ips.get(i)+"】查询结束，共计用时"+stopWatch2.getTotalTimeSeconds()+"秒，返回的报文为："+rs);
				if(!rs.equals("3")
						&& !rs.startsWith("errorMessage 1: ")
						&& !rs.startsWith("errorMessage 2: ")){
					List<String> result = XmlUtil.parseVinXmlForList(rs,vin);
					stopWatch.stop();
					logger.info("============#VinUtil.getVinIds============VIN【"+vin+"】共计用时："+stopWatch.getTotalTimeSeconds()+"秒==END==========");
					return result;
				}
			}
		}else{
			logger.info("VIN【"+vin+"】不是合法有效的数据。");
		}
		stopWatch.stop();
		logger.info("============#VinUtil.getVinIds============VIN【"+vin+"】共计用时："+stopWatch.getTotalTimeSeconds()+"秒==END==========");
		return new ArrayList<String>();
	}
	public static void main(String[] args) {
		// String vin = "11111111111111111";
		// System.out.println(isVin(vin));
		List<String> a = new ArrayList<String>();
		a.add("!");
		a.add("@");
		a.add("#");
		a.add("$");
		a.add("%");
		a.add("^");
		a.add("&");
		a.add("*");
		a.add("(");
		a.add(")");
		a.add("_");
		a.add("-");
		a.add("+");
		a.add("=");
		a.add("|");
		a.add("}");
		a.add("{");
		a.add("[");
		a.add("]");
		a.add(":");
		a.add("'");
		a.add(";");
		a.add("<");
		a.add(">");
		a.add("?");
		a.add("/");
		a.add(".");
		a.add(",");
		a.add("`");
		a.add("~");
		a.add(" ");

//		for (int i = 0; i < a.size(); i++) {
//			System.out.println(informal(a.get(i)));
//		}
//		System.out.println(specifyLetters("23q"));
		
		//1JS2KT3LVRW6Y9Z
//		System.out.println(area("1Z"));
		
//		System.out.println(isVin("LSJW26H35BS147599"));
		
//		System.out.println(isUpperCase("LSJW26H35BS147599"));
		try {
			List<String> list =  getVinIds("LGBH72E06GY037366");
			if (list.size() > 0) {
				System.out.println(list.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("Lvshcfaexaf494327".toUpperCase());
	}
}
