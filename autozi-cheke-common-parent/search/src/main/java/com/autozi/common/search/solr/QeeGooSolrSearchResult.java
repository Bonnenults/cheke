package com.autozi.common.search.solr;

import java.util.List;

import com.autozi.common.core.page.Page;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-4-25 上午11:46
 */
public class QeeGooSolrSearchResult {
    private Page<IndexEntity> indexEntityPage;
    private List<QeeGooFacetField> facetFieldList;
    private Page<SalesOrderHeaderEntity> salesOrderHeaderEntityPage;//订单头
    private Page<SalesOrderEntity> salesOrderEntityPage;//订单

    public Page<IndexEntity> getIndexEntityPage() {
        return indexEntityPage;
    }

    public void setIndexEntityPage(Page<IndexEntity> indexEntityPage) {
        this.indexEntityPage = indexEntityPage;
    }

    public List<QeeGooFacetField> getFacetFieldList() {
        return facetFieldList;
    }

    public void setFacetFieldList(List<QeeGooFacetField> facetFieldList) {
        this.facetFieldList = facetFieldList;
    }

	public Page<SalesOrderHeaderEntity> getSalesOrderHeaderEntityPage() {
		return salesOrderHeaderEntityPage;
	}

	public void setSalesOrderHeaderEntityPage(
			Page<SalesOrderHeaderEntity> salesOrderHeaderEntityPage) {
		this.salesOrderHeaderEntityPage = salesOrderHeaderEntityPage;
	}

	public Page<SalesOrderEntity> getSalesOrderEntityPage() {
		return salesOrderEntityPage;
	}

	public void setSalesOrderEntityPage(Page<SalesOrderEntity> salesOrderEntityPage) {
		this.salesOrderEntityPage = salesOrderEntityPage;
	}
    
}
