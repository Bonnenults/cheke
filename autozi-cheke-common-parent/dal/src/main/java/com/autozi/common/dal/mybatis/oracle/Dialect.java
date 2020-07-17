package com.autozi.common.dal.mybatis.oracle;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Dialect {

	public void setLimitParamters(PreparedStatement ps, int parameterSize,
			int offset, int limit) throws SQLException;

	public String getLimitString(String sql, boolean hasOffset);

	public String getLimitString(String sql, int offset, int limit);

}
