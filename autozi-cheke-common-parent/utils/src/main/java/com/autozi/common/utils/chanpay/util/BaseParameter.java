package com.autozi.common.utils.chanpay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseParameter {
	public Map<String,String> requestBaseParameter(String service,String partnerId){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put(ChanPayConstant.SERVICE, service);// 鉴权绑卡确认的接口名
		origMap.put(ChanPayConstant.VERSION, "1.0");
		origMap.put(ChanPayConstant.PARTNER_ID, partnerId); // 畅捷支付分配的商户号
		origMap.put(ChanPayConstant.TRADE_DATE, new SimpleDateFormat("yyyyMMdd").format(new Date()));
		origMap.put(ChanPayConstant.TRADE_TIME, new SimpleDateFormat("HHmmss").format(new Date()));
		origMap.put(ChanPayConstant.INPUT_CHARSET, ChanPayConstant.CHARSET);// 字符集
		origMap.put(ChanPayConstant.MEMO, "");// 备注
		return origMap;
		
	}
}
