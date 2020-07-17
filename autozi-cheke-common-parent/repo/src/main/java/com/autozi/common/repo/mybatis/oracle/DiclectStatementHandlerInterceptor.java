package com.autozi.common.repo.mybatis.oracle;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

//@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DiclectStatementHandlerInterceptor implements Interceptor {

	//private Properties properties;

	//private static final String DIALECT = "dialect";
//
	public Object intercept(Invocation invocation) throws Throwable {
	//	RoutingStatementHandler statement = (RoutingStatementHandler) invocation
			//	.getTarget();

//		PreparedStatementHandler handler = (PreparedStatementHandler) ReflectUtil.getField(statement, "delegate", StatementHandler.class);
//
//		Integer rowOffset = (Integer) ReflectUtil.getField(handler,
//				"rowOffset", int.class);
//		Integer rowLimit = (Integer) ReflectUtil.getField(handler, "rowLimit",
//				int.class);
//
//		if (rowLimit > 0 && rowLimit < Executor.NO_ROW_LIMIT) {
//			BoundSql boundSql = statement.getBoundSql();
//			String sql = boundSql.getSql();
//			String dialectStr = properties.getProperty(DIALECT);
//
//			Dialect dialect = (Dialect) Class.forName(dialectStr).newInstance();
//			sql = dialect.getLimitString(sql, rowOffset, rowLimit);
//
//			ReflectUtil.setFieldValue(boundSql, "sql", String.class, sql);
//		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		//this.properties = properties;
	}
}
