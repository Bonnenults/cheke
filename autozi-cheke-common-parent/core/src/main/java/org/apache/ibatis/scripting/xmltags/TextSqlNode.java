package org.apache.ibatis.scripting.xmltags;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.type.SimpleTypeRegistry;

public class TextSqlNode implements SqlNode {
  private String text;

  public TextSqlNode(String text) {
    this.text = text;
  }

  public String getText() {
	  return text;
  }
  
  public void setText(String text) {
	    this.text = text;
  }
  
  public boolean apply(DynamicContext context) {
    GenericTokenParser parser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
    context.appendSql(parser.parse(text));
    return true;
  }

	private static class BindingTokenParser implements TokenHandler {

		private DynamicContext context;

		public BindingTokenParser(DynamicContext context) {
			this.context = context;
		}

		public String handleToken(String content) {
			Object parameter = context.getBindings().get("_parameter");
			if (parameter == null) {
				context.getBindings().put("value", null);
			} else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
				context.getBindings().put("value", parameter);
			}
			Object value = OgnlCache.getValue(content, context.getBindings());
			return String.valueOf(value);

		}
	}

}

