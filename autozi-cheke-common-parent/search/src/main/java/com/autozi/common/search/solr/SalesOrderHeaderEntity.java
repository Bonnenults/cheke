package com.autozi.common.search.solr;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.solr.client.solrj.beans.Field;


public class SalesOrderHeaderEntity implements Serializable{
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
	private String keyWord;     //商品名称、订单号、规格型号、									
	
	//SalesOrderHeader
    @Field
	private String id;					// 订单头编号
    @Field
    private long sellerId;          //卖家ID
    @Field
    private int sellerType;         //卖家类型
    @Field
    private Long sellerAreaId;         //卖家区域Id
    @Field
	private long buyerId;//购买用户
    @Field
    private String buyerName;//购买用户名称
    @Field
    private String buyerCode;//购买用户编码
    @Field
    private int buyerType;  //买家类型
    @Field
    private Long buyUser;                         //采购员ID  user表
    @Field
    private long operatorId;                //操作员
    @Field
    private Long salesmanId;             //业务员ID,加盟服务商所属客户的业务员ID
    @Field
    private String createDate;       //创建时间戳
    @Field
    private String updateTime;       //最后一次更新时间
    @Field
    private double totalMoney;                	// 总金额
    @Field
    private int totalAmount;                // 总数量
    @Field
    private double orderingTotalMoney;			//订单总金额（下订单时的总金额，以后不会再改变）
    @Field
    private int orderType; //订单类型：10订单或20退单
    @Field
    private int orderClass;                   // 订单类别     不同的单据，B2B订单,POS,代客下单，商城订单//参照order
    @Field
    private long areaId;                    //收货地址的区域ID
    @Field
	private String orderAddress;              //订单的地址	
    @Field
	private String customer;                  //收货人
    @Field
	private String customerTel;               //收货人电话
    @Field
	private String customerPostCode;          //收货人邮编
    @Field
	private int delivery; 						// WMSDCConstant.ShippingType: 0、自提，1、快递，2、顺丰快递
    @Field
	private int settleType;					// 支付方式
    @Field
    private String note;                      //退货信息 （订单附言）
    @Field
	private String message;                   //提示信息
    @Field
    private double cashPaidAmount;  //现金支付金额
    @Field
    private double bankPaidAmount;  //银行支付金额
    @Field
    private String allOrderClosed;       //是否所有订单已关闭("true":已关闭，"false"：未关闭)
    @Field
    private String poNo;                    //补货单-采购合同号
    @Field
    private String cancelRemark;  //取消订单原因
    @Field
	private Integer orderSubType;//团购活动订单类型：OrderHeader.TYPE_GROUP_ORDER=预售团购订单
    @Field
    private Integer frontPayStatus; //预付款付款状态  0：未支付、1：已支付   IOrderStatusConstants
    @Field
	private Integer finalPayStatus; //尾款付款状态 0:不可付尾款 、1：可付尾款   IOrderStatusConstants
    @Field
	private Double frontTotalMoney; //子订单预付款总金额
    @Field
	private String deliverTime;	      //预售团购发货时间
    @Field
    private String couponIds;					// 多个Id之间用","分割；
    @Field
	private int orderHeaderStatus;			// 订单头状态 
    @Field
    private Long warehouseId; //FDC仓库ID
    @Field
    private String warehouseName;//仓库名称
    @Field
    private Long rdcWarehouseId; //RDC仓库ID
    @Field
    private String attendPromotion;   //参加促销 (true:参加，false:不参加)
    @Field
	private int payStatus; 					// 付款状态  0：未支付、1：已支付   IOrderStatusConstants
    @Field
    private String optClient; //下单终端
    @Field
    private String loanApplNo;//贷款申请编号
    @Field
    private Integer delFlag; //删除标记  1为删除
    @Field
	private Integer suitQuantity;//套装数量（用于团购订单，记录订购套数）
    @Field
    private Double suitUnitPrice;//套装单价（记录团购一套的价格）
    @Field
    private Long groupProId;//团购活动ID
    @Field
	private String prePayEndTime;       //预售团购最晚支付时间
    @Field
    private double coupons;
    @Field
    private String orderStatus;//子订单状态
    @Field
    private String hasCanReturnOrder;//是否有可退的订单("true":有，"false"：无)
    @Field
    private String hasNoPayOrder;//是否有未付款的订单("true":有，"false"：无)
    @Field
    private Long branchCompanyId;//子公司
    @Field
    private Integer orderQuantity;//子订单数量
    @Field
    private String brandId;//品牌Id，多个用空格隔开
    @Field
    private String productId;//品类Id，多个用空格隔开

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
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public int getSellerType() {
		return sellerType;
	}
	public void setSellerType(int sellerType) {
		this.sellerType = sellerType;
	}
	public Long getSellerAreaId() {
		return sellerAreaId;
	}
	public void setSellerAreaId(Long sellerAreaId) {
		this.sellerAreaId = sellerAreaId;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public int getBuyerType() {
		return buyerType;
	}
	public void setBuyerType(int buyerType) {
		this.buyerType = buyerType;
	}
	public Long getBuyUser() {
		return buyUser;
	}
	public void setBuyUser(Long buyUser) {
		this.buyUser = buyUser;
	}
	public long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	public Long getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
	}
	
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getOrderingTotalMoney() {
		return orderingTotalMoney;
	}
	public void setOrderingTotalMoney(double orderingTotalMoney) {
		this.orderingTotalMoney = orderingTotalMoney;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getOrderClass() {
		return orderClass;
	}
	public void setOrderClass(int orderClass) {
		this.orderClass = orderClass;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public String getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getCustomerPostCode() {
		return customerPostCode;
	}
	public void setCustomerPostCode(String customerPostCode) {
		this.customerPostCode = customerPostCode;
	}
	public int getDelivery() {
		return delivery;
	}
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}
	public int getSettleType() {
		return settleType;
	}
	public void setSettleType(int settleType) {
		this.settleType = settleType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getCashPaidAmount() {
		return cashPaidAmount;
	}
	public void setCashPaidAmount(double cashPaidAmount) {
		this.cashPaidAmount = cashPaidAmount;
	}
	public double getBankPaidAmount() {
		return bankPaidAmount;
	}
	public void setBankPaidAmount(double bankPaidAmount) {
		this.bankPaidAmount = bankPaidAmount;
	}
	public String getAllOrderClosed() {
		return allOrderClosed;
	}
	public void setAllOrderClosed(String allOrderClosed) {
		this.allOrderClosed = allOrderClosed;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public Integer getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(Integer orderSubType) {
		this.orderSubType = orderSubType;
	}
	public int getFrontPayStatus() {
		return frontPayStatus;
	}
	public void setFrontPayStatus(int frontPayStatus) {
		this.frontPayStatus = frontPayStatus;
	}
	public int getFinalPayStatus() {
		return finalPayStatus;
	}
	public void setFinalPayStatus(int finalPayStatus) {
		this.finalPayStatus = finalPayStatus;
	}
	public Double getFrontTotalMoney() {
		return frontTotalMoney;
	}
	public void setFrontTotalMoney(Double frontTotalMoney) {
		this.frontTotalMoney = frontTotalMoney;
	}
	public void setFrontPayStatus(Integer frontPayStatus) {
		this.frontPayStatus = frontPayStatus;
	}
	public void setFinalPayStatus(Integer finalPayStatus) {
		this.finalPayStatus = finalPayStatus;
	}
	
	public String getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}
	public int getOrderHeaderStatus() {
		return orderHeaderStatus;
	}
	public void setOrderHeaderStatus(int orderHeaderStatus) {
		this.orderHeaderStatus = orderHeaderStatus;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Long getRdcWarehouseId() {
		return rdcWarehouseId;
	}
	public void setRdcWarehouseId(Long rdcWarehouseId) {
		this.rdcWarehouseId = rdcWarehouseId;
	}
	public String getAttendPromotion() {
		return attendPromotion;
	}
	public void setAttendPromotion(String attendPromotion) {
		this.attendPromotion = attendPromotion;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public String getOptClient() {
		return optClient;
	}
	public void setOptClient(String optClient) {
		this.optClient = optClient;
	}
	public String getLoanApplNo() {
		return loanApplNo;
	}
	public void setLoanApplNo(String loanApplNo) {
		this.loanApplNo = loanApplNo;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getSuitQuantity() {
		return suitQuantity;
	}
	public void setSuitQuantity(Integer suitQuantity) {
		this.suitQuantity = suitQuantity;
	}
	public Double getSuitUnitPrice() {
		return suitUnitPrice;
	}
	public void setSuitUnitPrice(Double suitUnitPrice) {
		this.suitUnitPrice = suitUnitPrice;
	}
	public Long getGroupProId() {
		return groupProId;
	}
	public void setGroupProId(Long groupProId) {
		this.groupProId = groupProId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getPrePayEndTime() {
		return prePayEndTime;
	}
	public void setPrePayEndTime(String prePayEndTime) {
		this.prePayEndTime = prePayEndTime;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public double getCoupons() {
		return coupons;
	}
	public void setCoupons(double coupons) {
		this.coupons = coupons;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerCode() {
		return buyerCode;
	}
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Long getBranchCompanyId() {
		return branchCompanyId;
	}
	public void setBranchCompanyId(Long branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
