/**
 * 文件名称   : com.autozi.common.search.es.entity.AutoziPasscarGoods.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-7
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;


/**
 * <PRE>
 * 
 * 中文描述：Es商品实体
 * 
 * </PRE>
 * @version 1.0.0
 */
@Document(indexName="autozi_passcar",replicas=1,shards=6,type="goods")
public class AutoziPasscarGoods implements Serializable {
	private static final long serialVersionUID = 4174462328036198870L;
	public static final Double _default_shopping_price = 9999999999.99d;
	public static final Double _default_search_price = 9999999999.88d;
	public static final String _default_str_value = "";
	
	@Id
	@Field(index=FieldIndex.not_analyzed)
	private long id;
	@Field(index=FieldIndex.not_analyzed)
	private String b2bCode;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String name;
	@Field
	private String nameFirstLetter;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String standardName;
	@Field
	private String goodsAlias;
	@Field
	private String keywords;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String goodsStyle;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String serialNumber;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String carModel;
	@Field
	private String oemAlias;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String goodsRemark;
	@Field(index=FieldIndex.not_analyzed)
	private long brandId;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String brandName;
	@Field(index=FieldIndex.not_analyzed)
	private long productId;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String productName;
	@Field(index=FieldIndex.not_analyzed)
	private String productCode;
	@Field(index=FieldIndex.not_analyzed)
	private long supplierId;
	@Field(index=FieldIndex.not_analyzed)
	private String supplierCode;
	@Field(searchAnalyzer="ik",analyzer="ik")
	private String supplierName;
	@Field(index=FieldIndex.not_analyzed)
	private int status;
	@Field(index=FieldIndex.not_analyzed)
	private int onShopping;
	@Field(index=FieldIndex.not_analyzed)
    private int onSaleStatus;           // 销售目录状录     参照 IGoodsStatus【去掉】
	@Field(index=FieldIndex.not_analyzed)
    private int onPurchaseStatus;       // 采购目录状态，参照 IGoodsStatus
	@Field(index=FieldIndex.not_analyzed)
	private Double purchasePrice;// 厂商价(￥)
	@Field(index=FieldIndex.not_analyzed)
	private Double purchasePriceRate; 	// 供应加价率(%)
	@Field(index=FieldIndex.not_analyzed)
    private Double newOrderPrice ;		// 申请变更价(￥)
	@Field(index=FieldIndex.not_analyzed)
    private Double orderPriceRate; 		// 建议零售加价率(%)
	@Field(index=FieldIndex.not_analyzed)
	private Double orderPrice;	// 统一结算价
	@Field(index=FieldIndex.not_analyzed)
	private Double retailPrice; // 建议零售价
	@Field(index=FieldIndex.not_analyzed)
	private Double shoppingPrice;	// 商城价【页面暂时没有用到，目前作为了一个判断条件：即当设置了会员价格时这个字段才有值】
	@Field(index=FieldIndex.not_analyzed)
	private String referencePriceFor4S;
	@Field(index=FieldIndex.not_analyzed)
    private Date addTime;		// 上架时间
	@Field(index=FieldIndex.not_analyzed)
    private Date updateTime;	// 更新时间戳
	@Field(index=FieldIndex.not_analyzed)
	private String goodsUnit;	// 计量单位
	@Field(index=FieldIndex.not_analyzed)
	private Integer deliveryCycle; // 配送周期
	@Field(index=FieldIndex.not_analyzed)
	private String wassList; 	// 商品对应的区域商城列表
	@Field(index=FieldIndex.not_analyzed)
	private String subChainList; 	// 对应的子公司列表
	
	// ADD BY LIHF@Autozi.com 2017-9-15下午3:21:29 START 需求描述：建设检索名称，剔除特殊字符的字段
	private String nameFS;
	private String standardNameFS;
	private String goodsAliasFS;
	private String keywordsFS;
	private String goodsStyleFS;
	private String serialNumberFS;
	private String carModelFS;
	private String oemAliasFS;
	private String goodsRemarkFS;
	private String brandNameFS;
	private String productNameFS;
	private String supplierNameFS;
	// ADD BY LIHF@Autozi.com 2017-9-15下午3:21:29 END
	
