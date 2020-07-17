package com.autozi.common.utils.util1;

import com.autozi.common.core.utils.ApplicationContextProvider;
import com.autozi.passcar.cache.memcached.client.B2bMemClient;

/**
 * 利用真实供应商ID+订单号ID在memcached里记录对应库存数据是否正在处理
 * 如果正在处理，则别的业务操作同一个真实供应商ID+订单号ID时报错。
 * @author wei.wang
 *
 */
public class SettleLockUtil {
	/**
	 * 结算锁前缀
	 */
	private static final String LOCK_TYPE_SETTLE ="settle_";
	
	/**
	 * memcached实例
	 */
	private static B2bMemClient b2bMemClient=ApplicationContextProvider.getBean(B2bMemClient.class);
	
	/**
	 * 开启业务，返回memcached里存储的业务键
	 * @param realSupplierId
	 * @param orderId
	 */
	public static String startBusiness(Long realSupplierId,Long orderId) {
		//参数校验
		if(realSupplierId==null||realSupplierId.longValue()==0L||orderId==null||orderId.longValue()==0L){
			throw new RuntimeException("无效的真实供应商【"+realSupplierId+"】或订单【"+orderId+"】信息!");
		}
		String key= LOCK_TYPE_SETTLE +realSupplierId+"_"+orderId;
		//校验键
		Object value=b2bMemClient.get(key);
		if(value!=null &&value.toString().equals(key)){
			throw new RuntimeException("真实供应商【"+realSupplierId+"】，订单【"+orderId+"】生成供应商账单业务正在处理，请稍候...");
		}
		//写入键
		b2bMemClient.set(key,10, key);
		//返回键
		return key;
	}
	/**
	 * 业务完成后，释放锁
	 * @param key
	 */
	public static void endBusiness(String key) {
		//校验键
		if(org.apache.commons.lang.StringUtils.isBlank(key)){
			throw new RuntimeException("无效的参数【SettleLockUtil.endBusiness】!");
		}
		//删除键
		b2bMemClient.delete(key);
	}
}
