package com.autozi.common.utils.chanpay.util;

/**
 *
 * <p>
 * 定义请求的参数名称
 * </p>
 * 
 * @author yanghta@chenjet.com
 * @version $Id: BaseConstant.java, v 0.1 2017-05-03 下午5:25:44
 */
public class ChanPayConstant {

	// 基础参数
	public static final String SERVICE = "Service";
	public static final String VERSION = "Version";
	public static final String PARTNER_ID = "PartnerId";
	public static final String TRADE_DATE = "TradeDate";
	public static final String TRADE_TIME = "TradeTime";
	public static final String INPUT_CHARSET = "InputCharset";
	public static final String SIGN = "Sign";
	public static final String SIGN_TYPE = "SignType";
	public static final String MEMO = "Memo";
	public static final String MD5 = "MD5";
	public static final String RSA = "RSA";
	/**
	 * 畅捷支付平台公钥
	 */
	public static final String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥

	/**
	 * 商户私钥
	 */
	public static final String MERCHANT_PRIVATE_KEY= "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOa2n+rGy0EQhualvVB1OsZx2UHcMFm7U6w/RRquhuA3vmx6GTl3kNM4hFiC0+EFW/Q1WdI7nc3QCsK/E+v5E2sF89ZLOTSmqkufHH6SPJztS0TBqOMjkQj0ujeqfPrx6W0/l5giy9VtNFXfVTLbKgtfiVkFUu7V+T5kcpDJrRDdAgMBAAECgYEApM4p+Azfnm/O3a3hSTskrCMhffFrPH4bLDzaAVBQmpRXW6fwouNtOeTybUOvVNt+Lzl5Gzto419MgIb8FSGMoxibbbUO9EXmR1o9k2ckjdZr9rHOx1GP23H67Ddpsknaau4EnzMz8a5JZdnitpM6o/DoLEji92wYmgf7pmU41GUCQQD0Zn1j+ZwsUGZVLt6FzKQq5ljxqV/fnuzxSsSDRQNuDeszpRAZzOUrjgBULb/+ViMdnOIXSIKjeS2ZxYi7NbnLAkEA8anV6CANbhJc7t1N3dYkZC0KJCA1DMvwv7VB6hxIRhxTSNnFA3M+v2TEoZZrpnMRT449GhY170W+wKjfm6Kq9wJBAJw8FHZfr+VoNDLKNngkHOJLxJof42kJqICLglpEOPAWt7+ZClTj9bBFI5KseGq3V9VrX+DweorUQUFm1ISQ0McCQCSCYQOhnAXOFmYr3vCtwm+z8j1E9F7LjMKZsqyYuz6EVvzBkacUyR0HL0GuRPMvnSTku0xuoJVWdxwKJ0YPDD8CQQDVczxDbQt4C5BetlclIT0/MZnfTvuHCQ/HtfmtZzeuguxofFagmqecA9n8yI7PalIMjgtO4wJH6kbBLD3VLnRr";

	public static String CHARSET = "UTF-8";
	public static String GATEWAY_URL = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do";
	public static String BATCH_FILE_GATEWAY_URL = "https:/pay.chanpay.com/mag-unify/gateway/batchOrder.do";

	//充值银行回调地址
	public static String bankNotifyUrl = "";
	//扫码回调地址
	public static String scpUrl = "";
	//代收付回调地址
	public static String dsfUrl = "";

	public static String PARTNER_ID_VALUE = "200001400164";//商户ID  扫码：200001220037 代收付：200001160096 网银：200001280008
	public static String MCH_ID = PARTNER_ID_VALUE;//商户标识
	public static String TRADE_TYPE = "11";//即时交易 12担保
	public static String WXPAY = "WXPAY";//微信支付
	public static String ALIPAY = "ALIPAY";// 支付宝支付
	public static String SpbillCreateIp = "127.0.0.1";//本系IP

	public static String getBankNotifyUrl() {
		return bankNotifyUrl;
	}

	public static void setBankNotifyUrl(String bankNotifyUrl) {
		ChanPayConstant.bankNotifyUrl = bankNotifyUrl;
	}

	public static String getScpUrl() {
		return scpUrl;
	}

	public static void setScpUrl(String scpUrl) {
		ChanPayConstant.scpUrl = scpUrl;
	}

	public static String getDsfUrl() {
		return dsfUrl;
	}

	public static void setDsfUrl(String dsfUrl) {
		ChanPayConstant.dsfUrl = dsfUrl;
	}

}
