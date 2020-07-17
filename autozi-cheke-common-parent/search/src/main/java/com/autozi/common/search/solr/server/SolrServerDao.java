/**
 * 
 */
package com.autozi.common.search.solr.server;

import java.util.List;

import com.autozi.common.dal.entity.IdEntity;
import com.autozi.common.search.solr.SolrClient;

/**
 * @author haocheng
 *
 */

public class SolrServerDao {
   // private static Logger logger = LoggerFactory.getLogger(SolrServerDao.class);
    
   // private final static String idName=ISolrIndex.uniqueKey; 
    
    private static SolrClient solrClient = new SolrClient();
    
    private String url;
    private boolean enabled = false;

	
	
	/**
	 * 提交数据
	 * @param obj
	 * @param server
	 */
	public void commitObject(Object obj) {
//		if(obj instanceof ISolrIndex){
			boolean changed = write((IdEntity)obj);
			if (changed) {
				commit();
			}
//		}
	}
	
	public <T extends IdEntity> void commit() {
		solrClient.commit();
	}
	
	public void optimize() {
		solrClient.optimize();
	}
	
	/**
	 * 提交数据采用批量提交
	 * @param objectList
	 * @param server
	 */
	public <T extends IdEntity> void commitList(List<T> objectList) {
		boolean changed = false;
		for (T obj : objectList) {
			changed = write((IdEntity)obj);
		}
		if (changed) {
			commit();
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @param idName
	 * @param server
	 */
	public boolean update(final IdEntity obj) {
		return write((IdEntity)obj);
	}
	
	public boolean write(IdEntity entity) {
		
		return false;
		
	}

	/**
	 * 删除给出的一个对象 
	 * @param obj
	 * @param idName
	 * @param server
	 */
	public <T extends IdEntity> void deleteByExample(T obj) {
		deleteById(obj.getClass(), obj.getId());
	}
	
	/**
	 * 删除数据
	 * @param id     Id值
	 * @param idName Id名称
	 * @param server
	 */
	public <T extends IdEntity> void deleteById(Class<T> cls, final Long id) {
		solrClient.deleteById(cls.getName(), id);
		
	}
	
	/**
	 * 批量删除数据
	 * 注意：该数组元素必须不大于1024或者去更改solrconfig.xml中的参数，参数如下：
	 * &lgmaxBooleanClauses&gt1024&lg/maxBooleanClauses&gt
	 * 
	 * @param idArrays Id数组
	 * @param idName   Id名称
	 * @param server
	 */
	public <T extends IdEntity> void deleteByIds(Class<T> cls, final Long[] ids) {
		solrClient.deleteByIds(cls.getName(), ids);
		commit();
	}
	
	/**
	 * 删除所有元素
	 * 
	 * @param server
	 */
	public <T extends IdEntity> void deleteAll(Class<T> cls) {
		solrClient.deleteAll(cls.getName());
		commit();
	}
	
	public <T extends IdEntity> void deleteAll(String name) {
		solrClient.deleteAll(name);
		commit();
	}
	
	/**
	 * 与SOLR SYSTEM PING, 主要是检测solr是否down掉
	 * 
	 * @param server
	 * @return String
	 */
	public String ping() {
		return solrClient.ping();
	}
	
	public boolean isEnabled(){
		return enabled;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		solrClient = new SolrClient(url); 
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