	@Override
	public String toString() {
		return "id:"+id+"\tname:"+name+"\tbrandName:"+brandName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getB2bCode() {
		return b2bCode;
	}

	public void setB2bCode(String b2bCode) {
		this.b2bCode = b2bCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameFirstLetter() {
		return nameFirstLetter;
	}

	public void setNameFirstLetter(String nameFirstLetter) {
		this.nameFirstLetter = nameFirstLetter;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getGoodsAlias() {
		return goodsAlias;
	}

	public void setGoodsAlias(String goodsAlias) {
		this.goodsAlias = goodsAlias;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getGoodsStyle() {
		return goodsStyle;
	}

	public void setGoodsStyle(String goodsStyle) {
		this.goodsStyle = goodsStyle;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getOemAlias() {
		return oemAlias;
	}

	public void setOemAlias(String oemAlias) {
		this.oemAlias = oemAlias;
	}

	public String getGoodsRemark() {
		return goodsRemark;
	}

	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOnShopping() {
		return onShopping;
	}

	public void setOnShopping(int onShopping) {
		this.onShopping = onShopping;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getShoppingPrice() {
		return shoppingPrice;
	}

	public void setShoppingPrice(Double shoppingPrice) {
		this.shoppingPrice = shoppingPrice;
	}

	public String getReferencePriceFor4S() {
		return referencePriceFor4S;
	}

	public void setReferencePriceFor4S(String referencePriceFor4S) {
		this.referencePriceFor4S = referencePriceFor4S;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getOnSaleStatus() {
		return onSaleStatus;
	}

	public void setOnSaleStatus(int onSaleStatus) {
		this.onSaleStatus = onSaleStatus;
	}

	public int getOnPurchaseStatus() {
		return onPurchaseStatus;
	}

	public void setOnPurchaseStatus(int onPurchaseStatus) {
		this.onPurchaseStatus = onPurchaseStatus;
	}

	public Double getPurchasePriceRate() {
		return purchasePriceRate;
	}

	public void setPurchasePriceRate(Double purchasePriceRate) {
		this.purchasePriceRate = purchasePriceRate;
	}

	public Double getNewOrderPrice() {
		return newOrderPrice;
	}

	public void setNewOrderPrice(Double newOrderPrice) {
		this.newOrderPrice = newOrderPrice;
	}

	public Double getOrderPriceRate() {
		return orderPriceRate;
	}

	public void setOrderPriceRate(Double orderPriceRate) {
		this.orderPriceRate = orderPriceRate;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public Integer getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(Integer deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	public String getWassList() {
		return wassList;
	}

	public void setWassList(String wassList) {
		this.wassList = wassList;
	}

	public String getSubChainList() {
		return subChainList;
	}

	public void setSubChainList(String subChainList) {
		this.subChainList = subChainList;
	}

	public String getNameFS() {
		return nameFS;
	}

	public void setNameFS(String nameFS) {
		this.nameFS = nameFS;
	}

	public String getStandardNameFS() {
		return standardNameFS;
	}

	public void setStandardNameFS(String standardNameFS) {
		this.standardNameFS = standardNameFS;
	}

	public String getGoodsAliasFS() {
		return goodsAliasFS;
	}

	public void setGoodsAliasFS(String goodsAliasFS) {
		this.goodsAliasFS = goodsAliasFS;
	}

	public String getKeywordsFS() {
		return keywordsFS;
	}

	public void setKeywordsFS(String keywordsFS) {
		this.keywordsFS = keywordsFS;
	}

	public String getGoodsStyleFS() {
		return goodsStyleFS;
	}

	public void setGoodsStyleFS(String goodsStyleFS) {
		this.goodsStyleFS = goodsStyleFS;
	}

	public String getSerialNumberFS() {
		return serialNumberFS;
	}

	public void setSerialNumberFS(String serialNumberFS) {
		this.serialNumberFS = serialNumberFS;
	}

	public String getCarModelFS() {
		return carModelFS;
	}

	public void setCarModelFS(String carModelFS) {
		this.carModelFS = carModelFS;
	}

	public String getOemAliasFS() {
		return oemAliasFS;
	}

	public void setOemAliasFS(String oemAliasFS) {
		this.oemAliasFS = oemAliasFS;
	}

	public String getGoodsRemarkFS() {
		return goodsRemarkFS;
	}

	public void setGoodsRemarkFS(String goodsRemarkFS) {
		this.goodsRemarkFS = goodsRemarkFS;
	}

	public String getBrandNameFS() {
		return brandNameFS;
	}

	public void setBrandNameFS(String brandNameFS) {
		this.brandNameFS = brandNameFS;
	}

	public String getProductNameFS() {
		return productNameFS;
	}

	public void setProductNameFS(String productNameFS) {
		this.productNameFS = productNameFS;
	}

	public String getSupplierNameFS() {
		return supplierNameFS;
	}

	public void setSupplierNameFS(String supplierNameFS) {
		this.supplierNameFS = supplierNameFS;
	}
	
}
