package com.autozi.common.repo.mybatis.oracle;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.SimpleStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;


//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
//		@Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }) })
public class StatementHandlerInterceptor extends
		AbstractStatementHandlerInterceptor implements Interceptor {

	private Object prepare(Invocation invocation) throws Throwable {
		StatementHandler statement = getStatementHandler(invocation);

		if (statement instanceof SimpleStatementHandler
				|| statement instanceof PreparedStatementHandler) {

			RowBounds rowBounds = getRowBounds(statement);

			if (hasBounds(rowBounds)) {
				BoundSql boundSql = statement.getBoundSql();
				String sql = boundSql.getSql();

				if (statement instanceof SimpleStatementHandler) {
					sql = dialect.getLimitString(sql, rowBounds.getOffset(),
							rowBounds.getLimit());
				} else if (statement instanceof PreparedStatementHandler) {
					sql = dialect.getLimitString(sql, rowBounds.getOffset()>0 );
				}
				FieldUtils.setFieldValue(boundSql, "sql", sql);
			}
		}

		return invocation.proceed();
	}

	private Object parameterize(Invocation invocation) throws Throwable {
		Statement statement = (Statement) invocation.getArgs()[0];

		Object rtn = invocation.proceed();

		if (statement instanceof PreparedStatement) {
			PreparedStatement ps = (PreparedStatement) statement;

			StatementHandler statementHandler = getStatementHandler(invocation);
			RowBounds rowBounds = getRowBounds(statementHandler);

			if (hasBounds(rowBounds)) {
				BoundSql boundSql = statementHandler.getBoundSql();
				int parameterSize = boundSql.getParameterMappings().size();
				dialect.setLimitParamters(ps, parameterSize,rowBounds.getOffset(), rowBounds.getLimit());
			}
		}
		return rtn;
	}

	public Object intercept(Invocation invocation) throws Throwable {
		Method m = invocation.getMethod();
		if ("prepare".equals(m.getName())) {
			return prepare(invocation);
		} else if ("parameterize".equals(m.getName())) {
			return parameterize(invocation);
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

}
