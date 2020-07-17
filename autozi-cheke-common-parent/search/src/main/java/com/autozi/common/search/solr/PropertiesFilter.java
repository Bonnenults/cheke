package com.autozi.common.search.solr;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-3-19 上午11:24
 */
public class PropertiesFilter {
	
	public PropertiesFilter(String name,Type type){
		this.type = type;
		this.name = name;
	}
	public PropertiesFilter(){
	}
    /**
     * INNER_OR_EQUAL  需要将其用括号括起来作为一个条件 （categoryId:123 OR categoryId:456 OR categoryId:789）
     * STORE 暂时存值，目前用于存储分类id（用来关联productId以便查询自定义属性的）
     * **/
    public enum Type {
        LIKE, EQUAL, SORT_DESC, SORT_ASC, FACET, STORE;
    }

    private Type type;          //TYPE里的值，假设精准匹配modelId，则此处应村粗EQUAL
    private String name;        //如：modelId
    private Object value;      //value 对应key的值如：123456，如果是INNER_OR的情况，则此处存放一个list

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}



















