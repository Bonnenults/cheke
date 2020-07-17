package com.autozi.common.utils.util1;

import com.autozi.common.core.utils.ApplicationContextProvider;
import com.autozi.passcar.cache.memcached.client.B2bMemClient;

/**
 * 利用仓库+商品在memcached里记录对应库存数据是否正在处理
 * 如果正在处理，则别的业务操作同一个商品+仓库时报错。
 * @author Administrator
 *
 */
public class StockLockUtil {
	/**
	 * 库存锁前缀
	 */
	private static final String LOCK_TYPE_STOCK="stock_";
	
	/**
	 * memcached实例
	 */
	private static B2bMemClient b2bMemClient=ApplicationContextProvider.getBean(B2bMemClient.class);
	
	/**
	 * 开启业务，返回memcached里存储的业务键
	 * @param goodsId
	 * @param wareHouseId
	 */
	public static String startBusiness(Long goodsId,Long wareHouseId) {
		//参数校验
		if(goodsId==null||goodsId.longValue()==0L||wareHouseId==null||wareHouseId.longValue()==0L){
			throw new RuntimeException("无效的商品【"+goodsId+"】或仓库【"+wareHouseId+"】信息!");
		}
		String key=LOCK_TYPE_STOCK+goodsId+"_"+wareHouseId;
		//校验键
		Object value=b2bMemClient.get(key);
		if(value!=null &&value.toString().equals(key)){
			throw new RuntimeException("商品【"+goodsId+"】，仓库【"+wareHouseId+"】业务正在处理，请稍候...");
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
		if(key==null||"".equals(key.trim())){
			throw new RuntimeException("无效的参数【StockLockUtil.endBusiness】!");
		}
		//删除键
		b2bMemClient.delete(key);
	}
}
