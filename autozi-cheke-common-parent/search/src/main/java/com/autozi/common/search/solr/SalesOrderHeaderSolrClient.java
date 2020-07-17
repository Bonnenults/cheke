package com.autozi.common.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.autozi.common.core.page.Page;
import com.autozi.common.search.solr.server.SolrServerFactory;
import com.autozi.common.utils.util2.orderSolrProperties;

/**
 * 
 * @author zhiyun.chen
 *
 */
public class SalesOrderHeaderSolrClient {
    private static final String SOLR_ZKHOST_SERVER_URL = "orderHeader.solr.zkhost.server.url";
	private static Logger logger = LoggerFactory.getLogger(SalesOrderHeaderSolrClient.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");
    private static CloudSolrServer server;
    private static final String DEFAULT_SOLR_CORE = "autozi_order_header";
    private static SalesOrderHeaderSolrClient instance;

    private static final String zkHost = orderSolrProperties.getValue(SOLR_ZKHOST_SERVER_URL);
    
    CommitTimer commitTimer = new CommitTimer();

    public static SalesOrderHeaderSolrClient getInstance(){
    	if(instance==null){
    		instance=new SalesOrderHeaderSolrClient();
    	}
    	return instance;
    }    
    
    private SalesOrderHeaderSolrClient() {
    	server = SolrServerFactory.getCommonsCloudSolrServer(zkHost, DEFAULT_SOLR_CORE);
    }

    public void add(Collection<SolrInputDocument> docs){
        try {
            server.add(docs);
            server.commit(false, false);
        } catch (SolrServerException e) {
            logger.error("添加订单头索引出现异常:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("添加订单头索引出现异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Description: 批量添加订单头索引
     * @author: zhiyun.chen
     * 2016-11-17上午11:02:03
     */
    public void addBeans(Collection<SalesOrderHeaderEntity> entityList){
        try {
        	logger.info("=====提交订单头索引数据=====开始");
            server.addBeans(entityList);
            server.commit(false, false);
            logger.info("=====提交订单头索引数据=====结束");
        } catch (SolrServerException e) {
            logger.error("添加订单头索引出现异常:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("添加订单头索引出现异常:"+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * @Description: 单个添加订单头索引
     * @author: zhiyun.chen
     * 2016-11-17上午11:02:18
     */
    public void addBean(SalesOrderHeaderEntity entity){
        try {
        	logger.info("=====提交订单头索引数据=====开始");
            server.addBean(entity);
            server.commit(false, false);
            logger.info("=====提交订单头索引数据=====结束");
        } catch (SolrServerException e) {
            logger.error("添加订单头索引出现异常:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("添加订单头索引出现异常:"+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * @Description: 查询订单头（带分页）
     * @author: zhiyun.chen
     * 2016-11-17上午11:07:21
     */
    public QeeGooSolrSearchResult findPageForProFilter(Page<SalesOrderHeaderEntity> page, List<PropertiesFilter> propertiesFiltersList) throws Exception{
        QueryResponse response=null;
        QeeGooSolrSearchResult result = new QeeGooSolrSearchResult();
        logger.info("查询开始,开始计时");
        _logger_solr.info("查询开始,开始计时");
        StopWatch stopWatch = new StopWatch();
        try {
        	stopWatch.start();
        	if (page.getPageNo() > 100) {//最多查第100页的数据
        		page.setPageNo(100);
        	}
            SolrQuery solrQuery = SalesOrderHeaderSolrClientHelper.initSolrQuery(page, propertiesFiltersList);
            if(solrQuery!=null){
            	response = server.query(solrQuery);
            }
            stopWatch.stop();
            logger.info("查询结束,共计用时："+stopWatch.getTotalTimeSeconds()+"秒");
            _logger_solr.info("查询结束,共计用时："+stopWatch.getTotalTimeSeconds()+"秒");
        } catch (Exception e) {
        	e.printStackTrace();
        	stopWatch.stop();
            logger.error("solr 查询异常:"+e.getMessage());
            logger.info("查询结束,共计用时："+stopWatch.getTotalTimeSeconds()+"秒");
            _logger_solr.info("solr 查询异常:",e);
            _logger_solr.info("查询结束,共计用时："+stopWatch.getTotalTimeSeconds()+"秒");
            throw new Exception("100100100");
        }
        if(null != response){
            page = assambleIndexEntityResult(page, response);
            result.setSalesOrderHeaderEntityPage(page);
            List<QeeGooFacetField> qeeGooFacetFieldList = assambleQeeGooFacetFieldList(response);
            result.setFacetFieldList(qeeGooFacetFieldList);
        }
        return result;
    }

    //把solr的facetField转换成自定义的
    private List<QeeGooFacetField> assambleQeeGooFacetFieldList(QueryResponse response) {
        List<FacetField> facetFields = response.getFacetFields();
        List<QeeGooFacetField> qeeGooFacetFieldList = new ArrayList<QeeGooFacetField>();
        if(null != facetFields && facetFields.size() > 0){
            for(FacetField facetField : facetFields){
                QeeGooFacetField qeeGooFacetField = new QeeGooFacetField();
                qeeGooFacetField.setName(facetField.getName());
                List<FacetField.Count> facetFieldValues = facetField.getValues();
                List<QeeGooFacetField.Count> qeeGooFacetFieldCountList = new ArrayList<QeeGooFacetField.Count>();
                for(FacetField.Count count : facetFieldValues){
                    QeeGooFacetField.Count _count = new QeeGooFacetField.Count();
                    _count.setName(count.getName());
                    _count.setCount(count.getCount());
                    qeeGooFacetFieldCountList.add(_count);
                }
                qeeGooFacetField.setList(qeeGooFacetFieldCountList);
                qeeGooFacetFieldList.add(qeeGooFacetField);
            }
        }
        return qeeGooFacetFieldList;
    }

    private Page<SalesOrderHeaderEntity> assambleIndexEntityResult(Page<SalesOrderHeaderEntity> page, QueryResponse response) {
        SolrDocumentList solrDocList = response.getResults();
        DocumentObjectBinder binder = new DocumentObjectBinder();
        List<SalesOrderHeaderEntity> indexEntityList = binder.getBeans(SalesOrderHeaderEntity.class, solrDocList);
        if(null != indexEntityList){
            page.setResult(indexEntityList);
        }
        if (solrDocList.getNumFound() > page.getPageSize()*100) {
        	page.setTotalCount(page.getPageSize()*100);
        } else {
        	page.setTotalCount(solrDocList.getNumFound());
        }
        return page;
    }


    /**
     *  更新订单头索引，先删除goodsIdList对应的订单头索引，然后新增docs
     *  @param goodsIdList 商品IdList
     *  @param docs 要新增的订单头索引
     * **/
    public void updateIndex(List<String> goodsIdList, Collection<SolrInputDocument> docs) throws Exception {
        try {
            if(goodsIdList.size() > 0){
                server.deleteById(goodsIdList);
            }
            if(docs.size() > 0){
                server.add(docs);
            }
            if(goodsIdList.size() > 0 || docs.size() > 0){
                server.commit(false, false);
                server.optimize();
            }
        } catch (SolrServerException e) {
            logger.error("更新订单头索引失败"+e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("更新订单头索引失败"+e.getMessage());
            throw e;
        }
    }
    
    /**
     * 优化solr数据存储结构
     *
     */
    public void optimize() {
        try {
            server.optimize(true, false);
            server.optimize(false, false);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        commitTimer.commit();
    }

    /**
     * 删除数据
     * @param id     Id值
     */
    public void deleteById(String id) {
        try {
            server.deleteById(id);
            server.commit(false, false);
        } catch (SolrServerException e) {
            e.printStackTrace();
            logger.error("删除订单头索引失败" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("删除订单头索引失败" + e.getMessage());
        }
    }

    /**
     * 删除数据
     * @param id     Id值
     */
    public IndexEntity getById(String id){
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery("id:"+id);
        IndexEntity indexEntity = null;
        try {
            QueryResponse response = server.query(solrQuery);
            DocumentObjectBinder binder = new DocumentObjectBinder();
            SolrDocumentList docResult = response.getResults();
            if(null != docResult && docResult.size() > 0){
                List<IndexEntity> entityResult = binder.getBeans(IndexEntity.class, docResult);
                indexEntity = entityResult.get(0);
            }
        } catch (SolrServerException e) {
            logger.error("根据Id："+id+"，查询失败:"+e.getMessage());
        }
        return indexEntity;
    }

    /**
     * 批量删除数据
     * 注意：该数组元素必须不大于1024或者去更改solrconfig.xml中的参数，参数如下：
     * &lgmaxBooleanClauses&gt1024&lg/maxBooleanClauses&gt
     *
     * @param ids   IdList
     */
    public void deleteByIds(List<String> ids) {
        if (ids.size() > 0) {
            try {
                server.deleteById(ids);
                server.commit(false, false);
            } catch (SolrServerException e) {
                e.printStackTrace();
                logger.error("删除订单头索引失败" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("删除订单头索引失败" + e.getMessage());
            }
        }
    }

    /**
     * 删除所有元素
     */
    public void deleteAll() {
        try {
            server.deleteByQuery("*:*");
            server.commit(false, false);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class CommitTimer extends Thread {
        private static final int TIME_TO_WAIT = 60 * 1000;    // 60s

        private boolean needCommit = false;
        private long commitTime;	// time to commit

        void commit() {
            commitTime = new Date().getTime() + TIME_TO_WAIT;
            if (needCommit) {
                return;
            }

            needCommit = true;
        }

        public void run() {
            while (true) {
                if (needCommit) {
                    long now = new Date().getTime();
                    if (now >= commitTime) {
                        needCommit = false;
                        try {
                            server.commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    sleep(5 * 1000);	// sleep 5s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 与SOLR SYSTEM PING, 主要是检测solr是否down掉
     * @return String
     */
    public String ping() {
        try {
            return server.ping().getResponse().toString();
        } catch (SolrServerException e) {
            logger.error("solr 服务器连接失败：" + e.getMessage());
        } catch (IOException e) {
            logger.error("solr 服务器连接失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("solr 服务器连接失败：" + e.getMessage());
        }
        return null;
    }
    
    
    /**
     * 通过solr获取suggest
     * @param token
     * @return
     * @throws Exception
     */
    public List<QeeGooSolrSuggestResult> suggest(String token) throws Exception{
    	QueryResponse response = null;
        try {
            SolrQuery solrQuery = QeeGooSolrClientHelper.initSolrSuggest(token);
            response = server.query(solrQuery);
        } catch (Exception e) {
            logger.error("solr suggest异常:"+e.getMessage());
            e.printStackTrace();
            throw new Exception("100100200");
        }
        if(null != response){
        	SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
    		if (spellCheckResponse != null) {
    			List<Suggestion> suggestionList = spellCheckResponse.getSuggestions();
    			List<QeeGooSolrSuggestResult> s_rs = new ArrayList<QeeGooSolrSuggestResult>();
    			for (Suggestion suggestion : suggestionList) {
    				QeeGooSolrSuggestResult rs = converSuggestResult(suggestion);
    				s_rs.add(rs);
    			}
    			return s_rs;
    		}
        }
        
        return null;
    }
    
    private QeeGooSolrSuggestResult converSuggestResult(Suggestion suggestion){
    	QeeGooSolrSuggestResult rs = new QeeGooSolrSuggestResult();
    	rs.setNumFound(suggestion.getNumFound());
    	rs.setToken(suggestion.getToken());
		List<String> suggestedWordList = suggestion.getAlternatives();
		rs.setSuggestResult(suggestedWordList);
		return rs;
    }
    
}
