package com.autozi.common.dal.mybatis.oracle;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.InitializingBean;


public abstract class AbstractStatementHandlerInterceptor implements Interceptor, InitializingBean {
	
	private Class<Dialect> dialectClass;

	public void setDialectClass(Class<Dialect> dialectClass) {
		this.dialectClass = dialectClass;
	}
	
	protected Dialect dialect;

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	public void afterPropertiesSet() throws Exception {
		setDialect(dialectClass.newInstance());
	}

	protected StatementHandler getStatementHandler(Invocation invocation) {
		StatementHandler statement = (StatementHandler) invocation.getTarget();
		if (statement instanceof RoutingStatementHandler) {
			statement = (StatementHandler) FieldUtils.getFieldValue(statement,"delegate");
		}
		return statement;
	}
	
	protected RowBounds getRowBounds(StatementHandler statement) {
		return (RowBounds) FieldUtils.getFieldValue(statement, "rowBounds");
	}
	
	protected boolean hasBounds(RowBounds rowBounds) {
		return (rowBounds != null 
				&& rowBounds.getLimit() > 0 
				&& rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT);
	}

}
