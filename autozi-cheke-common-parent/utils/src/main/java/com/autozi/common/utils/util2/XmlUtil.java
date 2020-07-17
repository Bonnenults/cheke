package com.autozi.common.utils.util2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger("VIN");

	/**
	 * 将vin码查询到的id解析成list
	 * @param xml
	 */
	public static List<String> parseVinXmlForList(String xml,String vin) {
		logger.info("=======#XmlUtil.parseVinXmlForList=======解析报文开始========");
		logger.info("VIN【"+vin+"】；接收到的报文如下："+xml);
		Document doc = null;
		try {
			// 读取并解析XML文档
			// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
			//
			// SAXReader reader = new SAXReader(); 表示你要解析的xml文档
			// Document document = reader.read(new File("User.xml"));
			// 下面的是通过解析xml字符串的
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			@SuppressWarnings("rawtypes")
			Iterator iter = rootElt.element("Body").element("GetCXInfoByVINResponse").element("GetCXInfoByVINResult").elementIterator("string");
			return conver(iter,vin);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("VIN【"+vin+"】；报文解析中出现异常",e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("VIN【"+vin+"】；报文解析中出现异常",e);
		}
		logger.info("=======#XmlUtil.parseVinXmlForList=======解析报文结束========");
		return null;
	}

	private static List<String> conver(@SuppressWarnings("rawtypes") Iterator it,String vin){
		List<String> vins = new ArrayList<String>();
		if(null!= it){
			while (it.hasNext()) {
				Element recordEle = (Element) it.next();
				vins.add(recordEle.getText().trim());
				logger.info("VIN【"+vin+"】转换之后的力洋ID为："+recordEle.getText().trim());
			}
		}else{
			logger.info("VIN【"+vin+"】；收到解析之后的数据为：null，本次解析错误");
		}
		return vins;
	}
	
//	public static void main(String[] args) {
//		StringBuilder soapRequest = new StringBuilder();
//		soapRequest
//				.append("<?xml version='1.0' encoding='utf-8'?>")
//				.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>")
//
//				.append("<soap:Body>")
//				.append("<GetCXInfoByVINResponse xmlns='http://tempuri.org/'>")
//				.append("<GetCXInfoByVINResult>")
//
//				.append("<string>6410</string>")
//				.append("<string>6411</string>")
//				.append("<string>6412</string>")
//				.append("<string>MG005</string>")
//				.append("<string>MG006</string>")
//				.append("<string>2011</string>").append("<string>E5</string>")
//
//				.append("</GetCXInfoByVINResult>")
//				.append("</GetCXInfoByVINResponse>").append("</soap:Body>")
//				.append("</soap:Envelope>");
//
//		List list = parseVinXmlForList(soapRequest.toString());
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i));
//		}
//	}
	
//	public static void main(String[] args) {
//		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ReqData><ReqParam><RtnCode>   00000  </RtnCode><RtnMsg>00</RtnMsg></ReqParam></ReqData>";
//		Document doc = null;
//		try {
//			doc = DocumentHelper.parseText(xmlStr);
//			Element rootElt = doc.getRootElement();
//			System.out.println(rootElt.element("ReqParam").element("RtnCode").getTextTrim());;
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // 将字符串转为XML
//	}
}
