/**
 * 
 */
package com.autozi.common.search.solr.server;

import java.io.IOException;


/**
 * @author haocheng
 * 
 */
public class SolrServerTest {
	
	//private ExecutorService executorService=null;
	private SolrServerDao server = new SolrServerDao();;
	
//	public SolrServerTest(){
//		executorService=Executors.newFixedThreadPool(5);
//	}

	public SolrServerDao getSolrServer() {
		if (server == null) {
			server =  new SolrServerDao();
		}

		return server;
	}
	
	public void testAddData() {
		SolrServerDao server = getSolrServer();
		
//		Brand brand = new Brand();
//		brand.setId(1l);
//		brand.setName("NOKIA");
//		server.update(brand);
//		
//		brand = new Brand();
//		brand.setId(1l);
//		brand.setName("NOKIA N72");
//		server.update(brand);
//		
//		brand = new Brand();
//		brand.setId(2l);
//		brand.setName("海尔");
//		server.update(brand);
//		
//		brand = new Brand();
//		brand.setId(3l);
//		brand.setName("安全360");
//		server.update(brand);
//		
//		brand = new Brand();
//		brand.setId(4l);
//		brand.setName("NOKIA中国");
//		server.update(brand);
		
		server.commit();
	}
	
	public void testQuery() throws IOException{
//		// 得到一个 SolrServer 实例（通过上面介绍的方法创建）
//		SolrServerDao dao = ApplicationContextProvider.getBean(SolrServerDao.class);
//
//		List ids = dao.getIds(Brand.class, "name", "N72");
//		System.out.println("ids:"+ids);
	}
	
	public void testDeleteAll() throws IOException{
		//SolrServerDao dao = ApplicationContextProvider.getBean(SolrServerDao.class);

		//dao.deleteAll(OeCodeCarType.class);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		SolrServerTest test=new SolrServerTest();
//		test.testDeleteAll();
//		test.testAddData();
		
//		Area area=new Area();
//		area.setId(1000L);
//		area.setName("tianjin");
//		area.setAreaCode("022");
//		SolrInputDocument input=EntityConvert.entity2SolrInputDocument(area);
//		test.add(input);
		test.testQuery();
	}

}