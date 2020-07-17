package com.autozi.common.utils.util3;



import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;

public class ContextUtil extends Object {
    public static ApplicationContext ctx = null;
    public static String webPath = "";
 
    public static DataSource getDataSource() {
    	DataSource dataSource = (DataSource) ctx.getBean("dataSource");
    	
    	return dataSource;
    }
      
    public static Object getBean(String beanName){
    	return ctx.getBean(beanName);
    }
   
}
