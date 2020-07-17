package com.autozi.common.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.FacetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.core.page.Page;


public class SolrClient {
	
	private static Logger logger = LoggerFactory.getLogger(SolrClient.class);
    public static final SolrInputDocument DOC = new SolrInputDocument();
    SolrServer server;
	CommitTimer commitTimer = new CommitTimer();
    static String url = "http://127.0.0.1:8080/solr";
    
	
    
    public SolrClient(){
    	this(url);
    }
	
	public SolrClient(String url){
		server = new HttpSolrServer(url);
		
		commitTimer.start();
	}
	
	
	public Page<Long> findPage(Page<Long> page, Map<String,Object> filter,IndexEntity entity){
		
		return null;
	}
	
	

	public void commit() {
		commitTimer.commit();
	}

	/**
	 * 删除数据
	 * @param id     Id值
	 * @param idName Id名称
	 * @param server
	 */
	public void deleteById(String name, Long id) {
		try {
			server.deleteByQuery("id:" + name + "@" +id);
			server.commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void deleteByIds(String name, Long[] ids) {
		if (ids.length > 0) {
			try {
				StringBuffer query = new StringBuffer("id:" + name + "@" + ids[0]);
				for (int i = 1; i < ids.length; i++) {
					if (ids[i] != null) {
						query.append(" OR id:" + name + "@" + ids[i]);
					}
				}
				server.deleteByQuery(query.toString());
				server.commit(false, false);
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除所有元素
	 * 
	 * @param server
	 */
	public void deleteAll(String name) {
		try {
			server.deleteByQuery("rname:"+ name);
			server.commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 与SOLR SYSTEM PING, 主要是检测solr是否down掉
	 * 
	 * @param server
	 * @return String
	 */
	public String ping() {
		try {
			return server.ping().getResponse().toString();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 优化solr数据存储结构
	 * 
	 * @param server
	 */
	@SuppressWarnings("unused")
	public void optimize() {
		try {
			long now = System.currentTimeMillis();
			server.optimize(true, false);
			server.optimize(false, false);
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
	
	public  Map<String, Integer> queryByGroup(String qStr,String groupField,String sortField,boolean asc,Integer pageSize,Integer pageNum){
		  Map<String, Integer> rmap = new LinkedHashMap<String, Integer>();
		  try {
		  // SolrServer server =server;//getSolrServer() 方法就是返回一个CommonsHttpSolrServer
		   SolrQuery query = new SolrQuery();
		   if(qStr!=null&&qStr.length()>0)
		    query.setQuery(qStr);
		   else
		    query.setQuery("*:*");            //如果没有查询语句，必须这么写，否则会报异常
		   query.setIncludeScore(false);      //是否按每组数量高低排序
		   query.setFacet(true);              //是否分组查询
		   query.setRows(0);                  //设置返回结果条数，如果你时分组查询，你就设置为0
		   query.addFacetField(groupField);   //增加分组字段
		   query.setFacetSort(true);          //分组是否排序
		   query.setFacetLimit(pageSize);     //限制每次返回结果数
		   query.setSortField(sortField,asc ? SolrQuery.ORDER.asc :SolrQuery.ORDER.desc );//分组排序字段
		   query.set(FacetParams.FACET_OFFSET,(pageNum-1)*pageSize);//当前结果起始位置
		   QueryResponse rsp = server.query( query );

              FacetField facetField = rsp.getFacetField(groupField);
              List<Count> countList = facetField.getValues();
		   List<Count> returnList = new ArrayList<Count>();
		   if(pageNum*pageSize<countList.size())
		    returnList = countList.subList((pageNum-1)*pageSize,pageNum*pageSize);
		   else
		    returnList = countList.subList((pageNum-1)*pageSize,countList.size()-1);
		   
		   for (Count count : returnList) {
		    if(count.getCount()>0)
		     rmap.put(count.getName(), (int) count.getCount());
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return rmap;
		 }
	
	public static void main(String[] args) throws SolrServerException, IOException {
		SolrClient solr=new SolrClient();
		String reps=solr.ping();
		System.out.println(reps);
//		solr.initData();
		solr.query();
	}
	
	public void initData() throws SolrServerException, IOException{
		Collection<SolrInputDocument> docs = new  ArrayList<SolrInputDocument>();  

		SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",  "1302191535070000");
        doc.addField("brandId", 747);
        doc.addField("keyWord", "一汽大众迈腾,一汽大众迈腾,2007款 2.0L MT,铂金火花塞,czy_28_test0811,盖茨");
        doc.addField("category1Id", 20121109120345000L);
        doc.addField("category2Id", 20121109120345000L);
//        doc.addField("keyWord", "一汽大众迈腾 2007款 2.0L MT");
//        doc.addField("keyWord", "铂金火花塞");
//        doc.addField("keyWord", "czy_28_test0811");
//        doc.addField("keyWord", "盖茨");
        doc.addField("price", 3.50F);
        doc.addField("carModelId", "1 2 3");
//        doc.addField("carModelId", "1");
//        doc.addField("carModelId", "2");
//        doc.addField("carModelId", "3");

	    SolrInputDocument doc1 = new SolrInputDocument();
        Long doc1Id =  1301110919190001L;
        doc1.addField( "id", doc1Id);
        doc1.addField( "brandId", 15088);
        doc1.addField( "keyWord", "进口大众Polo 1.4L MT,进口大众Polo,博世,钇金火花塞,L2400MF");
        doc1.addField("category1Id", 20121109120345000L);
        doc1.addField("category2Id", 20121109120345000L);
//        doc1.addField( "keyWord", "进口大众Polo");
//        doc1.addField( "keyWord", "博世");
//        doc1.addField( "keyWord", "钇金火花塞");
//        doc1.addField( "keyWord", "L2400MF");
        doc1.addField( "price", 4.50F);
        doc1.addField("carModelId", "4 5 6");
//        doc1.addField("carModelId", "4");
//        doc1.addField("carModelId", "5");
//        doc1.addField("carModelId", "6");

        SolrInputDocument doc2 = new SolrInputDocument();
        Long doc2Id =  1301081557120001L;
        doc2.addField( "id",doc2Id);
		doc2.addField( "brandId", 14232L);
        doc2.addField( "keyWord", "进口奥迪80 2.0L MT,进口奥迪80,盖茨,F7TJC,普通火花塞");
        doc2.addField("category1Id", 20121109104051002L);
        doc2.addField("category2Id", 20121109104051002L);
//        doc2.addField( "keyWord", "进口奥迪80");
//        doc2.addField( "keyWord", "盖茨");
//        doc2.addField( "keyWord", "F7TJC");
//        doc2.addField( "keyWord", "普通火花塞");
        doc2.addField( "price", 5.25);
        doc2.addField("carModelId", "7 8 9");
//        doc2.addField("carModelId", "7");
//        doc2.addField("carModelId", "8");
//        doc2.addField("carModelId", "9");

        SolrInputDocument doc3 = new SolrInputDocument();
		Long doc3Id = 1212171436350015L;
		doc3.addField("id", doc3Id);
		doc3.addField( "brandId", 1212071653350222L);
		doc3.addField( "keyWord", "进口奥迪100 C4 1991款 2.8L AT,进口奥迪100,博世,WO-7506,普通火花塞");
        doc3.addField("category1Id", 20121109104051002L);
        doc3.addField("category2Id", 20121109104051002L);
//        doc3.addField( "keyWord", "进口奥迪100");
//		doc3.addField( "keyWord", "博世");
//		doc3.addField( "keyWord", "WO-7506");
//		doc3.addField( "keyWord", "普通火花塞");
        doc3.addField( "price",25.25);
        doc3.addField("carModelId", "10 11 12");
//        doc3.addField("carModelId", "10");
//        doc3.addField("carModelId", "11");
//        doc3.addField("carModelId", "12");

        SolrInputDocument doc4 = new SolrInputDocument();
		Long doc4Id = 710L;
		doc4.addField( "id",doc4Id);
		doc4.addField( "brandId", 710L);
        doc4.addField( "keyWord", "进口奥迪100 C4 1991款 2.8L AT,进口奥迪100,博世,LH907新凯旋,免维护蓄电池");
        doc4.addField("category1Id", 20121109104051002L);
        doc4.addField("category2Id", 20121109104051002L);
//        doc4.addField( "keyWord", "进口奥迪100");
//        doc4.addField( "keyWord", "博世");
//        doc4.addField( "keyWord", "LH907新凯旋");//GOODSSTYLE
//        doc4.addField( "keyWord", "免维护蓄电池");
        doc4.addField( "price", "15.25");
        doc4.addField("carModelId", "13 14");
//        doc4.addField("carModelId", "12");
//        doc4.addField("carModelId", "10");

        SolrInputDocument doc5 = new SolrInputDocument();
		Long doc5Id = 1361679L;
		doc5.addField( "id", doc5Id);
		doc5.addField( "brandId", 1361679L);
        doc5.addField( "keyWord", "进口奥迪100 C4 1991款 2.8L AT,进口奥迪100,博世,LH907新凯旋,免维护蓄电池");
        doc5.addField("category1Id", 20121109104051002L);
        doc5.addField("category2Id", 20121109104051002L);
//        doc5.addField( "keyWord", "进口奥迪100");
//        doc5.addField( "keyWord", "博世");
//        doc5.addField( "keyWord", "0986487901");//GOODSSTYLE
//        doc5.addField( "keyWord", "后刹车蹄");
        doc5.addField( "price", "123.25");
        doc5.addField("carModelId", "101 10");
//        doc5.addField("carModelId", "101");
//        doc5.addField("carModelId", "1010");

        docs.add(doc);
		docs.add(doc1);
		docs.add(doc2);
		docs.add(doc3);
		docs.add(doc4);
		docs.add(doc5);
		server.add(docs);
		server.commit();
	}
    public void query(){
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //query.setQuery("*:*");
        query.setFacet(true);
        query.addFacetField("category1Id").setFacetMinCount(1);
        query.setStart(0);
        query.setRows(6);
        query.setFilterQueries("{!tag=aa}brandId:590");
        query.setSortField("updateTime", SolrQuery.ORDER.asc);
        QueryResponse response=null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        SolrDocumentList doclist=response.getResults();

        List<FacetField> facetFieldsList=response.getFacetFields();
        for(FacetField facetField : facetFieldsList){
            System.out.println(facetField.getName());
            List<Count> values = facetField.getValues();
            for (Count count : values) {
                System.out.println(count.getName());
                System.out.println(count.getCount());
            }
        }
        if(null != doclist && doclist.size() > 0){
            for (int i = 0; i < doclist.size(); i++) {
                SolrDocument entries = doclist.get(i);
                Map<String, Object> fieldValueMap = entries.getFieldValueMap();
                Object id = fieldValueMap.get("id");
                System.out.println(id);
            }
        }
    }
}
