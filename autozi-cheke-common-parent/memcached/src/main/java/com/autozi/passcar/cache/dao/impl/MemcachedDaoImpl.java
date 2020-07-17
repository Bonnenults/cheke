package com.autozi.passcar.cache.dao.impl;

import com.autozi.passcar.cache.dao.BussinessType;
import com.autozi.passcar.cache.dao.IMemcachedDao;
import com.autozi.passcar.cache.memcached.client.B2bGoodsMemClient;
import com.autozi.passcar.cache.memcached.client.B2bMallMemClient;
import com.autozi.passcar.cache.memcached.client.B2bMemClient;
import com.autozi.passcar.cache.memcached.client.B2bPartUserMemClient;
import com.autozi.passcar.cache.memcached.client.B2bSystemMenusClient;
import com.autozi.passcar.cache.memcached.client.UserCenterMemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * memcached缓存实现
 * @author xujinlei
 *
 */
@Component
public class MemcachedDaoImpl implements IMemcachedDao {
	@Autowired
    private UserCenterMemClient userCenterMemClient;
	@Autowired
    private B2bMemClient b2bMemClient;
	@Autowired
	private B2bMallMemClient b2bMallMemClient;
	@Autowired
    private B2bSystemMenusClient b2bSystemMenusClient;
	@Autowired
    private B2bGoodsMemClient b2bGoodsMemClient;
	@Autowired
    private B2bPartUserMemClient b2bPartUserMemClient;
//	@Autowired
//    private B2bTo4plMemClient b2bTo4plMemClient;

	public <T> void set(String key, int expiredTime, T value,BussinessType bt) {
		switch(bt) {
		case UC:
			userCenterMemClient.set(key, expiredTime, value);
			break;
		case B2B:
			b2bMemClient.set(key, expiredTime, value);
			break;
		case B2B_MALL:
			b2bMallMemClient.set(key, expiredTime, value);
			break;
		case B2B_GOODS:
			b2bGoodsMemClient.set(key, expiredTime, value);
			break;
		case B2B_PARTYVSUESR:
			b2bPartUserMemClient.set(key, expiredTime, value);
			break;
		case B2B_SYSTEMMENUS:
			b2bSystemMenusClient.set(key, expiredTime, value);
			break;
//		case B2B_4PL:
//			b2bTo4plMemClient.set(key, expiredTime, value);
//			break;
		default:
			userCenterMemClient.set(key, expiredTime, value);
			break;
		}
	}

	public <T> void set(String key, T value,BussinessType bt) {
		switch(bt) {
		case UC:
			userCenterMemClient.set(key, value);
			break;
		case B2B:
			b2bMemClient.set(key, value);
			break;
		case B2B_MALL:
			b2bMallMemClient.set(key, value);
			break;
		case B2B_GOODS:
			b2bGoodsMemClient.set(key, value);
			break;
		case B2B_PARTYVSUESR:
			b2bPartUserMemClient.set(key, value);
			break;
		case B2B_SYSTEMMENUS:
			b2bSystemMenusClient.set(key, value);
			break;
//		case B2B_4PL:
//			b2bTo4plMemClient.set(key, value);
//			break;
		default:
			userCenterMemClient.set(key, value);
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key,BussinessType bt) {
		switch(bt) {
		case UC:
			return (T)userCenterMemClient.get(key);
		case B2B:
			return (T)b2bMemClient.get(key);
		case B2B_MALL:
			return (T)b2bMallMemClient.get(key);
		case B2B_GOODS:
			return (T)b2bGoodsMemClient.get(key);
		case B2B_PARTYVSUESR:
			return (T)b2bPartUserMemClient.get(key);
		case B2B_SYSTEMMENUS:
			return (T)b2bSystemMenusClient.get(key);
//		case B2B_4PL:
//			return (T)b2bTo4plMemClient.get(key);
		default:
			return (T)userCenterMemClient.get(key);
		}
	}

	public void delete(String key,BussinessType bt) {
		switch(bt) {
		case UC:
			userCenterMemClient.delete(key);
			break;
		case B2B:
			b2bMemClient.delete(key);
			break;
		case B2B_MALL:
			b2bMallMemClient.delete(key);
			break;
		case B2B_GOODS:
			b2bGoodsMemClient.delete(key);
			break;
		case B2B_PARTYVSUESR:
			b2bPartUserMemClient.delete(key);
			break;
		case B2B_SYSTEMMENUS:
			b2bSystemMenusClient.delete(key);
			break;
//		case B2B_4PL:
//			b2bTo4plMemClient.delete(key);
//			break;
		default:
			userCenterMemClient.delete(key);
			break;
		}
	}

}
