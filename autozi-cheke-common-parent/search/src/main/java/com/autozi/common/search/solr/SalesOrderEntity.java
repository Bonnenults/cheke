package com.autozi.common.search.solr;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.solr.client.solrj.beans.Field;


public class SalesOrderEntity implements Serializable{
	private static final long serialVersionUID = 7986942802845865483L;
//    public static final String excludeRegex = "[\\:\\?\\~\\^\\+]+";
	//替换掉除了中文、数字、英文字母之外的字符 
	//[*./-\\(\\)）℃×'，=）+（´×#°!@$%^&！￥…—『』{}|“”;:；\\?？<>《》`·\"\\]+
	//生成索引和查询时都过滤掉特殊字符
	public static final String excludeRegex = "[^ \\一-\\龥\\w]";
	/**
     * 多音字，项目取固定值
     */
    public static final HashMap<String,String> pinyinMap = new LinkedHashMap<String, String>() {
		{
			this.put("刹", "sha1");//为了与PinyinHelper.toHanyuPinyinStringArray方法保持一致，在正确拼音后面加上1
		}
	};
    @Field
	private Long version;
    @Field
	private String keyWord;     //商品名称、品牌名称、规格型号、出厂编码、订单编号
    @Field
    private String id;             			// 订货单编号
    @Field
	private Long orderHeaderId;					// 订单头编号
    @Field
    private Long referenceOrderId;   // 退货时关联订货单编号    ,如果是活动赠品单关联的是主订单
    @Field									
	private int orderStatus;       			// 订单状态(id)
    @Field									
	private double unitPrice;					// 订货价 退货价，优惠和折扣之后的价格
    @Field	
    private double realUnitPrice;     			// 订货关系中定义的价格
    @Field				
	private String orderingTime;          		// 订单申请时间，退货申请时间 @  
    @Field
	private int promotions;           	// 是否是活动的赠品单		
    @Field
	private double discountFee;				//折扣的优惠  //优惠到总金额 
    @Field
	private double totalMoney;				//总金额【单价*数量-优惠】
    @Field
	private int delivery; 						// WMSDCConstant.ShippingType: 0、自提，1、快递，2、顺丰快递
    @Field
	private double coupon;						// 优惠券金额
    @Field
	private long buyerId;//购买用户
    @Field
    private int billState;//默认为0，目前只用于退货单标识是否已生成退款单
    @Field
    private Integer confirmType;//审核类型IOrderStatusConstants：0/null-未审核，10-库存直接满足，20-补库满足，30-按单直通交付，40-门店交付满足
    @Field
    private Integer orderingQuantity;			// 订货：购数量， 退货：退货数量
    @Field
    private Integer confirmsQuantity;        //订退货的（连锁审核）确认数量
    @Field
    private Integer sellerStockChangeQuantity;//交货量
    @Field
    private Integer availableReturnQuantity;//可退数量
    @Field
    private Integer buyerStockChangeQuantity;//收货量
    @Field
    private Integer finalQuantity;//确认收货量
    @Field
    private Integer cancelQuantity;            //WMS强制取消的数量。分批发货以后实在没有货了，强制咔嚓掉部分数量。
    @Field
    private Integer remainingQuantity;       //待发货量，如果是分批发货的话，为了避免去查询orderDetail来计算该数值，所以这里冗余一个待发货量
    @Field				
	private String confirmsTime;          		//审核时间
    @Field
    private String updateTime;              //时间戳
    @Field
    private int orderSubType;//订单类型：OrderHeader.TYPE_ORDER=订货 OrderHeader.TYPE_RETURN=退货
    @Field
    private int orderType;//订单类型，订单或退单
    @Field
    private String note;  //（订单备注）
    @Field
    private String goodsUnit;
    @Field
    private Long branchCompanyId;
    @Field
    private Double supplierOrderPrice;
    @Field
    private int confirmStatus;
    @Field
    private Long supplierId;
    @Field
    private String optClient;//客户端类型【订单终端支付页面订单来源】
    @Field
    private int payStatus;
    @Field
    private Integer settleType;//结算方式
    @Field
    private Integer shippingType;//配送方式
    @Field
    private Long cdcWarehouseId;//门店仓库ID
    @Field
    private Long cdcPartyId;//门店主体
    @Field
    private String chainRemark;//平台订单审核备注
    @Field
    private String supplierRemark;//供应商订单审核备注
    @Field
    private Long realSupplierId;//真实供应商id
    @Field
    private Integer supplierType;//供应商类型
    @Field
    private Integer partyRank;//主体性质
    @Field
    private Long  confirmsPartyId;//平台审核  设置默认值0
    @Field
    private Long clientServiceId;//客户服务商ID
    @Field
    private Long clientServiceUserId;//客户服务商员工ID
    @Field
	private String cancelRemark;//取消订单原因
	//客户服务商用
    @Field
	private Double clientServiceSalesFee;//销售补贴
    @Field
	private Double clientServiceServiceFee;//客户服务费
    @Field
	private Double clientServiceTransportFee;//配送服务费
    @Field
	private String zgsName;//经营主体
    @Field
	private String confirmsName;//审核人
    @Field
	private Long vmiWareHouseId;//vmi库
    @Field
    private Long promotionId;
    
