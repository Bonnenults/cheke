package org.apache.ibatis.scripting.xmltags;

import java.util.List;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class IfSqlNode implements SqlNode {
	private ExpressionEvaluator evaluator;
	private String test;
	private SqlNode contents;

	public IfSqlNode(SqlNode contents, String test) {
		this.test = test;
		this.contents = contents;
		this.evaluator = new ExpressionEvaluator();
	}

	@SuppressWarnings("rawtypes")
	public List getSqlString() {
		if (contents instanceof MixedSqlNode) {
			return ((MixedSqlNode) contents).getSqlString();
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	public void setSql(List sql) {
		if (contents instanceof MixedSqlNode) {
			((MixedSqlNode) contents).setSql(sql);
		}
	}

	public boolean apply(DynamicContext context) {
		if (evaluator.evaluateBoolean(test, context.getBindings())) {
			contents.apply(context);
			return true;
		}
		return false;
	}

}
