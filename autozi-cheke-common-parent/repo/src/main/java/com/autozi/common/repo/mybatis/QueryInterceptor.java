package com.autozi.common.repo.mybatis;

import com.autozi.common.utils.util1.PropertyUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

;

/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 配置文件内容:
 * <pre>
 * 	&lt;plugins>
 *		&lt;plugin interceptor="net.b2bcenter.framework.modules.mybatis.OffsetLimitInterceptor">
 *			&lt;property name="dialectClass" value="net.b2bcenter.framework.modules.mybatis.jdbc.dialect.MySQLDialect"/>
 *		&lt;/plugin>
 *	&lt;/plugins>
 * </pre>
 * 
 * @author badqiu
 *
 */

@Intercepts({@Signature(type= Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class QueryInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(QueryInterceptor.class);

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	
	private Dialect dialect;

//    private SqlConverter validConverter;
	
	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation invocation) throws Throwable {
	    Object result = null;

	    MappedStatement ms = (MappedStatement)invocation.getArgs()[MAPPED_STATEMENT_INDEX];
	    synchronized(ms) {
		    List sqlList = ((DynamicSqlSource)ms.getSqlSource()).getSqlString();
		    sqlList = (List)PropertyUtils.deepClone(sqlList);
		    
		    try {
	    		processIntercept(invocation.getArgs());
	    		result = invocation.proceed();
		    } catch (Exception e) {
		    	logger.debug("parameter:"+invocation.getArgs()[PARAMETER_INDEX]+" sql:"+((DynamicSqlSource)ms.getSqlSource()).getSqlString());
		    	e.printStackTrace();
		    	throw e;
		    } finally {
			    ((DynamicSqlSource)ms.getSqlSource()).setSql(sqlList);
		    }
	    }
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private void processIntercept(final Object[] queryArgs) {
		MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
        DynamicSqlSource dss = (DynamicSqlSource) ms.getSqlSource();

        List sqlList = dss.getSqlString();

        // convert VALID
//        sqlList = convertValid(sqlList);
			
        dss.setSql(sqlList);

		// process page select
		convertPage(queryArgs, ms, parameter);

	}

//	@SuppressWarnings("rawtypes")
//	private List convertValid(List sqlList) {
//        if (validConverter == null) {
//            WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//            if (currentWebApplicationContext != null) {
//                validConverter = currentWebApplicationContext.getBean(ValidConverter.class);
//            } else {
//                return sqlList;
//            }
//        }
//		return validConverter.exec(sqlList);
//	}

	private void convertPage(final Object[] queryArgs, MappedStatement ms,Object parameter) {
		final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		
		if(dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			logger.debug("sql:"+sql);
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;
			
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset,limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
	}

	
	//see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		
		String[] keys=ms.getKeyProperties();
		if(keys!=null){
			StringBuffer aa=new StringBuffer();
			for(String a:keys){
				aa.append(a).append(",");
			}
			String key=aa.toString().substring(0, aa.length()-1);
			builder.keyProperty(key);
		}
		

		
		//setStatementTimeout()
		builder.timeout(ms.getTimeout());
		
		//setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		
		//setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
	    
		//setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialectClass");
		try {
			dialect = (Dialect)Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:"+dialectClass,e);
		} 
		logger.debug(QueryInterceptor.class.getSimpleName()+".dialect="+dialectClass);
	}
	
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}