    //Goods
    @Field
	private Long goodsId;               		// 商品id
    @Field
	private String goodsStyle;          		// 规格类型	
    @Field									
	private String goodsName;					// 商品名称
    @Field
	private String goodsImageUrl;					// 商品图片地址
    @Field
	private String brandName;					// 名称				
    @Field
	private String b2bCode;      				// 商品编码
    @Field										
	private String serialNumber;                // 出厂编号	
    @Field									
	private Long brandId;             			// 品牌id		
    
    @Field
    private String hasCanReturnOrder;//是否有可退的订单("true":有，"false"：无)
    @Field
    private String hasNoPayOrder;//是否有未付款的订单("true":有，"false"：无)
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getOrderHeaderId() {
		return orderHeaderId;
	}
	public void setOrderHeaderId(Long orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}
	public Long getReferenceOrderId() {
		return referenceOrderId;
	}
	public void setReferenceOrderId(Long referenceOrderId) {
		this.referenceOrderId = referenceOrderId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getRealUnitPrice() {
		return realUnitPrice;
	}
	public void setRealUnitPrice(double realUnitPrice) {
		this.realUnitPrice = realUnitPrice;
	}
	
	public int getPromotions() {
		return promotions;
	}
	public void setPromotions(int promotions) {
		this.promotions = promotions;
	}
	public double getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(double discountFee) {
		this.discountFee = discountFee;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getDelivery() {
		return delivery;
	}
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public int getBillState() {
		return billState;
	}
	public void setBillState(int billState) {
		this.billState = billState;
	}
	public Integer getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(Integer confirmType) {
		this.confirmType = confirmType;
	}
	public Integer getOrderingQuantity() {
		return orderingQuantity;
	}
	public void setOrderingQuantity(Integer orderingQuantity) {
		this.orderingQuantity = orderingQuantity;
	}
	public Integer getConfirmsQuantity() {
		return confirmsQuantity;
	}
	public void setConfirmsQuantity(Integer confirmsQuantity) {
		this.confirmsQuantity = confirmsQuantity;
	}
	public Integer getSellerStockChangeQuantity() {
		return sellerStockChangeQuantity;
	}
	public void setSellerStockChangeQuantity(Integer sellerStockChangeQuantity) {
		this.sellerStockChangeQuantity = sellerStockChangeQuantity;
	}
	public Integer getAvailableReturnQuantity() {
		return availableReturnQuantity;
	}
	public void setAvailableReturnQuantity(Integer availableReturnQuantity) {
		this.availableReturnQuantity = availableReturnQuantity;
	}
	public Integer getCancelQuantity() {
		return cancelQuantity;
	}
	public void setCancelQuantity(Integer cancelQuantity) {
		this.cancelQuantity = cancelQuantity;
	}
	public Integer getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(Integer remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public String getOrderingTime() {
		return orderingTime;
	}
	public void setOrderingTime(String orderingTime) {
		this.orderingTime = orderingTime;
	}
	public String getConfirmsTime() {
		return confirmsTime;
	}
	public void setConfirmsTime(String confirmsTime) {
		this.confirmsTime = confirmsTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(int orderSubType) {
		this.orderSubType = orderSubType;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	public Long getBranchCompanyId() {
		return branchCompanyId;
	}
	public void setBranchCompanyId(Long branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
	public Double getSupplierOrderPrice() {
		return supplierOrderPrice;
	}
	public void setSupplierOrderPrice(Double supplierOrderPrice) {
		this.supplierOrderPrice = supplierOrderPrice;
	}
	public int getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getOptClient() {
		return optClient;
	}
	public void setOptClient(String optClient) {
		this.optClient = optClient;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getSettleType() {
		return settleType;
	}
	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}
	public Integer getShippingType() {
		return shippingType;
	}
	public void setShippingType(Integer shippingType) {
		this.shippingType = shippingType;
	}
	public Long getCdcWarehouseId() {
		return cdcWarehouseId;
	}
	public void setCdcWarehouseId(Long cdcWarehouseId) {
		this.cdcWarehouseId = cdcWarehouseId;
	}
	public Long getCdcPartyId() {
		return cdcPartyId;
	}
	public void setCdcPartyId(Long cdcPartyId) {
		this.cdcPartyId = cdcPartyId;
	}
	public String getChainRemark() {
		return chainRemark;
	}
	public void setChainRemark(String chainRemark) {
		this.chainRemark = chainRemark;
	}
	public String getSupplierRemark() {
		return supplierRemark;
	}
	public void setSupplierRemark(String supplierRemark) {
		this.supplierRemark = supplierRemark;
	}
	public Long getRealSupplierId() {
		return realSupplierId;
	}
	public void setRealSupplierId(Long realSupplierId) {
		this.realSupplierId = realSupplierId;
	}
	public Integer getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}
	public Integer getPartyRank() {
		return partyRank;
	}
	public void setPartyRank(Integer partyRank) {
		this.partyRank = partyRank;
	}
	public Long getConfirmsPartyId() {
		return confirmsPartyId;
	}
	public void setConfirmsPartyId(Long confirmsPartyId) {
		this.confirmsPartyId = confirmsPartyId;
	}
	public Long getClientServiceId() {
		return clientServiceId;
	}
	public void setClientServiceId(Long clientServiceId) {
		this.clientServiceId = clientServiceId;
	}
	public Long getClientServiceUserId() {
		return clientServiceUserId;
	}
	public void setClientServiceUserId(Long clientServiceUserId) {
		this.clientServiceUserId = clientServiceUserId;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public Double getClientServiceSalesFee() {
		return clientServiceSalesFee;
	}
	public void setClientServiceSalesFee(Double clientServiceSalesFee) {
		this.clientServiceSalesFee = clientServiceSalesFee;
	}
	public Double getClientServiceServiceFee() {
		return clientServiceServiceFee;
	}
	public void setClientServiceServiceFee(Double clientServiceServiceFee) {
		this.clientServiceServiceFee = clientServiceServiceFee;
	}
	public Double getClientServiceTransportFee() {
		return clientServiceTransportFee;
	}
	public void setClientServiceTransportFee(Double clientServiceTransportFee) {
		this.clientServiceTransportFee = clientServiceTransportFee;
	}
	public String getZgsName() {
		return zgsName;
	}
	public void setZgsName(String zgsName) {
		this.zgsName = zgsName;
	}
	public String getConfirmsName() {
		return confirmsName;
	}
	public void setConfirmsName(String confirmsName) {
		this.confirmsName = confirmsName;
	}
	public Long getVmiWareHouseId() {
		return vmiWareHouseId;
	}
	public void setVmiWareHouseId(Long vmiWareHouseId) {
		this.vmiWareHouseId = vmiWareHouseId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsStyle() {
		return goodsStyle;
	}
	public void setGoodsStyle(String goodsStyle) {
		this.goodsStyle = goodsStyle;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsImageUrl() {
		return goodsImageUrl;
	}
	public void setGoodsImageUrl(String goodsImageUrl) {
		this.goodsImageUrl = goodsImageUrl;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getB2bCode() {
		return b2bCode;
	}
	public void setB2bCode(String b2bCode) {
		this.b2bCode = b2bCode;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Integer getBuyerStockChangeQuantity() {
		return buyerStockChangeQuantity;
	}
	public void setBuyerStockChangeQuantity(Integer buyerStockChangeQuantity) {
		this.buyerStockChangeQuantity = buyerStockChangeQuantity;
	}
	public Integer getFinalQuantity() {
		return finalQuantity;
	}
	public void setFinalQuantity(Integer finalQuantity) {
		this.finalQuantity = finalQuantity;
	}
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	public String getHasCanReturnOrder() {
		return hasCanReturnOrder;
	}
	public void setHasCanReturnOrder(String hasCanReturnOrder) {
		this.hasCanReturnOrder = hasCanReturnOrder;
	}
	public String getHasNoPayOrder() {
		return hasNoPayOrder;
	}
	public void setHasNoPayOrder(String hasNoPayOrder) {
		this.hasNoPayOrder = hasNoPayOrder;
	}
	
}
