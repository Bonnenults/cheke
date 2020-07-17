package com.autozi.passcar.cache.memcached.client;

import com.autozi.passcar.cache.memcached.PropertyUtils;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Date;

/**
 * Created by 刘兵 on 2017/7/11.
 */
public class B2bSystemMenusClient {
    private static String memcachedNodes = PropertyUtils.getValue("com.autozi.b2b.memcached.servers");
    private static String poolName = "com.autozi.b2b.systemmenu.memcached.servers";
    private int initConn = 10;
    private int minConn = 5;
    private int maxConn = 300;
    private int socketTimeOut = 1000;
    private int socketConnectTimeOut = 1000;

    private static MemCachedClient mcc = null;
    private static SockIOPool pool = null;

    private static B2bSystemMenusClient instatic = new B2bSystemMenusClient();

    public B2bSystemMenusClient(){
        if(mcc==null){
            createMcc();
        }
    }

    public static B2bSystemMenusClient getInstance(){
        return instatic;
    }

    /**
     * 初始化memcached
     *
     */
    public void createMcc(){
        if(mcc==null){
            String[] servers = memcachedNodes.split(",");
            pool = SockIOPool.getInstance(poolName);
            pool.setServers(servers);
            pool.setFailover(true);
            pool.setInitConn(this.getInitConn());
            pool.setMinConn(this.getMinConn());
            pool.setMaxConn(this.getMaxConn());
            pool.setMaintSleep(30);
            pool.setNagle(false);
            pool.setSocketTO(this.getSocketTimeOut());
            pool.setSocketConnectTO(this.getSocketConnectTimeOut());
            pool.setAliveCheck(true);
            pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
            pool.initialize();
            mcc = new MemCachedClient(poolName, false);
        }
    }

    public void set(String key, Object value) {
        Date expiryDate = new Date(3600*1000);
        mcc.set(key, value,expiryDate);
    }

    public void set(String key, int expiredTime, Object value) {
        Date expiryDate = new Date(expiredTime*1000);
        mcc.set(key, value, expiryDate);
    }

    public Object get(String key) {
        return mcc.get(key);
    }
    public void delete(String key) {
        Object obj = get(key);
        if(obj != null) {
            mcc.delete(key);
        }
    }

    public int getInitConn() {
        return initConn;
    }

    public void setInitConn(int initConn) {
        this.initConn = initConn;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getSocketConnectTimeOut() {
        return socketConnectTimeOut;
    }

    public void setSocketConnectTimeOut(int socketConnectTimeOut) {
    		this.socketConnectTimeOut = socketConnectTimeOut;
    	}
}
