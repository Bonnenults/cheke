package com.autozi.common.utils.log;

import com.autozi.common.utils.util1.Converter;


public class TraceLog extends Log {
	
	private final static char COMMA = ',';
	private final static char LEFT = '[';
	private final static char RIGHT = ']';
	
	public TraceLog() {
		super(LOG_TRACE);
	}
	
	public TraceLog(String prefix) {
		super(LOG_TRACE, prefix);
	}

	public void enter(Object cls, String method) {
		enter(cls.getClass(), method);
	}
	
	public void enter(Class<?> cls, String method) {
		debug("Enter "+cls.getName()+"."+method+"()");
	}

	public void exit(Class<?> cls, String method) {
		debug("Exit "+cls.getName()+"."+method+"()");
	}

	public void exit(Object cls, String method) {
		exit(cls.getClass(), method);
	}
	
	/**
	 * 打印交易日志
	 * 
	 * @param settleCode
	 * @param tradeId
	 * @param tradeDate
	 * @param tradeAmount
	 * @param payType
	 * @param payStatus
	 */
	public void printTradeLog(Long tradeId
			,String tradeDate,Double tradeAmount,String payType,String payStatus){
		StringBuffer mpsp = new StringBuffer();
		mpsp.append("结算单号").append(LEFT).append(tradeId).append(RIGHT).append(COMMA);
		mpsp.append("交易订单号").append(LEFT).append(Converter.getTradeId(tradeId)).append(RIGHT).append(COMMA);
		mpsp.append("交易日期").append(LEFT).append(tradeDate).append(RIGHT).append(COMMA);
		mpsp.append("交易金额").append(LEFT).append(tradeAmount).append(RIGHT).append(COMMA);
		mpsp.append("支付方式").append(LEFT).append(payType).append(RIGHT).append(COMMA);
		mpsp.append("支付结果").append(LEFT).append(payStatus).append(RIGHT);
		this.info(mpsp.toString());
	}
}
