/**
 * 
 */
package com.autozi.common.search.solr.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author haocheng
 *
 */
@Component
public class SolrServerFactory implements DisposableBean{
	
	private static Logger logger = LoggerFactory.getLogger(SolrServerFactory.class);

	private static Map<String, SolrServer> solrServerMap = Collections.synchronizedMap(new HashMap<String, SolrServer>());
	private static Map<String, CloudSolrServer> cloudSolrServerMap = Collections.synchronizedMap(new HashMap<String, CloudSolrServer>());
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：初始化
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2016-7-5
	 * @param zkHost
	 * @return
	 */
	public static CloudSolrServer getCommonsCloudSolrServer(String zkHost,String defaultCollection){
		CloudSolrServer cloudSolrServer = null;
		String _key = zkHost+"/"+defaultCollection;
		if (!cloudSolrServerMap.containsKey(_key)) {
			try {
				cloudSolrServer = new CloudSolrServer(zkHost);
				cloudSolrServer.setDefaultCollection(defaultCollection);
				cloudSolrServer.setZkClientTimeout(5000);// ms
				cloudSolrServer.setZkConnectTimeout(1000);// ms
				cloudSolrServer.connect();
				if(cloudSolrServer!=null){
					cloudSolrServerMap.put(_key, cloudSolrServer);
					logger.info("Solr服务 " + _key + " 加载完毕");
				}
			} catch (Exception e) {
				logger.warn("SOLR_URL出错！");
				e.printStackTrace();
			}
		}else{
			cloudSolrServer=cloudSolrServerMap.get(_key);
		}
		return cloudSolrServer;
	}

	/**
	 * 获取CommonsHttpSolrServer
	 * 
	 * @param SOLR_URL
	 * @return SolrServer
	 */
	public static SolrServer getCommonsHttpSolrServer(final String SOLR_URL) {
		SolrServer solrServer = null;
		if (!solrServerMap.containsKey(SOLR_URL)) {
			try {
				solrServer = new HttpSolrServer(SOLR_URL);
				initParams((HttpSolrServer)solrServer);
				if (solrServer != null) {
					solrServerMap.put(SOLR_URL, solrServer);
					logger.info("服务 " + SOLR_URL + " 加载完毕");
				}
			} catch (Exception e) {
				logger.warn("SOLR_URL出错！");
				e.printStackTrace();
			}
		}else{
			solrServer=solrServerMap.get(SOLR_URL);
		}
		
		return solrServer;
	}

	private static void initParams(HttpSolrServer server) {
		server.setSoTimeout(5000); // socket read timeout
		server.setConnectionTimeout(1000);
		server.setDefaultMaxConnectionsPerHost(1000);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		server.setAllowCompression(true);
		// UPDATE BY LIHF@QEEGOO 2015-5-25下午8:01:44 START BUG描述：调成3
		server.setMaxRetries(3);
		// UPDATE BY LIHF@QEEGOO 2015-5-25下午8:01:44 END   BUG描述：
	}

	@Override
	public void destroy() throws Exception {
		if(solrServerMap!=null){
			Iterator<String> ite = solrServerMap.keySet().iterator();
			while(ite.hasNext()){
				SolrServer solrServer = solrServerMap.get(ite.next());
				if(solrServer!=null){
					solrServer.shutdown();
				}
			}
		}
		if(cloudSolrServerMap!=null){
			Iterator<String> ite = cloudSolrServerMap.keySet().iterator();
			while(ite.hasNext()){
				CloudSolrServer solrServer = cloudSolrServerMap.get(ite.next());
				if(solrServer!=null){
					solrServer.shutdown();
				}
			}
		}
		
	}

}
