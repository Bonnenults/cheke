package com.autozi.common.search.solr;

import java.util.List;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-4-25 上午11:46
 */
public class QeeGooSolrSuggestResult {
    private int numFound;//条数
    private String token;//查询的字符串
    private List<String> suggestResult;//结果集
	public int getNumFound() {
		return numFound;
	}
	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<String> getSuggestResult() {
		return suggestResult;
	}
	public void setSuggestResult(List<String> suggestResult) {
		this.suggestResult = suggestResult;
	}

    
}
