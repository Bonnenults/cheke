package com.autozi.common.search.solr.jyj;

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
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.autozi.common.core.page.Page;
import com.autozi.common.search.solr.IndexEntity;
import com.autozi.common.search.solr.PropertiesFilter;
import com.autozi.common.search.solr.QeeGooFacetField;
import com.autozi.common.search.solr.QeeGooSolrSearchResult;
import com.autozi.common.search.solr.server.SolrServerFactory;
import com.autozi.common.utils.util2.ApplicationProperties;

/**
 * 类描述:
 * 创建人: BINBIN.LEE
 * 创建时间: 20170213 下午5:20
 */
public class JYJQeeGooSolrClient {
    private static final String SOLR_SERVER_URL = "solr.server.url";
    private static final String SOLR_ZKHOST_SERVER_URL = "solr.zkhost.server.url";
	private static Logger logger = LoggerFactory.getLogger(JYJQeeGooSolrClient.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");
    private static CloudSolrServer server;
    private static final String SOLR_CORE [] = {"/goodshome","/collhome"};
    private static final String DEFAULT_SOLR_CORE = "autozi_goods";
    private static JYJQeeGooSolrClient instance;

    private static JYJQeeGooSolrClient collinstance;
    
    private static final String url = ApplicationProperties.getValue(SOLR_SERVER_URL);
    private static final String zkHost = ApplicationProperties.getValue(SOLR_ZKHOST_SERVER_URL);
    
    CommitTimer commitTimer = new CommitTimer();

    public static JYJQeeGooSolrClient getInstance(){
    	if(instance==null){
    		instance=new JYJQeeGooSolrClient(url);
    	}

    	return instance;
    }
    
    public static JYJQeeGooSolrClient getCollInstance(){
    	if(collinstance==null){
    		collinstance=new JYJQeeGooSolrClient(url+SOLR_CORE[1]);
    	}

    	return collinstance;
    }
    
    private JYJQeeGooSolrClient(String url) {
    	server = SolrServerFactory.getCommonsCloudSolrServer(zkHost, DEFAULT_SOLR_CORE);
    }

    public void add(Collection<SolrInputDocument> docs){
        try {
            server.add(docs);
            server.commit(false, false);
        } catch (SolrServerException e) {
            logger.error("添加索引出现异常:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("添加索引出现异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addBeans(Collection<IndexEntity> entityList){
        try {
        	logger.info("=====提交索引数据=====开始");
            server.addBeans(entityList);
            server.commit(false, false);
            logger.info("=====提交索引数据=====结束");
        } catch (SolrServerException e) {
            logger.error("添加索引出现异常:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("添加索引出现异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Return QeeGooSolrSearchResult
     * @prop indexEntityPage 商品列表页所需要的page
     * @prop facetFieldList facet的结果
     * */
    public QeeGooSolrSearchResult findPageForProFilter(Page<IndexEntity> page, List<PropertiesFilter> propertiesFiltersList, String ownerPartyId) throws Exception{
        QueryResponse response=null;
        QeeGooSolrSearchResult result = new QeeGooSolrSearchResult();
        logger.info("查询开始,开始计时");
        _logger_solr.info("查询开始,开始计时");
        StopWatch stopWatch = new StopWatch();
        try {
        	stopWatch.start();
            SolrQuery solrQuery = JYJQeeGooSolrClientHelper.initSolrQuery(page, propertiesFiltersList, ownerPartyId);
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
            result.setIndexEntityPage(page);
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

    private Page<IndexEntity> assambleIndexEntityResult(Page<IndexEntity> page, QueryResponse response) {
        SolrDocumentList solrDocList = response.getResults();
        DocumentObjectBinder binder = new DocumentObjectBinder();
        List<IndexEntity> indexEntityList = binder.getBeans(IndexEntity.class, solrDocList);
        if(null != indexEntityList){
            page.setResult(indexEntityList);
        }
        page.setTotalCount(solrDocList.getNumFound());
        return page;
    }


    /**
     *  更新索引，先删除goodsIdList对应的索引，然后新增docs
     *  @param goodsIdList 商品IdList
     *  @param docs 要新增的索引
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
            logger.error("更新索引失败"+e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("更新索引失败"+e.getMessage());
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
            logger.error("删除索引失败" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("删除索引失败" + e.getMessage());
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
                logger.error("删除索引失败" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("删除索引失败" + e.getMessage());
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
     * @Return QeeGooSolrSearchResult
     * @prop indexEntityPage 商品列表页所需要的page
     * @prop facetFieldList facet的结果
     * */
    public List<QeeGooFacetField> findFacetForProFilter(List<PropertiesFilter> propertiesFiltersList, String ownerPartyId){
        QueryResponse response=null;
        try {
            SolrQuery solrQuery = JYJQeeGooSolrClientHelper.initSolrFacetQuery(propertiesFiltersList);
            response = server.query(solrQuery);
        } catch (Exception e) {
            logger.error("solr 查询异常:"+e.getMessage());
            e.printStackTrace();
        }
        if(null != response){
        	return assambleQeeGooFacetFieldList(response);
        }else{
        	return null;
        }
    }
    

}
