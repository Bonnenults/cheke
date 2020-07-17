package com.autozi.common.web.session.filter;

/**
 * 可以以不同的存储技术实现该接口，比如Cache,db等。然后把它注入给StoreSessionManager
 * @author haocheng
 *
 */
public interface StoreProxy {
	   StoreHttpSession get(String sessionId);
	   void delete(String sessionId);
	   void set(String sessionId,int ext,Object obj);
}
