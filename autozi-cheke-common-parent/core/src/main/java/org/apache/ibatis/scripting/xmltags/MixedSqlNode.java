package org.apache.ibatis.scripting.xmltags;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class MixedSqlNode implements SqlNode {
	private List<SqlNode> contents;

	public MixedSqlNode(List<SqlNode> contents) {
		this.contents = contents;
	}

	public boolean apply(DynamicContext context) {
		for (SqlNode sqlNode : contents) {
			sqlNode.apply(context);
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSqlString() {
		if (contents == null || contents.size() == 0) {
			return null;
		}

		List sql = new ArrayList();
		for (SqlNode node : contents) {
			if (node instanceof TextSqlNode) {
				sql.add(((TextSqlNode) node).getText());
			} else if (node instanceof TrimSqlNode) {
				sql.add(((TrimSqlNode) node).getSqlString());
			} else if (node instanceof IfSqlNode) {
				sql.add(((IfSqlNode) node).getSqlString());
			} else {
				sql.add(null);
			}
		}
		return sql;
	}

	@SuppressWarnings("rawtypes")
	public void setSql(List sql) {
		if (contents == null || contents.size() == 0) {
			return;
		}

		for (int i = 0; i < contents.size(); i++) {
			SqlNode node = contents.get(i);
			if (node instanceof TextSqlNode) {
				((TextSqlNode) node).setText((String) sql.get(i));
			} else if (node instanceof TrimSqlNode) {
				((TrimSqlNode) node).setSql((List) sql.get(i));
			} else if (node instanceof IfSqlNode) {
				((IfSqlNode) node).setSql((List) sql.get(i));
			}
		}
	}

}
