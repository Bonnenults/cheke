package com.autozi.common.search.solr;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.solr.client.solrj.beans.Field;

public class IndexEntity implements Serializable{
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
		/**
		 * 
		 */
		private static final long serialVersionUID = -3637797884612686088L;

		{
			this.put("刹", "sha1");//为了与PinyinHelper.toHanyuPinyinStringArray方法保持一致，在正确拼音后面加上1
		}
	};
    @Field
	private String id;   //
    
    public static final String idTypeJYJ = "JYJ";	// 积压件id后缀
    public static final String idTypeCCJ = "CCJ";	// 拆车件id后缀
    
    @Field
	private Long version;
    @Field
	private String keyWord;     //商品名称、品牌名称、规格型号、出厂编码、适用车型
    @Field
	private Long brandId;
    @Field
    private Long productId;
    @Field
	private Float price;
    @Field
	private String memberPrice1; //门店会员
    @Field
    private String memberPrice2; //分销会员
    @Field
    private String memberPrice3; //大客户
    @Field
    private String memberPrice4; //普通会员1
    @Field
    private String memberPrice5; //普通会员2
    @Field
    private String memberPrice6; //普通会员3
    @Field
    private String memberPrice7; //普通会员3
    @Field
    private String memberPrice8;
    @Field
    private String memberPrice9;
    @Field
    private String memberPrice10;
    @Field
    private String memberPrice11;
    @Field
    private String memberPrice12;
    @Field
    private String memberPrice13;
    @Field
    private String memberPrice14;
    @Field
    private String memberPrice15;
    @Field
    private String memberPrice16;
    @Field
    private String memberPrice17;
    @Field
    private String memberPrice18;
    @Field
    private String memberPrice19;
    @Field
    private String memberPrice20;
    @Field
    private String memberPrice21;
    @Field
    private String memberPrice22;
    @Field
    private String memberPrice23;
    @Field
    private String memberPrice24;
    @Field
    private String memberPrice25;
    @Field
    private String memberPrice26;
    @Field
    private String memberPrice27;
    @Field
    private String memberPrice28;
    @Field
    private String memberPrice29;
    @Field
    private String memberPrice30;
    @Field
    private String memberPrice31;
    @Field
    private String memberPrice32;
    @Field
    private String memberPrice33;
    @Field
    private String memberPrice34;
    @Field
    private String memberPrice35;
    @Field
    private String memberPrice36;
    @Field
    private String memberPrice37;
    @Field
    private String memberPrice38;
    @Field
    private String memberPrice39;
    @Field
    private String memberPrice40;
    @Field
    private String memberPrice41;
    @Field
    private String memberPrice42;
    @Field
    private String memberPrice43;
    @Field
    private String memberPrice44;
    @Field
    private String memberPrice45;
    @Field
    private String memberPrice46;
    @Field
    private String memberPrice47;
    @Field
    private String memberPrice48;
    @Field
    private String memberPrice49;
    @Field
    private String memberPrice50;
    @Field
    private String memberPrice51;
    @Field
    private String memberPrice52;
    @Field
    private String memberPrice53;
    @Field
    private String memberPrice54;
    @Field
    private String memberPrice55;
    @Field
    private String memberPrice56;
    @Field
    private String memberPrice57;
    @Field
    private String memberPrice58;
    @Field
    private String memberPrice59;
    @Field
    private String memberPrice60;
    @Field
    private String memberPrice61;
    @Field
    private String memberPrice62;
    @Field
    private String memberPrice63;
    @Field
    private String memberPrice64;
    @Field
    private String memberPrice65;
    @Field
    private String memberPrice66;
    @Field
    private String memberPrice67;
    @Field
    private String memberPrice68;
    @Field
    private String memberPrice69;
    @Field
    private String memberPrice70;
    @Field
    private String memberPrice71;
    @Field
    private String memberPrice72;
    @Field
    private String memberPrice73;
    @Field
    private String memberPrice74;
    @Field
    private String memberPrice75;
    @Field
    private String memberPrice76;
    @Field
    private String memberPrice77;
    @Field
    private String memberPrice78;
    @Field
    private String memberPrice79;
    @Field
    private String memberPrice80;
    @Field
    private String memberPrice81;
    @Field
    private String memberPrice82;
    @Field
    private String memberPrice83;
    @Field
    private String memberPrice84;
    @Field
    private String memberPrice85;
    @Field
    private String memberPrice86;
    @Field
    private String memberPrice87;
    @Field
    private String memberPrice88;
    @Field
    private String memberPrice89;
    @Field
    private String memberPrice90;
    @Field
    private String memberPrice91;
    @Field
    private String memberPrice92;
    @Field
    private String memberPrice93;
    @Field
    private String memberPrice94;
    @Field
    private String memberPrice95;
    @Field
    private String memberPrice96;
    @Field
    private String memberPrice97;
    @Field
    private String memberPrice98;
    @Field
    private String memberPrice99;
    @Field
    private String memberPrice100;
    @Field
    private String memberPrice101;
    @Field
    private String memberPrice102;
    @Field
    private String memberPrice103;
    @Field
    private String memberPrice104;
    @Field
    private String memberPrice105;
    @Field
    private String memberPrice106;
    @Field
    private String memberPrice107;
    @Field
    private String memberPrice108;
    @Field
    private String memberPrice109;
    @Field
    private String memberPrice110;
    @Field
    private String memberPrice111;
    @Field
    private String memberPrice112;
    @Field
    private String memberPrice113;
    @Field
    private String memberPrice114;
    @Field
    private String memberPrice115;
    @Field
    private String memberPrice116;
    @Field
    private String memberPrice117;
    @Field
    private String memberPrice118;
    @Field
    private String memberPrice119;
    @Field
    private String memberPrice120;
    @Field
    private String memberPrice121;
    @Field
    private String memberPrice122;
    @Field
    private String memberPrice123;
    @Field
    private String memberPrice124;
    @Field
    private String memberPrice125;
    @Field
    private String memberPrice126;//滴滴价
    @Field
    private String memberPrice127;//R
    @Field
    private String memberPrice128;//R建议零售价
    @Field
	private int priceLevel;
    @Field
	private Long category1Id;
    @Field
	private Long category2Id;
    @Field
	private Long category3Id;
    @Field
	private Long category4Id;
    @Field
	private Long category5Id;
    @Field
    private String categoryProp0;  //支持查询的分类属性值。属性名，外部匹配
    @Field
    private String categoryProp1;
    @Field
    private String categoryProp2;
    @Field
    private String categoryProp3;
    @Field
    private String categoryProp4;
    @Field
    private String categoryProp5;
    @Field
    private String categoryProp6;
    @Field
    private String categoryProp7;
    @Field
    private String categoryProp8;
    @Field
    private String categoryProp9;
    @Field
	private String carBrandId;
    @Field
	private String carSeriesId;
    @Field
	private String carModelId;    //car_model_id 空格分开
    @Field
    private String carLogoId;    //car_model_id 空格分开
    @Field
	private String updateTime;   //201303080843
    @Field
	private long flagshipStoreId;   //旗舰店ID
    @Field
	private String carBrandAndSeries;   //商品关联的车型品牌和车系json串
    @Field
	private String oem;
    @Field
	private String carYear;
    @Field
	private String carCapacity;
//    @Field
//    private String wareHouseId;	//空格分开
    @Field
    private Long wearingCategory1Id;
    @Field
    private Long wearingCategory2Id;
    @Field
    private String oemAlias;
    @Field
    private long supplierId;        	// 供应商ID
    @Field
    private String supplierName;
    @Field
    private String goodsName;
    @Field
    private int hasImages;		//是否有商品图片
    @Field
    private int salesQuantity;//销量 ，ID存商品ID
    @Field
    private String goodsBrandNameAndCategory2Name;//品牌及2级分类名称（中间不带空格）
    @Field
    private String goodsBrandNameAndGoodsName;//品牌及商品名称
    @Field
    private String goodsBrandNameAndCategory2Name_bs;//品牌及2级分类名称（中间带空格）
    @Field
    private String goodsBrandNameAndGoodsName_bs;//品牌及商品名称（中间带空格）
    @Field
    private String goodsStyle;
    @Field
    private String goodsName_goodsStyle;
    @Field
    private String goodsName_goodsStyle_serialNumber;
    
    //------------20150630 zhiyun.chen 新增------
    @Field
    private String serialNumber;        // 出厂编号
    @Field
    private String brandName;//品牌名称
    @Field
    private String promotionTitle;		// 促销标题
    @Field
    private String smallImagePath;
    @Field
    private String middleImagePath;
    @Field
    private String largeImagePath;
    @Field
    private String categoryType;

    //----2015-11-18 @pengfei.wang 新增 商品备注：目前用于原厂件SKU，用于区别使用在具体哪个车型上
    @Field
    private String goodsRemark;
    //----2015-11-19 @pengfei.wang 新增规格型号查询字段（去点空格特殊字符）
    @Field
    private String goodsStyleQuery;
    //------------2015-12-22 zhiyun.chen 新增---商品在仓库的上下架范围（中驰、驰加）---
    @Field
    private String areaShoppingStoreScope;//区域商城范围
    @Field
    private String goodsNameDisplay;    //列表页显示
    //===============价格等级范围===============//
    @Field
	private String scopeMemberPrice1; //门店会员
    @Field
    private String scopeMemberPrice2; //分销会员
    @Field
    private String scopeMemberPrice3; //大客户
    @Field
    private String scopeMemberPrice4; //普通会员1
    @Field
    private String scopeMemberPrice5; //普通会员2
    @Field
    private String scopeMemberPrice6; //普通会员3
    @Field
    private String scopeMemberPrice7; //普通会员3
    @Field
    private String scopeMemberPrice8;
    @Field
    private String scopeMemberPrice9;
    @Field
    private String scopeMemberPrice10;
    @Field
    private String scopeMemberPrice11;
    @Field
    private String scopeMemberPrice12;
    @Field
    private String scopeMemberPrice13;
    @Field
    private String scopeMemberPrice14;
    @Field
    private String scopeMemberPrice15;
    @Field
    private String scopeMemberPrice16;
    @Field
    private String scopeMemberPrice17;
    @Field
    private String scopeMemberPrice18;
    @Field
    private String scopeMemberPrice19;
    @Field
    private String scopeMemberPrice20;
    @Field
    private String scopeMemberPrice21;
    @Field
    private String scopeMemberPrice22;
    @Field
    private String scopeMemberPrice23;
    @Field
    private String scopeMemberPrice24;
    @Field
    private String scopeMemberPrice25;
    @Field
    private String scopeMemberPrice26;
    @Field
    private String scopeMemberPrice27;
    @Field
    private String scopeMemberPrice28;
    @Field
    private String scopeMemberPrice29;
    @Field
    private String scopeMemberPrice30;
    @Field
    private String scopeMemberPrice31;
    @Field
    private String scopeMemberPrice32;
    @Field
    private String scopeMemberPrice33;
    @Field
    private String scopeMemberPrice34;
    @Field
    private String scopeMemberPrice35;
    @Field
    private String scopeMemberPrice36;
    @Field
    private String scopeMemberPrice37;
    @Field
    private String scopeMemberPrice38;
    @Field
    private String scopeMemberPrice39;
    @Field
    private String scopeMemberPrice40;
    @Field
    private String scopeMemberPrice41;
    @Field
    private String scopeMemberPrice42;
    @Field
    private String scopeMemberPrice43;
    @Field
    private String scopeMemberPrice44;
    @Field
    private String scopeMemberPrice45;
    @Field
    private String scopeMemberPrice46;
    @Field
    private String scopeMemberPrice47;
    @Field
    private String scopeMemberPrice48;
    @Field
    private String scopeMemberPrice49;
    @Field
    private String scopeMemberPrice50;
    @Field
    private String scopeMemberPrice51;
    @Field
    private String scopeMemberPrice52;
    @Field
    private String scopeMemberPrice53;
    @Field
    private String scopeMemberPrice54;
    @Field
    private String scopeMemberPrice55;
    @Field
    private String scopeMemberPrice56;
    @Field
    private String scopeMemberPrice57;
    @Field
    private String scopeMemberPrice58;
    @Field
    private String scopeMemberPrice59;
    @Field
    private String scopeMemberPrice60;
    @Field
    private String scopeMemberPrice61;
    @Field
    private String scopeMemberPrice62;
    @Field
    private String scopeMemberPrice63;
    @Field
    private String scopeMemberPrice64;
    @Field
    private String scopeMemberPrice65;
    @Field
    private String scopeMemberPrice66;
    @Field
    private String scopeMemberPrice67;
    @Field
    private String scopeMemberPrice68;
    @Field
    private String scopeMemberPrice69;
    @Field
    private String scopeMemberPrice70;
    @Field
    private String scopeMemberPrice71;
    @Field
    private String scopeMemberPrice72;
    @Field
    private String scopeMemberPrice73;
    @Field
    private String scopeMemberPrice74;
    @Field
    private String scopeMemberPrice75;
    @Field
    private String scopeMemberPrice76;
    @Field
    private String scopeMemberPrice77;
    @Field
    private String scopeMemberPrice78;
    @Field
    private String scopeMemberPrice79;
    @Field
    private String scopeMemberPrice80;
    @Field
    private String scopeMemberPrice81;
    @Field
    private String scopeMemberPrice82;
    @Field
    private String scopeMemberPrice83;
    @Field
    private String scopeMemberPrice84;
    @Field
    private String scopeMemberPrice85;
    @Field
    private String scopeMemberPrice86;
    @Field
    private String scopeMemberPrice87;
    @Field
    private String scopeMemberPrice88;
    @Field
    private String scopeMemberPrice89;
    @Field
    private String scopeMemberPrice90;
    @Field
    private String scopeMemberPrice91;
    @Field
    private String scopeMemberPrice92;
    @Field
    private String scopeMemberPrice93;
    @Field
    private String scopeMemberPrice94;
    @Field
    private String scopeMemberPrice95;
    @Field
    private String scopeMemberPrice96;
    @Field
    private String scopeMemberPrice97;
    @Field
    private String scopeMemberPrice98;
    @Field
    private String scopeMemberPrice99;
    @Field
    private String scopeMemberPrice100;
    @Field
    private String scopeMemberPrice101;
    @Field
    private String scopeMemberPrice102;
    @Field
    private String scopeMemberPrice103;
    @Field
    private String scopeMemberPrice104;
    @Field
    private String scopeMemberPrice105;
    @Field
    private String scopeMemberPrice106;
    @Field
    private String scopeMemberPrice107;
    @Field
    private String scopeMemberPrice108;
    @Field
    private String scopeMemberPrice109;
    @Field
    private String scopeMemberPrice110;
    @Field
    private String scopeMemberPrice111;
    @Field
    private String scopeMemberPrice112;
    @Field
    private String scopeMemberPrice113;
    @Field
    private String scopeMemberPrice114;
    @Field
    private String scopeMemberPrice115;
    @Field
    private String scopeMemberPrice116;
    @Field
    private String scopeMemberPrice117;
    @Field
    private String scopeMemberPrice118;
    @Field
    private String scopeMemberPrice119;
    @Field
    private String scopeMemberPrice120;
    @Field
    private String scopeMemberPrice121;
    @Field
    private String scopeMemberPrice122;
    @Field
    private String scopeMemberPrice123;
    @Field
    private String scopeMemberPrice124;
    @Field
    private String scopeMemberPrice125;
    @Field
    private String scopeMemberPrice126;//滴滴价
    @Field
    private String scopeMemberPrice127;//R
    @Field
    private String scopeMemberPrice128;//R建议零售价
    //------------2016-5-25 zhiyun.chen 询价商品范围---
    @Field
    private String askPriceScope;//询价商品范围(存区域商城ID)
   //------------2016-5-25 zhiyun.chen 询价商品范围---

    //------------2016-11-22 haiyang.qi 商品所属旗舰店Id---
    @Field
    private String flagshipStoreIds;    //flagship_store_ids 空格分开
    //------------2016-11-22 haiyang.qi 商品所属旗舰店Id---
    @Field
    private String referencePriceFor4S;   //4S店参考价格

    //------------2017-01-06 haiyang.qi 商品排序设置---
    @Field
    private Integer sort;
    //------------2017-01-06 haiyang.qi 商品排序设置---
    
    // ------------20170213--积压件------binbin.lee----------
    @Field
    private String jyjAreaId;	// 区域id （最小）
    @Field
    private	String jyjAreaName;	// 区域名称
    @Field
    private String jyjProductType;	// 配件类型（市场原厂件、4S店原厂件）
    @Field
    private String jyjGeneral;	// 是否通用件
    @Field
    private String jyjApplicableModel;	// 非通用件车型 描述信息
    @Field
    private Float jyjShoppingPrice;	// jyj显示价格 --20170223	
    @Field
    private String jyjGoodsNameInfo;	// jyjGoodsNameInfo 积压件商品名称含有非法字符以及空格，不参与查询条件（防止影响商城goodsName查询条件）
    
    // ------------20170213--积压件------binbin.lee----------
    @Field
    private String	goodsSource; // 商品来源:autozi,jyj
    @Field
    private String cbSort;//全车件2级分类+品牌 的组合排序
    // @Binbin.Lee 2017-4-28 有库存的商品对应仓库Id --start--
    @Field
    private String isGoodsQuantityStockId; // 有库存的商品对应仓库Id (含有多个)
    // @Binbin.Lee 2017-4-28 有库存的商品对应仓库Id --end--
    // @Binbin.Lee 2017-5-11 添加挤压将结算价格  --Start--
    @Field
    private Float jyjSettlePrice;
    // @Binbin.Lee 2017-5-11 添加挤压将结算价格  --end--


    //商品对应有库存的FSId --start-- By QiHaiYang
    @Field
    private String isGoodsQuantityFSId; // FS有库存的商品对应FS仓库Id (含有多个)
    //商品对应有库存的FSId --end-- By QiHaiYang
    
    // @Binbin.Lee 2017-6-23 V4.9.1 添加积压件商品类型 0积压件, 1正常--start--
    @Field
    private Integer jyjGoodsType;	//0积压件, 1正常, 2拆车件
    // @Binbin.Lee 2017-6-23 V4.9.1 添加积压件商品类型 0积压件, 1正常--end--
    
    // @Binbin.LEE 2017-7-24 v4.9.4 添加字段 --start--
    @Field
    private Integer jyjInvoiceTag ;	// 商品是否能够开发票
    @Field
    private Integer jyjShowTag;	// 积压件是否展示 0:不展示/1：展示
    @Field
    private Float jyjTradePrice;	// 积压件批发价
    // @Binbin.LEE 2017-7-24 v4.9.4 添加字段 --start--
    
    @Field
	private String partyRScope;    //R的范围（拥有商品的R的主体ID，多个用逗号隔开）
    @Field
	private String partyRChangePrice;    //小的设置的销售价（小的设置的销售价，多个用逗号隔开）
    @Field
	private String partyRChangePriceScope;    //小的设置的销售价的范围（小的设置的销售价范围，多个用逗号隔开）
    @Field
	private Integer productBrandType;    //商品类型（易损件、车型件、车胎、工具）
    
    
    /*拆车件三级分类字段*/
    @Field
    private Long ccjCategory1Id;
    @Field
    private Long ccjCategory2Id;
    @Field
    private Long ccjCategory3Id;
    
    @Field
    private Integer ccjProductQuality;
    /*拆车件分类字段*/
    
	public Long getCcjCategory1Id() {
		return ccjCategory1Id;
	}

	public Integer getCcjProductQuality() {
		return ccjProductQuality;
	}

	public void setCcjProductQuality(Integer ccjProductQuality) {
		this.ccjProductQuality = ccjProductQuality;
	}

	public void setCcjCategory1Id(Long ccjCategory1Id) {
		this.ccjCategory1Id = ccjCategory1Id;
	}

	public Long getCcjCategory2Id() {
		return ccjCategory2Id;
	}

	public void setCcjCategory2Id(Long ccjCategory2Id) {
		this.ccjCategory2Id = ccjCategory2Id;
	}

	public Long getCcjCategory3Id() {
		return ccjCategory3Id;
	}

	public void setCcjCategory3Id(Long ccjCategory3Id) {
		this.ccjCategory3Id = ccjCategory3Id;
	}
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getExcluderegex() {
		return excludeRegex;
	}

	public static HashMap<String, String> getPinyinmap() {
		return pinyinMap;
	}

	public static String getIdtypejyj() {
		return idTypeJYJ;
	}

	public static String getIdtypeccj() {
		return idTypeCCJ;
	}
	
	public Integer getJyjInvoiceTag() {
		return jyjInvoiceTag;
	}

	public void setJyjInvoiceTag(Integer jyjInvoiceTag) {
		this.jyjInvoiceTag = jyjInvoiceTag;
	}

	public Integer getJyjShowTag() {
		return jyjShowTag;
	}

	public void setJyjShowTag(Integer jyjShowTag) {
		this.jyjShowTag = jyjShowTag;
	}

	public Float getJyjTradePrice() {
		return jyjTradePrice;
	}

	public void setJyjTradePrice(Float jyjTradePrice) {
		this.jyjTradePrice = jyjTradePrice;
	}

	public Integer getJyjGoodsType() {
		return jyjGoodsType;
	}

	public void setJyjGoodsType(Integer jyjGoodsType) {
		this.jyjGoodsType = jyjGoodsType;
	}

	public Float getJyjSettlePrice() {
		return jyjSettlePrice;
	}

	public void setJyjSettlePrice(Float jyjSettlePrice) {
		this.jyjSettlePrice = jyjSettlePrice;
	}

	public String getIsGoodsQuantityStockId() {
		return isGoodsQuantityStockId;
	}

	public void setIsGoodsQuantityStockId(String isGoodsQuantityStockId) {
		this.isGoodsQuantityStockId = isGoodsQuantityStockId;
	}

	public String getJyjGoodsNameInfo() {
		return jyjGoodsNameInfo;
	}

	public void setJyjGoodsNameInfo(String jyjGoodsNameInfo) {
		this.jyjGoodsNameInfo = jyjGoodsNameInfo;
	}

	public Float getJyjShoppingPrice() {
		return jyjShoppingPrice;
	}

	public void setJyjShoppingPrice(Float jyjShoppingPrice) {
		this.jyjShoppingPrice = jyjShoppingPrice;
	}


	public String getGoodsSource() {
		return goodsSource;
	}

	public void setGoodsSource(String goodsSource) {
		this.goodsSource = goodsSource;
	}

	public String getJyjApplicableModel() {
    	return jyjApplicableModel;
    }
    
    public void setJyjApplicableModel(String jyjApplicableModel) {
    	this.jyjApplicableModel = jyjApplicableModel;
    }
    
    public String getCarYear() {
        return carYear;
    }

	public String getJyjGeneral() {
		return jyjGeneral;
	}

	public void setJyjGeneral(String jyjGeneral) {
		this.jyjGeneral = jyjGeneral;
	}

	public String getJyjAreaId() {
		return jyjAreaId;
	}

	public void setJyjAreaId(String jyjAreaId) {
		this.jyjAreaId = jyjAreaId;
	}

	public String getJyjAreaName() {
		return jyjAreaName;
	}

	public void setJyjAreaName(String jyjAreaName) {
		this.jyjAreaName = jyjAreaName;
	}

	public String getJyjProductType() {
		return jyjProductType;
	}

	public void setJyjProductType(String jyjProductType) {
		this.jyjProductType = jyjProductType;
	}

	public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(String carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


    public long getFlagshipStoreId() {
        return flagshipStoreId;
    }

    public void setFlagshipStoreId(long flagshipStoreId) {
        this.flagshipStoreId = flagshipStoreId;
    }

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

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getCategory1Id() {
		return category1Id;
	}

	public void setCategory1Id(Long category1Id) {
		this.category1Id = category1Id;
	}

	public Long getCategory2Id() {
		return category2Id;
	}

	public void setCategory2Id(Long category2Id) {
		this.category2Id = category2Id;
	}

	public Long getCategory3Id() {
		return category3Id;
	}

	public void setCategory3Id(Long category3Id) {
		this.category3Id = category3Id;
	}

	public Long getCategory4Id() {
		return category4Id;
	}

	public void setCategory4Id(Long category4Id) {
		this.category4Id = category4Id;
	}

	public Long getCategory5Id() {
		return category5Id;
	}

	public void setCategory5Id(Long category5Id) {
		this.category5Id = category5Id;
	}

	public String getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}

	public String getCarSeriesId() {
		return carSeriesId;
	}

	public void setCarSeriesId(String carSeriesId) {
		this.carSeriesId = carSeriesId;
	}

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategoryProp0() {
        return categoryProp0;
    }

    public void setCategoryProp0(String categoryProp0) {
        this.categoryProp0 = categoryProp0;
    }

    public String getCategoryProp1() {
        return categoryProp1;
    }

    public void setCategoryProp1(String categoryProp1) {
        this.categoryProp1 = categoryProp1;
    }

    public String getCategoryProp2() {
        return categoryProp2;
    }

    public void setCategoryProp2(String categoryProp2) {
        this.categoryProp2 = categoryProp2;
    }

    public String getCategoryProp3() {
        return categoryProp3;
    }

    public void setCategoryProp3(String categoryProp3) {
        this.categoryProp3 = categoryProp3;
    }

    public String getCategoryProp4() {
        return categoryProp4;
    }

    public void setCategoryProp4(String categoryProp4) {
        this.categoryProp4 = categoryProp4;
    }

    public String getCategoryProp5() {
        return categoryProp5;
    }

    public void setCategoryProp5(String categoryProp5) {
        this.categoryProp5 = categoryProp5;
    }

    public String getCategoryProp6() {
        return categoryProp6;
    }

    public void setCategoryProp6(String categoryProp6) {
        this.categoryProp6 = categoryProp6;
    }

    public String getCategoryProp7() {
        return categoryProp7;
    }

    public void setCategoryProp7(String categoryProp7) {
        this.categoryProp7 = categoryProp7;
    }

    public String getCategoryProp8() {
        return categoryProp8;
    }

    public void setCategoryProp8(String categoryProp8) {
        this.categoryProp8 = categoryProp8;
    }

    public String getCategoryProp9() {
        return categoryProp9;
    }

    public void setCategoryProp9(String categoryProp9) {
        this.categoryProp9 = categoryProp9;
    }

    public int getPriceLevel() {
		return priceLevel;
	}

	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}

	public String getCarBrandAndSeries() {
		return carBrandAndSeries;
	}

	public void setCarBrandAndSeries(String carBrandAndSeries) {
		this.carBrandAndSeries = carBrandAndSeries;
	}

	public String getCarLogoId() {
		return carLogoId;
	}

	public void setCarLogoId(String carLogoId) {
		this.carLogoId = carLogoId;
	}

//	public String getWareHouseId() {
//		return wareHouseId;
//	}
//
//	public void setWareHouseId(String wareHouseId) {
//		this.wareHouseId = wareHouseId;
//	}


	public String getOemAlias() {
		return oemAlias;
	}

	public void setOemAlias(String oemAlias) {
		this.oemAlias = oemAlias;
	}

	public void setWearingCategory1Id(Long wearingCategory1Id) {
		this.wearingCategory1Id = wearingCategory1Id;
	}

	public Long getWearingCategory1Id() {
		return wearingCategory1Id;
	}

	public void setWearingCategory2Id(Long wearingCategory2Id) {
		this.wearingCategory2Id = wearingCategory2Id;
	}

	public Long getWearingCategory2Id() {
		return wearingCategory2Id;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getHasImages() {
		return hasImages;
	}

	public void setHasImages(int hasImages) {
		this.hasImages = hasImages;
	}

	public int getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public String getGoodsBrandNameAndGoodsName() {
		return goodsBrandNameAndGoodsName;
	}

	public void setGoodsBrandNameAndGoodsName(String goodsBrandNameAndGoodsName) {
		this.goodsBrandNameAndGoodsName = goodsBrandNameAndGoodsName;
	}

	public String getGoodsBrandNameAndCategory2Name() {
		return goodsBrandNameAndCategory2Name;
	}

	public void setGoodsBrandNameAndCategory2Name(
			String goodsBrandNameAndCategory2Name) {
		this.goodsBrandNameAndCategory2Name = goodsBrandNameAndCategory2Name;
	}

	public String getGoodsStyle() {
		return goodsStyle;
	}

	public void setGoodsStyle(String goodsStyle) {
		this.goodsStyle = goodsStyle;
	}

	public String getGoodsName_goodsStyle() {
		return goodsName_goodsStyle;
	}

	public void setGoodsName_goodsStyle(String goodsName_goodsStyle) {
		this.goodsName_goodsStyle = goodsName_goodsStyle;
	}

	public String getGoodsName_goodsStyle_serialNumber() {
		return goodsName_goodsStyle_serialNumber;
	}

	public void setGoodsName_goodsStyle_serialNumber(
			String goodsName_goodsStyle_serialNumber) {
		this.goodsName_goodsStyle_serialNumber = goodsName_goodsStyle_serialNumber;
	}

	public String getGoodsBrandNameAndCategory2Name_bs() {
		return goodsBrandNameAndCategory2Name_bs;
	}

	public void setGoodsBrandNameAndCategory2Name_bs(
			String goodsBrandNameAndCategory2Name_bs) {
		this.goodsBrandNameAndCategory2Name_bs = goodsBrandNameAndCategory2Name_bs;
	}

	public String getGoodsBrandNameAndGoodsName_bs() {
		return goodsBrandNameAndGoodsName_bs;
	}

	public void setGoodsBrandNameAndGoodsName_bs(
			String goodsBrandNameAndGoodsName_bs) {
		this.goodsBrandNameAndGoodsName_bs = goodsBrandNameAndGoodsName_bs;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public String getSmallImagePath() {
		return smallImagePath;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}

	public String getMiddleImagePath() {
		return middleImagePath;
	}

	public void setMiddleImagePath(String middleImagePath) {
		this.middleImagePath = middleImagePath;
	}

	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	public String getMemberPrice1() {
		return memberPrice1;
	}

	public void setMemberPrice1(String memberPrice1) {
		this.memberPrice1 = memberPrice1;
	}

	public String getMemberPrice2() {
		return memberPrice2;
	}

	public void setMemberPrice2(String memberPrice2) {
		this.memberPrice2 = memberPrice2;
	}

	public String getMemberPrice3() {
		return memberPrice3;
	}

	public void setMemberPrice3(String memberPrice3) {
		this.memberPrice3 = memberPrice3;
	}

	public String getMemberPrice4() {
		return memberPrice4;
	}

	public void setMemberPrice4(String memberPrice4) {
		this.memberPrice4 = memberPrice4;
	}

	public String getMemberPrice5() {
		return memberPrice5;
	}

	public void setMemberPrice5(String memberPrice5) {
		this.memberPrice5 = memberPrice5;
	}

	public String getMemberPrice6() {
		return memberPrice6;
	}

	public void setMemberPrice6(String memberPrice6) {
		this.memberPrice6 = memberPrice6;
	}

	public String getMemberPrice7() {
		return memberPrice7;
	}

	public void setMemberPrice7(String memberPrice7) {
		this.memberPrice7 = memberPrice7;
	}

	public String getMemberPrice8() {
		return memberPrice8;
	}

	public void setMemberPrice8(String memberPrice8) {
		this.memberPrice8 = memberPrice8;
	}

	public String getMemberPrice9() {
		return memberPrice9;
	}

	public void setMemberPrice9(String memberPrice9) {
		this.memberPrice9 = memberPrice9;
	}

	public String getMemberPrice10() {
		return memberPrice10;
	}

	public void setMemberPrice10(String memberPrice10) {
		this.memberPrice10 = memberPrice10;
	}

	public String getMemberPrice11() {
		return memberPrice11;
	}

	public void setMemberPrice11(String memberPrice11) {
		this.memberPrice11 = memberPrice11;
	}

	public String getMemberPrice12() {
		return memberPrice12;
	}

	public void setMemberPrice12(String memberPrice12) {
		this.memberPrice12 = memberPrice12;
	}

	public String getMemberPrice13() {
		return memberPrice13;
	}

	public void setMemberPrice13(String memberPrice13) {
		this.memberPrice13 = memberPrice13;
	}

	public String getMemberPrice14() {
		return memberPrice14;
	}

	public void setMemberPrice14(String memberPrice14) {
		this.memberPrice14 = memberPrice14;
	}

	public String getMemberPrice15() {
		return memberPrice15;
	}

	public void setMemberPrice15(String memberPrice15) {
		this.memberPrice15 = memberPrice15;
	}

	public String getMemberPrice16() {
		return memberPrice16;
	}

	public void setMemberPrice16(String memberPrice16) {
		this.memberPrice16 = memberPrice16;
	}

	public String getMemberPrice17() {
		return memberPrice17;
	}

	public void setMemberPrice17(String memberPrice17) {
		this.memberPrice17 = memberPrice17;
	}

	public String getMemberPrice18() {
		return memberPrice18;
	}

	public void setMemberPrice18(String memberPrice18) {
		this.memberPrice18 = memberPrice18;
	}

	public String getMemberPrice19() {
		return memberPrice19;
	}

	public void setMemberPrice19(String memberPrice19) {
		this.memberPrice19 = memberPrice19;
	}

	public String getMemberPrice20() {
		return memberPrice20;
	}

	public void setMemberPrice20(String memberPrice20) {
		this.memberPrice20 = memberPrice20;
	}

	public String getMemberPrice21() {
		return memberPrice21;
	}

	public void setMemberPrice21(String memberPrice21) {
		this.memberPrice21 = memberPrice21;
	}

	public String getMemberPrice22() {
		return memberPrice22;
	}

	public void setMemberPrice22(String memberPrice22) {
		this.memberPrice22 = memberPrice22;
	}

	public String getMemberPrice23() {
		return memberPrice23;
	}

	public void setMemberPrice23(String memberPrice23) {
		this.memberPrice23 = memberPrice23;
	}

	public String getMemberPrice24() {
		return memberPrice24;
	}

	public void setMemberPrice24(String memberPrice24) {
		this.memberPrice24 = memberPrice24;
	}

	public String getMemberPrice25() {
		return memberPrice25;
	}

	public void setMemberPrice25(String memberPrice25) {
		this.memberPrice25 = memberPrice25;
	}

	public String getMemberPrice26() {
		return memberPrice26;
	}

	public void setMemberPrice26(String memberPrice26) {
		this.memberPrice26 = memberPrice26;
	}

	public String getMemberPrice27() {
		return memberPrice27;
	}

	public void setMemberPrice27(String memberPrice27) {
		this.memberPrice27 = memberPrice27;
	}

	public String getMemberPrice28() {
		return memberPrice28;
	}

	public void setMemberPrice28(String memberPrice28) {
		this.memberPrice28 = memberPrice28;
	}

	public String getMemberPrice29() {
		return memberPrice29;
	}

	public void setMemberPrice29(String memberPrice29) {
		this.memberPrice29 = memberPrice29;
	}

	public String getMemberPrice30() {
		return memberPrice30;
	}

	public void setMemberPrice30(String memberPrice30) {
		this.memberPrice30 = memberPrice30;
	}

	public String getMemberPrice31() {
		return memberPrice31;
	}

	public void setMemberPrice31(String memberPrice31) {
		this.memberPrice31 = memberPrice31;
	}

	public String getMemberPrice32() {
		return memberPrice32;
	}

	public void setMemberPrice32(String memberPrice32) {
		this.memberPrice32 = memberPrice32;
	}

	public String getMemberPrice33() {
		return memberPrice33;
	}

	public void setMemberPrice33(String memberPrice33) {
		this.memberPrice33 = memberPrice33;
	}

	public String getMemberPrice34() {
		return memberPrice34;
	}

	public void setMemberPrice34(String memberPrice34) {
		this.memberPrice34 = memberPrice34;
	}

	public String getMemberPrice35() {
		return memberPrice35;
	}

	public void setMemberPrice35(String memberPrice35) {
		this.memberPrice35 = memberPrice35;
	}

	public String getMemberPrice36() {
		return memberPrice36;
	}

	public void setMemberPrice36(String memberPrice36) {
		this.memberPrice36 = memberPrice36;
	}

	public String getMemberPrice37() {
		return memberPrice37;
	}

	public void setMemberPrice37(String memberPrice37) {
		this.memberPrice37 = memberPrice37;
	}

	public String getMemberPrice38() {
		return memberPrice38;
	}

	public void setMemberPrice38(String memberPrice38) {
		this.memberPrice38 = memberPrice38;
	}

	public String getMemberPrice39() {
		return memberPrice39;
	}

	public void setMemberPrice39(String memberPrice39) {
		this.memberPrice39 = memberPrice39;
	}

	public String getMemberPrice40() {
		return memberPrice40;
	}

	public void setMemberPrice40(String memberPrice40) {
		this.memberPrice40 = memberPrice40;
	}

	public String getMemberPrice41() {
		return memberPrice41;
	}

	public void setMemberPrice41(String memberPrice41) {
		this.memberPrice41 = memberPrice41;
	}

	public String getMemberPrice42() {
		return memberPrice42;
	}

	public void setMemberPrice42(String memberPrice42) {
		this.memberPrice42 = memberPrice42;
	}

	public String getMemberPrice43() {
		return memberPrice43;
	}

	public void setMemberPrice43(String memberPrice43) {
		this.memberPrice43 = memberPrice43;
	}

	public String getMemberPrice44() {
		return memberPrice44;
	}

	public void setMemberPrice44(String memberPrice44) {
		this.memberPrice44 = memberPrice44;
	}

	public String getMemberPrice45() {
		return memberPrice45;
	}

	public void setMemberPrice45(String memberPrice45) {
		this.memberPrice45 = memberPrice45;
	}

	public String getMemberPrice46() {
		return memberPrice46;
	}

	public void setMemberPrice46(String memberPrice46) {
		this.memberPrice46 = memberPrice46;
	}

	public String getMemberPrice47() {
		return memberPrice47;
	}

	public void setMemberPrice47(String memberPrice47) {
		this.memberPrice47 = memberPrice47;
	}

	public String getMemberPrice48() {
		return memberPrice48;
	}

	public void setMemberPrice48(String memberPrice48) {
		this.memberPrice48 = memberPrice48;
	}

	public String getMemberPrice49() {
		return memberPrice49;
	}

	public void setMemberPrice49(String memberPrice49) {
		this.memberPrice49 = memberPrice49;
	}

	public String getMemberPrice50() {
		return memberPrice50;
	}

	public void setMemberPrice50(String memberPrice50) {
		this.memberPrice50 = memberPrice50;
	}

	public String getGoodsRemark() {
		return goodsRemark;
	}

	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}

	public String getGoodsStyleQuery() {
		return goodsStyleQuery;
	}

	public void setGoodsStyleQuery(String goodsStyleQuery) {
		this.goodsStyleQuery = goodsStyleQuery;
	}

	public String getAreaShoppingStoreScope() {
		return areaShoppingStoreScope;
	}

	public void setAreaShoppingStoreScope(String areaShoppingStoreScope) {
		this.areaShoppingStoreScope = areaShoppingStoreScope;
	}

	public String getGoodsNameDisplay() {
		return goodsNameDisplay;
	}

	public void setGoodsNameDisplay(String goodsNameDisplay) {
		this.goodsNameDisplay = goodsNameDisplay;
	}

	public String getScopeMemberPrice1() {
		return scopeMemberPrice1;
	}

	public void setScopeMemberPrice1(String scopeMemberPrice1) {
		this.scopeMemberPrice1 = scopeMemberPrice1;
	}

	public String getScopeMemberPrice2() {
		return scopeMemberPrice2;
	}

	public void setScopeMemberPrice2(String scopeMemberPrice2) {
		this.scopeMemberPrice2 = scopeMemberPrice2;
	}

	public String getScopeMemberPrice3() {
		return scopeMemberPrice3;
	}

	public void setScopeMemberPrice3(String scopeMemberPrice3) {
		this.scopeMemberPrice3 = scopeMemberPrice3;
	}

	public String getScopeMemberPrice4() {
		return scopeMemberPrice4;
	}

	public void setScopeMemberPrice4(String scopeMemberPrice4) {
		this.scopeMemberPrice4 = scopeMemberPrice4;
	}

	public String getScopeMemberPrice5() {
		return scopeMemberPrice5;
	}

	public void setScopeMemberPrice5(String scopeMemberPrice5) {
		this.scopeMemberPrice5 = scopeMemberPrice5;
	}

	public String getScopeMemberPrice6() {
		return scopeMemberPrice6;
	}

	public void setScopeMemberPrice6(String scopeMemberPrice6) {
		this.scopeMemberPrice6 = scopeMemberPrice6;
	}

	public String getScopeMemberPrice7() {
		return scopeMemberPrice7;
	}

	public void setScopeMemberPrice7(String scopeMemberPrice7) {
		this.scopeMemberPrice7 = scopeMemberPrice7;
	}

	public String getScopeMemberPrice8() {
		return scopeMemberPrice8;
	}

	public void setScopeMemberPrice8(String scopeMemberPrice8) {
		this.scopeMemberPrice8 = scopeMemberPrice8;
	}

	public String getScopeMemberPrice9() {
		return scopeMemberPrice9;
	}

	public void setScopeMemberPrice9(String scopeMemberPrice9) {
		this.scopeMemberPrice9 = scopeMemberPrice9;
	}

	public String getScopeMemberPrice10() {
		return scopeMemberPrice10;
	}

	public void setScopeMemberPrice10(String scopeMemberPrice10) {
		this.scopeMemberPrice10 = scopeMemberPrice10;
	}

	public String getScopeMemberPrice11() {
		return scopeMemberPrice11;
	}

	public void setScopeMemberPrice11(String scopeMemberPrice11) {
		this.scopeMemberPrice11 = scopeMemberPrice11;
	}

	public String getScopeMemberPrice12() {
		return scopeMemberPrice12;
	}

	public void setScopeMemberPrice12(String scopeMemberPrice12) {
		this.scopeMemberPrice12 = scopeMemberPrice12;
	}

	public String getScopeMemberPrice13() {
		return scopeMemberPrice13;
	}

	public void setScopeMemberPrice13(String scopeMemberPrice13) {
		this.scopeMemberPrice13 = scopeMemberPrice13;
	}

	public String getScopeMemberPrice14() {
		return scopeMemberPrice14;
	}

	public void setScopeMemberPrice14(String scopeMemberPrice14) {
		this.scopeMemberPrice14 = scopeMemberPrice14;
	}

	public String getScopeMemberPrice15() {
		return scopeMemberPrice15;
	}

	public void setScopeMemberPrice15(String scopeMemberPrice15) {
		this.scopeMemberPrice15 = scopeMemberPrice15;
	}

	public String getScopeMemberPrice16() {
		return scopeMemberPrice16;
	}

	public void setScopeMemberPrice16(String scopeMemberPrice16) {
		this.scopeMemberPrice16 = scopeMemberPrice16;
	}

	public String getScopeMemberPrice17() {
		return scopeMemberPrice17;
	}

	public void setScopeMemberPrice17(String scopeMemberPrice17) {
		this.scopeMemberPrice17 = scopeMemberPrice17;
	}

	public String getScopeMemberPrice18() {
		return scopeMemberPrice18;
	}

	public void setScopeMemberPrice18(String scopeMemberPrice18) {
		this.scopeMemberPrice18 = scopeMemberPrice18;
	}

	public String getScopeMemberPrice19() {
		return scopeMemberPrice19;
	}

	public void setScopeMemberPrice19(String scopeMemberPrice19) {
		this.scopeMemberPrice19 = scopeMemberPrice19;
	}

	public String getScopeMemberPrice20() {
		return scopeMemberPrice20;
	}

	public void setScopeMemberPrice20(String scopeMemberPrice20) {
		this.scopeMemberPrice20 = scopeMemberPrice20;
	}

	public String getScopeMemberPrice21() {
		return scopeMemberPrice21;
	}

	public void setScopeMemberPrice21(String scopeMemberPrice21) {
		this.scopeMemberPrice21 = scopeMemberPrice21;
	}

	public String getScopeMemberPrice22() {
		return scopeMemberPrice22;
	}

	public void setScopeMemberPrice22(String scopeMemberPrice22) {
		this.scopeMemberPrice22 = scopeMemberPrice22;
	}

	public String getScopeMemberPrice23() {
		return scopeMemberPrice23;
	}

	public void setScopeMemberPrice23(String scopeMemberPrice23) {
		this.scopeMemberPrice23 = scopeMemberPrice23;
	}

	public String getScopeMemberPrice24() {
		return scopeMemberPrice24;
	}

	public void setScopeMemberPrice24(String scopeMemberPrice24) {
		this.scopeMemberPrice24 = scopeMemberPrice24;
	}

	public String getScopeMemberPrice25() {
		return scopeMemberPrice25;
	}

	public void setScopeMemberPrice25(String scopeMemberPrice25) {
		this.scopeMemberPrice25 = scopeMemberPrice25;
	}

	public String getScopeMemberPrice26() {
		return scopeMemberPrice26;
	}

	public void setScopeMemberPrice26(String scopeMemberPrice26) {
		this.scopeMemberPrice26 = scopeMemberPrice26;
	}

	public String getScopeMemberPrice27() {
		return scopeMemberPrice27;
	}

	public void setScopeMemberPrice27(String scopeMemberPrice27) {
		this.scopeMemberPrice27 = scopeMemberPrice27;
	}

	public String getScopeMemberPrice28() {
		return scopeMemberPrice28;
	}

	public void setScopeMemberPrice28(String scopeMemberPrice28) {
		this.scopeMemberPrice28 = scopeMemberPrice28;
	}

	public String getScopeMemberPrice29() {
		return scopeMemberPrice29;
	}

	public void setScopeMemberPrice29(String scopeMemberPrice29) {
		this.scopeMemberPrice29 = scopeMemberPrice29;
	}

	public String getScopeMemberPrice30() {
		return scopeMemberPrice30;
	}

	public void setScopeMemberPrice30(String scopeMemberPrice30) {
		this.scopeMemberPrice30 = scopeMemberPrice30;
	}

	public String getScopeMemberPrice31() {
		return scopeMemberPrice31;
	}

	public void setScopeMemberPrice31(String scopeMemberPrice31) {
		this.scopeMemberPrice31 = scopeMemberPrice31;
	}

	public String getScopeMemberPrice32() {
		return scopeMemberPrice32;
	}

	public void setScopeMemberPrice32(String scopeMemberPrice32) {
		this.scopeMemberPrice32 = scopeMemberPrice32;
	}

	public String getScopeMemberPrice33() {
		return scopeMemberPrice33;
	}

	public void setScopeMemberPrice33(String scopeMemberPrice33) {
		this.scopeMemberPrice33 = scopeMemberPrice33;
	}

	public String getScopeMemberPrice34() {
		return scopeMemberPrice34;
	}

	public void setScopeMemberPrice34(String scopeMemberPrice34) {
		this.scopeMemberPrice34 = scopeMemberPrice34;
	}

	public String getScopeMemberPrice35() {
		return scopeMemberPrice35;
	}

	public void setScopeMemberPrice35(String scopeMemberPrice35) {
		this.scopeMemberPrice35 = scopeMemberPrice35;
	}

	public String getScopeMemberPrice36() {
		return scopeMemberPrice36;
	}

	public void setScopeMemberPrice36(String scopeMemberPrice36) {
		this.scopeMemberPrice36 = scopeMemberPrice36;
	}

	public String getScopeMemberPrice37() {
		return scopeMemberPrice37;
	}

	public void setScopeMemberPrice37(String scopeMemberPrice37) {
		this.scopeMemberPrice37 = scopeMemberPrice37;
	}

	public String getScopeMemberPrice38() {
		return scopeMemberPrice38;
	}

	public void setScopeMemberPrice38(String scopeMemberPrice38) {
		this.scopeMemberPrice38 = scopeMemberPrice38;
	}

	public String getScopeMemberPrice39() {
		return scopeMemberPrice39;
	}

	public void setScopeMemberPrice39(String scopeMemberPrice39) {
		this.scopeMemberPrice39 = scopeMemberPrice39;
	}

	public String getScopeMemberPrice40() {
		return scopeMemberPrice40;
	}

	public void setScopeMemberPrice40(String scopeMemberPrice40) {
		this.scopeMemberPrice40 = scopeMemberPrice40;
	}

	public String getScopeMemberPrice41() {
		return scopeMemberPrice41;
	}

	public void setScopeMemberPrice41(String scopeMemberPrice41) {
		this.scopeMemberPrice41 = scopeMemberPrice41;
	}

	public String getScopeMemberPrice42() {
		return scopeMemberPrice42;
	}

	public void setScopeMemberPrice42(String scopeMemberPrice42) {
		this.scopeMemberPrice42 = scopeMemberPrice42;
	}

	public String getScopeMemberPrice43() {
		return scopeMemberPrice43;
	}

	public void setScopeMemberPrice43(String scopeMemberPrice43) {
		this.scopeMemberPrice43 = scopeMemberPrice43;
	}

	public String getScopeMemberPrice44() {
		return scopeMemberPrice44;
	}

	public void setScopeMemberPrice44(String scopeMemberPrice44) {
		this.scopeMemberPrice44 = scopeMemberPrice44;
	}

	public String getScopeMemberPrice45() {
		return scopeMemberPrice45;
	}

	public void setScopeMemberPrice45(String scopeMemberPrice45) {
		this.scopeMemberPrice45 = scopeMemberPrice45;
	}

	public String getScopeMemberPrice46() {
		return scopeMemberPrice46;
	}

	public void setScopeMemberPrice46(String scopeMemberPrice46) {
		this.scopeMemberPrice46 = scopeMemberPrice46;
	}

	public String getScopeMemberPrice47() {
		return scopeMemberPrice47;
	}

	public void setScopeMemberPrice47(String scopeMemberPrice47) {
		this.scopeMemberPrice47 = scopeMemberPrice47;
	}

	public String getScopeMemberPrice48() {
		return scopeMemberPrice48;
	}

	public void setScopeMemberPrice48(String scopeMemberPrice48) {
		this.scopeMemberPrice48 = scopeMemberPrice48;
	}

	public String getScopeMemberPrice49() {
		return scopeMemberPrice49;
	}

	public void setScopeMemberPrice49(String scopeMemberPrice49) {
		this.scopeMemberPrice49 = scopeMemberPrice49;
	}

	public String getScopeMemberPrice50() {
		return scopeMemberPrice50;
	}

	public void setScopeMemberPrice50(String scopeMemberPrice50) {
		this.scopeMemberPrice50 = scopeMemberPrice50;
	}

	public String getMemberPrice51() {
		return memberPrice51;
	}

	public void setMemberPrice51(String memberPrice51) {
		this.memberPrice51 = memberPrice51;
	}

	public String getMemberPrice52() {
		return memberPrice52;
	}

	public void setMemberPrice52(String memberPrice52) {
		this.memberPrice52 = memberPrice52;
	}

	public String getMemberPrice53() {
		return memberPrice53;
	}

	public void setMemberPrice53(String memberPrice53) {
		this.memberPrice53 = memberPrice53;
	}

	public String getScopeMemberPrice51() {
		return scopeMemberPrice51;
	}

	public void setScopeMemberPrice51(String scopeMemberPrice51) {
		this.scopeMemberPrice51 = scopeMemberPrice51;
	}

	public String getScopeMemberPrice52() {
		return scopeMemberPrice52;
	}

	public void setScopeMemberPrice52(String scopeMemberPrice52) {
		this.scopeMemberPrice52 = scopeMemberPrice52;
	}

	public String getScopeMemberPrice53() {
		return scopeMemberPrice53;
	}

	public void setScopeMemberPrice53(String scopeMemberPrice53) {
		this.scopeMemberPrice53 = scopeMemberPrice53;
	}

	public String getMemberPrice54() {
		return memberPrice54;
	}

	public void setMemberPrice54(String memberPrice54) {
		this.memberPrice54 = memberPrice54;
	}

	public String getMemberPrice55() {
		return memberPrice55;
	}

	public void setMemberPrice55(String memberPrice55) {
		this.memberPrice55 = memberPrice55;
	}

	public String getMemberPrice56() {
		return memberPrice56;
	}

	public void setMemberPrice56(String memberPrice56) {
		this.memberPrice56 = memberPrice56;
	}

	public String getScopeMemberPrice54() {
		return scopeMemberPrice54;
	}

	public void setScopeMemberPrice54(String scopeMemberPrice54) {
		this.scopeMemberPrice54 = scopeMemberPrice54;
	}

	public String getScopeMemberPrice55() {
		return scopeMemberPrice55;
	}

	public void setScopeMemberPrice55(String scopeMemberPrice55) {
		this.scopeMemberPrice55 = scopeMemberPrice55;
	}

	public String getScopeMemberPrice56() {
		return scopeMemberPrice56;
	}

	public void setScopeMemberPrice56(String scopeMemberPrice56) {
		this.scopeMemberPrice56 = scopeMemberPrice56;
	}

	public String getMemberPrice57() {
		return memberPrice57;
	}

	public void setMemberPrice57(String memberPrice57) {
		this.memberPrice57 = memberPrice57;
	}

	public String getMemberPrice58() {
		return memberPrice58;
	}

	public void setMemberPrice58(String memberPrice58) {
		this.memberPrice58 = memberPrice58;
	}

	public String getMemberPrice59() {
		return memberPrice59;
	}

	public void setMemberPrice59(String memberPrice59) {
		this.memberPrice59 = memberPrice59;
	}

	public String getMemberPrice60() {
		return memberPrice60;
	}

	public void setMemberPrice60(String memberPrice60) {
		this.memberPrice60 = memberPrice60;
	}

	public String getMemberPrice61() {
		return memberPrice61;
	}

	public void setMemberPrice61(String memberPrice61) {
		this.memberPrice61 = memberPrice61;
	}

	public String getMemberPrice62() {
		return memberPrice62;
	}

	public void setMemberPrice62(String memberPrice62) {
		this.memberPrice62 = memberPrice62;
	}

	public String getScopeMemberPrice57() {
		return scopeMemberPrice57;
	}

	public void setScopeMemberPrice57(String scopeMemberPrice57) {
		this.scopeMemberPrice57 = scopeMemberPrice57;
	}

	public String getScopeMemberPrice58() {
		return scopeMemberPrice58;
	}

	public void setScopeMemberPrice58(String scopeMemberPrice58) {
		this.scopeMemberPrice58 = scopeMemberPrice58;
	}

	public String getScopeMemberPrice59() {
		return scopeMemberPrice59;
	}

	public void setScopeMemberPrice59(String scopeMemberPrice59) {
		this.scopeMemberPrice59 = scopeMemberPrice59;
	}

	public String getScopeMemberPrice60() {
		return scopeMemberPrice60;
	}

	public void setScopeMemberPrice60(String scopeMemberPrice60) {
		this.scopeMemberPrice60 = scopeMemberPrice60;
	}

	public String getScopeMemberPrice61() {
		return scopeMemberPrice61;
	}

	public void setScopeMemberPrice61(String scopeMemberPrice61) {
		this.scopeMemberPrice61 = scopeMemberPrice61;
	}

	public String getScopeMemberPrice62() {
		return scopeMemberPrice62;
	}

	public void setScopeMemberPrice62(String scopeMemberPrice62) {
		this.scopeMemberPrice62 = scopeMemberPrice62;
	}

	public String getMemberPrice63() {
		return memberPrice63;
	}

	public void setMemberPrice63(String memberPrice63) {
		this.memberPrice63 = memberPrice63;
	}

	public String getMemberPrice64() {
		return memberPrice64;
	}

	public void setMemberPrice64(String memberPrice64) {
		this.memberPrice64 = memberPrice64;
	}

	public String getMemberPrice65() {
		return memberPrice65;
	}

	public void setMemberPrice65(String memberPrice65) {
		this.memberPrice65 = memberPrice65;
	}

	public String getMemberPrice66() {
		return memberPrice66;
	}

	public void setMemberPrice66(String memberPrice66) {
		this.memberPrice66 = memberPrice66;
	}

	public String getMemberPrice67() {
		return memberPrice67;
	}

	public void setMemberPrice67(String memberPrice67) {
		this.memberPrice67 = memberPrice67;
	}

	public String getMemberPrice68() {
		return memberPrice68;
	}

	public void setMemberPrice68(String memberPrice68) {
		this.memberPrice68 = memberPrice68;
	}

	public String getMemberPrice69() {
		return memberPrice69;
	}

	public void setMemberPrice69(String memberPrice69) {
		this.memberPrice69 = memberPrice69;
	}

	public String getMemberPrice70() {
		return memberPrice70;
	}

	public void setMemberPrice70(String memberPrice70) {
		this.memberPrice70 = memberPrice70;
	}

	public String getMemberPrice71() {
		return memberPrice71;
	}

	public void setMemberPrice71(String memberPrice71) {
		this.memberPrice71 = memberPrice71;
	}

	public String getMemberPrice72() {
		return memberPrice72;
	}

	public void setMemberPrice72(String memberPrice72) {
		this.memberPrice72 = memberPrice72;
	}

	public String getMemberPrice73() {
		return memberPrice73;
	}

	public void setMemberPrice73(String memberPrice73) {
		this.memberPrice73 = memberPrice73;
	}

	public String getMemberPrice74() {
		return memberPrice74;
	}

	public void setMemberPrice74(String memberPrice74) {
		this.memberPrice74 = memberPrice74;
	}

	public String getScopeMemberPrice63() {
		return scopeMemberPrice63;
	}

	public void setScopeMemberPrice63(String scopeMemberPrice63) {
		this.scopeMemberPrice63 = scopeMemberPrice63;
	}

	public String getScopeMemberPrice64() {
		return scopeMemberPrice64;
	}

	public void setScopeMemberPrice64(String scopeMemberPrice64) {
		this.scopeMemberPrice64 = scopeMemberPrice64;
	}

	public String getScopeMemberPrice65() {
		return scopeMemberPrice65;
	}

	public void setScopeMemberPrice65(String scopeMemberPrice65) {
		this.scopeMemberPrice65 = scopeMemberPrice65;
	}

	public String getScopeMemberPrice66() {
		return scopeMemberPrice66;
	}

	public void setScopeMemberPrice66(String scopeMemberPrice66) {
		this.scopeMemberPrice66 = scopeMemberPrice66;
	}

	public String getScopeMemberPrice67() {
		return scopeMemberPrice67;
	}

	public void setScopeMemberPrice67(String scopeMemberPrice67) {
		this.scopeMemberPrice67 = scopeMemberPrice67;
	}

	public String getScopeMemberPrice68() {
		return scopeMemberPrice68;
	}

	public void setScopeMemberPrice68(String scopeMemberPrice68) {
		this.scopeMemberPrice68 = scopeMemberPrice68;
	}

	public String getScopeMemberPrice69() {
		return scopeMemberPrice69;
	}

	public void setScopeMemberPrice69(String scopeMemberPrice69) {
		this.scopeMemberPrice69 = scopeMemberPrice69;
	}

	public String getScopeMemberPrice70() {
		return scopeMemberPrice70;
	}

	public void setScopeMemberPrice70(String scopeMemberPrice70) {
		this.scopeMemberPrice70 = scopeMemberPrice70;
	}

	public String getScopeMemberPrice71() {
		return scopeMemberPrice71;
	}

	public void setScopeMemberPrice71(String scopeMemberPrice71) {
		this.scopeMemberPrice71 = scopeMemberPrice71;
	}

	public String getScopeMemberPrice72() {
		return scopeMemberPrice72;
	}

	public void setScopeMemberPrice72(String scopeMemberPrice72) {
		this.scopeMemberPrice72 = scopeMemberPrice72;
	}

	public String getScopeMemberPrice73() {
		return scopeMemberPrice73;
	}

	public void setScopeMemberPrice73(String scopeMemberPrice73) {
		this.scopeMemberPrice73 = scopeMemberPrice73;
	}

	public String getScopeMemberPrice74() {
		return scopeMemberPrice74;
	}

	public void setScopeMemberPrice74(String scopeMemberPrice74) {
		this.scopeMemberPrice74 = scopeMemberPrice74;
	}

	public String getMemberPrice75() {
		return memberPrice75;
	}

	public void setMemberPrice75(String memberPrice75) {
		this.memberPrice75 = memberPrice75;
	}

	public String getMemberPrice76() {
		return memberPrice76;
	}

	public void setMemberPrice76(String memberPrice76) {
		this.memberPrice76 = memberPrice76;
	}

	public String getMemberPrice77() {
		return memberPrice77;
	}

	public void setMemberPrice77(String memberPrice77) {
		this.memberPrice77 = memberPrice77;
	}

	public String getScopeMemberPrice75() {
		return scopeMemberPrice75;
	}

	public void setScopeMemberPrice75(String scopeMemberPrice75) {
		this.scopeMemberPrice75 = scopeMemberPrice75;
	}

	public String getScopeMemberPrice76() {
		return scopeMemberPrice76;
	}

	public void setScopeMemberPrice76(String scopeMemberPrice76) {
		this.scopeMemberPrice76 = scopeMemberPrice76;
	}

	public String getScopeMemberPrice77() {
		return scopeMemberPrice77;
	}

	public void setScopeMemberPrice77(String scopeMemberPrice77) {
		this.scopeMemberPrice77 = scopeMemberPrice77;
	}

	public String getScopeMemberPrice78() {
		return scopeMemberPrice78;
	}

	public void setScopeMemberPrice78(String scopeMemberPrice78) {
		this.scopeMemberPrice78 = scopeMemberPrice78;
	}

	public String getScopeMemberPrice79() {
		return scopeMemberPrice79;
	}

	public void setScopeMemberPrice79(String scopeMemberPrice79) {
		this.scopeMemberPrice79 = scopeMemberPrice79;
	}

	public String getMemberPrice78() {
		return memberPrice78;
	}

	public void setMemberPrice78(String memberPrice78) {
		this.memberPrice78 = memberPrice78;
	}

	public String getMemberPrice79() {
		return memberPrice79;
	}

	public void setMemberPrice79(String memberPrice79) {
		this.memberPrice79 = memberPrice79;
	}

	public String getMemberPrice80() {
		return memberPrice80;
	}

	public void setMemberPrice80(String memberPrice80) {
		this.memberPrice80 = memberPrice80;
	}

	public String getScopeMemberPrice80() {
		return scopeMemberPrice80;
	}

	public void setScopeMemberPrice80(String scopeMemberPrice80) {
		this.scopeMemberPrice80 = scopeMemberPrice80;
	}

	public String getMemberPrice81() {
		return memberPrice81;
	}

	public void setMemberPrice81(String memberPrice81) {
		this.memberPrice81 = memberPrice81;
	}

	public String getMemberPrice82() {
		return memberPrice82;
	}

	public void setMemberPrice82(String memberPrice82) {
		this.memberPrice82 = memberPrice82;
	}

	public String getMemberPrice83() {
		return memberPrice83;
	}

	public void setMemberPrice83(String memberPrice83) {
		this.memberPrice83 = memberPrice83;
	}

	public String getScopeMemberPrice81() {
		return scopeMemberPrice81;
	}

	public void setScopeMemberPrice81(String scopeMemberPrice81) {
		this.scopeMemberPrice81 = scopeMemberPrice81;
	}

	public String getScopeMemberPrice82() {
		return scopeMemberPrice82;
	}

	public void setScopeMemberPrice82(String scopeMemberPrice82) {
		this.scopeMemberPrice82 = scopeMemberPrice82;
	}

	public String getScopeMemberPrice83() {
		return scopeMemberPrice83;
	}

	public void setScopeMemberPrice83(String scopeMemberPrice83) {
		this.scopeMemberPrice83 = scopeMemberPrice83;
	}

	public String getAskPriceScope() {
		return askPriceScope;
	}

	public void setAskPriceScope(String askPriceScope) {
		this.askPriceScope = askPriceScope;
	}

	public String getMemberPrice84() {
		return memberPrice84;
	}

	public void setMemberPrice84(String memberPrice84) {
		this.memberPrice84 = memberPrice84;
	}

	public String getMemberPrice85() {
		return memberPrice85;
	}

	public void setMemberPrice85(String memberPrice85) {
		this.memberPrice85 = memberPrice85;
	}

	public String getMemberPrice86() {
		return memberPrice86;
	}
	
	public String getMemberPrice127() {
		return memberPrice127;
	}

	public void setMemberPrice127(String memberPrice127) {
		this.memberPrice127 = memberPrice127;
	}

	public void setMemberPrice86(String memberPrice86) {
		this.memberPrice86 = memberPrice86;
	}

	public String getScopeMemberPrice84() {
		return scopeMemberPrice84;
	}

	public void setScopeMemberPrice84(String scopeMemberPrice84) {
		this.scopeMemberPrice84 = scopeMemberPrice84;
	}

	public String getScopeMemberPrice85() {
		return scopeMemberPrice85;
	}

	public void setScopeMemberPrice85(String scopeMemberPrice85) {
		this.scopeMemberPrice85 = scopeMemberPrice85;
	}

	public String getScopeMemberPrice86() {
		return scopeMemberPrice86;
	}

	public void setScopeMemberPrice86(String scopeMemberPrice86) {
		this.scopeMemberPrice86 = scopeMemberPrice86;
	}

	public String getMemberPrice87() {
		return memberPrice87;
	}

	public void setMemberPrice87(String memberPrice87) {
		this.memberPrice87 = memberPrice87;
	}

	public String getMemberPrice88() {
		return memberPrice88;
	}

	public void setMemberPrice88(String memberPrice88) {
		this.memberPrice88 = memberPrice88;
	}

	public String getScopeMemberPrice87() {
		return scopeMemberPrice87;
	}

	public void setScopeMemberPrice87(String scopeMemberPrice87) {
		this.scopeMemberPrice87 = scopeMemberPrice87;
	}

	public String getScopeMemberPrice88() {
		return scopeMemberPrice88;
	}

	public void setScopeMemberPrice88(String scopeMemberPrice88) {
		this.scopeMemberPrice88 = scopeMemberPrice88;
	}

	public String getMemberPrice89() {
		return memberPrice89;
	}

	public void setMemberPrice89(String memberPrice89) {
		this.memberPrice89 = memberPrice89;
	}

	public String getMemberPrice90() {
		return memberPrice90;
	}

	public void setMemberPrice90(String memberPrice90) {
		this.memberPrice90 = memberPrice90;
	}

	public String getScopeMemberPrice89() {
		return scopeMemberPrice89;
	}

	public void setScopeMemberPrice89(String scopeMemberPrice89) {
		this.scopeMemberPrice89 = scopeMemberPrice89;
	}

	public String getScopeMemberPrice90() {
		return scopeMemberPrice90;
	}

	public void setScopeMemberPrice90(String scopeMemberPrice90) {
		this.scopeMemberPrice90 = scopeMemberPrice90;
	}

	public String getMemberPrice91() {
		return memberPrice91;
	}

	public void setMemberPrice91(String memberPrice91) {
		this.memberPrice91 = memberPrice91;
	}

	public String getScopeMemberPrice91() {
		return scopeMemberPrice91;
	}

	public void setScopeMemberPrice91(String scopeMemberPrice91) {
		this.scopeMemberPrice91 = scopeMemberPrice91;
	}

	public String getMemberPrice92() {
		return memberPrice92;
	}

	public void setMemberPrice92(String memberPrice92) {
		this.memberPrice92 = memberPrice92;
	}

	public String getMemberPrice93() {
		return memberPrice93;
	}

	public void setMemberPrice93(String memberPrice93) {
		this.memberPrice93 = memberPrice93;
	}

	public String getMemberPrice94() {
		return memberPrice94;
	}

	public void setMemberPrice94(String memberPrice94) {
		this.memberPrice94 = memberPrice94;
	}

	public String getMemberPrice95() {
		return memberPrice95;
	}

	public void setMemberPrice95(String memberPrice95) {
		this.memberPrice95 = memberPrice95;
	}

	public String getMemberPrice96() {
		return memberPrice96;
	}

	public void setMemberPrice96(String memberPrice96) {
		this.memberPrice96 = memberPrice96;
	}

	public String getMemberPrice97() {
		return memberPrice97;
	}

	public void setMemberPrice97(String memberPrice97) {
		this.memberPrice97 = memberPrice97;
	}

	public String getScopeMemberPrice92() {
		return scopeMemberPrice92;
	}

	public void setScopeMemberPrice92(String scopeMemberPrice92) {
		this.scopeMemberPrice92 = scopeMemberPrice92;
	}

	public String getScopeMemberPrice93() {
		return scopeMemberPrice93;
	}

	public void setScopeMemberPrice93(String scopeMemberPrice93) {
		this.scopeMemberPrice93 = scopeMemberPrice93;
	}

	public String getScopeMemberPrice94() {
		return scopeMemberPrice94;
	}

	public void setScopeMemberPrice94(String scopeMemberPrice94) {
		this.scopeMemberPrice94 = scopeMemberPrice94;
	}

	public String getScopeMemberPrice95() {
		return scopeMemberPrice95;
	}

	public void setScopeMemberPrice95(String scopeMemberPrice95) {
		this.scopeMemberPrice95 = scopeMemberPrice95;
	}

	public String getScopeMemberPrice96() {
		return scopeMemberPrice96;
	}

	public void setScopeMemberPrice96(String scopeMemberPrice96) {
		this.scopeMemberPrice96 = scopeMemberPrice96;
	}

	public String getScopeMemberPrice97() {
		return scopeMemberPrice97;
	}

	public void setScopeMemberPrice97(String scopeMemberPrice97) {
		this.scopeMemberPrice97 = scopeMemberPrice97;
	}

	public String getMemberPrice98() {
		return memberPrice98;
	}

	public void setMemberPrice98(String memberPrice98) {
		this.memberPrice98 = memberPrice98;
	}

	public String getMemberPrice99() {
		return memberPrice99;
	}

	public void setMemberPrice99(String memberPrice99) {
		this.memberPrice99 = memberPrice99;
	}

	public String getMemberPrice126() {
		return memberPrice126;
	}

	public void setMemberPrice126(String memberPrice126) {
		this.memberPrice126 = memberPrice126;
	}

	public String getScopeMemberPrice98() {
		return scopeMemberPrice98;
	}

	public void setScopeMemberPrice98(String scopeMemberPrice98) {
		this.scopeMemberPrice98 = scopeMemberPrice98;
	}

	public String getScopeMemberPrice99() {
		return scopeMemberPrice99;
	}

	public void setScopeMemberPrice99(String scopeMemberPrice99) {
		this.scopeMemberPrice99 = scopeMemberPrice99;
	}

	public String getScopeMemberPrice100() {
		return scopeMemberPrice100;
	}

	public void setScopeMemberPrice100(String scopeMemberPrice100) {
		this.scopeMemberPrice100 = scopeMemberPrice100;
	}

	public String getScopeMemberPrice101() {
		return scopeMemberPrice101;
	}

	public void setScopeMemberPrice101(String scopeMemberPrice101) {
		this.scopeMemberPrice101 = scopeMemberPrice101;
	}

	public String getMemberPrice100() {
		return memberPrice100;
	}

	public void setMemberPrice100(String memberPrice100) {
		this.memberPrice100 = memberPrice100;
	}

	public String getMemberPrice101() {
		return memberPrice101;
	}

	public void setMemberPrice101(String memberPrice101) {
		this.memberPrice101 = memberPrice101;
	}

	public String getMemberPrice102() {
		return memberPrice102;
	}

	public void setMemberPrice102(String memberPrice102) {
		this.memberPrice102 = memberPrice102;
	}

	public String getScopeMemberPrice102() {
		return scopeMemberPrice102;
	}

	public void setScopeMemberPrice102(String scopeMemberPrice102) {
		this.scopeMemberPrice102 = scopeMemberPrice102;
	}

	public String getMemberPrice103() {
		return memberPrice103;
	}

	public void setMemberPrice103(String memberPrice103) {
		this.memberPrice103 = memberPrice103;
	}

	public String getScopeMemberPrice103() {
		return scopeMemberPrice103;
	}

	public void setScopeMemberPrice103(String scopeMemberPrice103) {
		this.scopeMemberPrice103 = scopeMemberPrice103;
	}

	public String getMemberPrice104() {
		return memberPrice104;
	}

	public void setMemberPrice104(String memberPrice104) {
		this.memberPrice104 = memberPrice104;
	}

	public String getMemberPrice105() {
		return memberPrice105;
	}

	public void setMemberPrice105(String memberPrice105) {
		this.memberPrice105 = memberPrice105;
	}

	public String getMemberPrice106() {
		return memberPrice106;
	}

	public void setMemberPrice106(String memberPrice106) {
		this.memberPrice106 = memberPrice106;
	}

	public String getScopeMemberPrice104() {
		return scopeMemberPrice104;
	}

	public void setScopeMemberPrice104(String scopeMemberPrice104) {
		this.scopeMemberPrice104 = scopeMemberPrice104;
	}

	public String getScopeMemberPrice105() {
		return scopeMemberPrice105;
	}

	public void setScopeMemberPrice105(String scopeMemberPrice105) {
		this.scopeMemberPrice105 = scopeMemberPrice105;
	}

	public String getScopeMemberPrice106() {
		return scopeMemberPrice106;
	}

	public void setScopeMemberPrice106(String scopeMemberPrice106) {
		this.scopeMemberPrice106 = scopeMemberPrice106;
	}

	public String getMemberPrice107() {
		return memberPrice107;
	}

	public void setMemberPrice107(String memberPrice107) {
		this.memberPrice107 = memberPrice107;
	}

	public String getMemberPrice108() {
		return memberPrice108;
	}

	public void setMemberPrice108(String memberPrice108) {
		this.memberPrice108 = memberPrice108;
	}

	public String getMemberPrice109() {
		return memberPrice109;
	}

	public void setMemberPrice109(String memberPrice109) {
		this.memberPrice109 = memberPrice109;
	}

	public String getMemberPrice110() {
		return memberPrice110;
	}

	public void setMemberPrice110(String memberPrice110) {
		this.memberPrice110 = memberPrice110;
	}

	public String getMemberPrice111() {
		return memberPrice111;
	}

	public void setMemberPrice111(String memberPrice111) {
		this.memberPrice111 = memberPrice111;
	}

	public String getScopeMemberPrice107() {
		return scopeMemberPrice107;
	}

	public void setScopeMemberPrice107(String scopeMemberPrice107) {
		this.scopeMemberPrice107 = scopeMemberPrice107;
	}

	public String getScopeMemberPrice108() {
		return scopeMemberPrice108;
	}

	public void setScopeMemberPrice108(String scopeMemberPrice108) {
		this.scopeMemberPrice108 = scopeMemberPrice108;
	}

	public String getScopeMemberPrice109() {
		return scopeMemberPrice109;
	}

	public void setScopeMemberPrice109(String scopeMemberPrice109) {
		this.scopeMemberPrice109 = scopeMemberPrice109;
	}

	public String getScopeMemberPrice110() {
		return scopeMemberPrice110;
	}

	public void setScopeMemberPrice110(String scopeMemberPrice110) {
		this.scopeMemberPrice110 = scopeMemberPrice110;
	}

	public String getScopeMemberPrice111() {
		return scopeMemberPrice111;
	}

	public void setScopeMemberPrice111(String scopeMemberPrice111) {
		this.scopeMemberPrice111 = scopeMemberPrice111;
	}

	public String getMemberPrice112() {
		return memberPrice112;
	}

	public void setMemberPrice112(String memberPrice112) {
		this.memberPrice112 = memberPrice112;
	}

	public String getScopeMemberPrice112() {
		return scopeMemberPrice112;
	}

	public void setScopeMemberPrice112(String scopeMemberPrice112) {
		this.scopeMemberPrice112 = scopeMemberPrice112;
	}

	public String getMemberPrice113() {
		return memberPrice113;
	}

	public void setMemberPrice113(String memberPrice113) {
		this.memberPrice113 = memberPrice113;
	}

	public String getMemberPrice114() {
		return memberPrice114;
	}

	public void setMemberPrice114(String memberPrice114) {
		this.memberPrice114 = memberPrice114;
	}

	public String getMemberPrice115() {
		return memberPrice115;
	}

	public void setMemberPrice115(String memberPrice115) {
		this.memberPrice115 = memberPrice115;
	}

	public String getMemberPrice116() {
		return memberPrice116;
	}

	public void setMemberPrice116(String memberPrice116) {
		this.memberPrice116 = memberPrice116;
	}

	public String getMemberPrice117() {
		return memberPrice117;
	}

	public void setMemberPrice117(String memberPrice117) {
		this.memberPrice117 = memberPrice117;
	}

	public String getMemberPrice118() {
		return memberPrice118;
	}

	public void setMemberPrice118(String memberPrice118) {
		this.memberPrice118 = memberPrice118;
	}

	public String getScopeMemberPrice113() {
		return scopeMemberPrice113;
	}

	public void setScopeMemberPrice113(String scopeMemberPrice113) {
		this.scopeMemberPrice113 = scopeMemberPrice113;
	}

	public String getScopeMemberPrice114() {
		return scopeMemberPrice114;
	}

	public void setScopeMemberPrice114(String scopeMemberPrice114) {
		this.scopeMemberPrice114 = scopeMemberPrice114;
	}

	public String getScopeMemberPrice115() {
		return scopeMemberPrice115;
	}

	public void setScopeMemberPrice115(String scopeMemberPrice115) {
		this.scopeMemberPrice115 = scopeMemberPrice115;
	}

	public String getScopeMemberPrice116() {
		return scopeMemberPrice116;
	}

	public void setScopeMemberPrice116(String scopeMemberPrice116) {
		this.scopeMemberPrice116 = scopeMemberPrice116;
	}

	public String getScopeMemberPrice117() {
		return scopeMemberPrice117;
	}

	public void setScopeMemberPrice117(String scopeMemberPrice117) {
		this.scopeMemberPrice117 = scopeMemberPrice117;
	}

	public String getScopeMemberPrice118() {
		return scopeMemberPrice118;
	}

	public void setScopeMemberPrice118(String scopeMemberPrice118) {
		this.scopeMemberPrice118 = scopeMemberPrice118;
	}

    public String getFlagshipStoreIds() {
        return flagshipStoreIds;
    }

    public void setFlagshipStoreIds(String flagshipStoreIds) {
        this.flagshipStoreIds = flagshipStoreIds;
    }

	public String getReferencePriceFor4S() {
		return referencePriceFor4S;
	}

	public void setReferencePriceFor4S(String referencePriceFor4S) {
		this.referencePriceFor4S = referencePriceFor4S;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMemberPrice119() {
		return memberPrice119;
	}

	public void setMemberPrice119(String memberPrice119) {
		this.memberPrice119 = memberPrice119;
	}

	public String getScopeMemberPrice119() {
		return scopeMemberPrice119;
	}

	public void setScopeMemberPrice119(String scopeMemberPrice119) {
		this.scopeMemberPrice119 = scopeMemberPrice119;
	}

	public String getCbSort() {
		return cbSort;
	}

	public void setCbSort(String cbSort) {
		this.cbSort = cbSort;
	}

	public String getMemberPrice120() {
		return memberPrice120;
	}

	public void setMemberPrice120(String memberPrice120) {
		this.memberPrice120 = memberPrice120;
	}

	public String getMemberPrice121() {
		return memberPrice121;
	}

	public void setMemberPrice121(String memberPrice121) {
		this.memberPrice121 = memberPrice121;
	}

	public String getMemberPrice122() {
		return memberPrice122;
	}

	public void setMemberPrice122(String memberPrice122) {
		this.memberPrice122 = memberPrice122;
	}

	public String getMemberPrice123() {
		return memberPrice123;
	}

	public void setMemberPrice123(String memberPrice123) {
		this.memberPrice123 = memberPrice123;
	}

	public String getMemberPrice124() {
		return memberPrice124;
	}

	public void setMemberPrice124(String memberPrice124) {
		this.memberPrice124 = memberPrice124;
	}

	public String getMemberPrice125() {
		return memberPrice125;
	}

	public void setMemberPrice125(String memberPrice125) {
		this.memberPrice125 = memberPrice125;
	}

	public String getScopeMemberPrice120() {
		return scopeMemberPrice120;
	}

	public void setScopeMemberPrice120(String scopeMemberPrice120) {
		this.scopeMemberPrice120 = scopeMemberPrice120;
	}

	public String getScopeMemberPrice121() {
		return scopeMemberPrice121;
	}

	public void setScopeMemberPrice121(String scopeMemberPrice121) {
		this.scopeMemberPrice121 = scopeMemberPrice121;
	}

	public String getScopeMemberPrice122() {
		return scopeMemberPrice122;
	}

	public void setScopeMemberPrice122(String scopeMemberPrice122) {
		this.scopeMemberPrice122 = scopeMemberPrice122;
	}

	public String getScopeMemberPrice123() {
		return scopeMemberPrice123;
	}

	public void setScopeMemberPrice123(String scopeMemberPrice123) {
		this.scopeMemberPrice123 = scopeMemberPrice123;
	}

	public String getScopeMemberPrice124() {
		return scopeMemberPrice124;
	}

	public void setScopeMemberPrice124(String scopeMemberPrice124) {
		this.scopeMemberPrice124 = scopeMemberPrice124;
	}
	
	public String getMemberPrice128() {
		return memberPrice128;
	}

	public void setMemberPrice128(String memberPrice128) {
		this.memberPrice128 = memberPrice128;
	}

	public String getScopeMemberPrice128() {
		return scopeMemberPrice128;
	}

	public void setScopeMemberPrice128(String scopeMemberPrice128) {
		this.scopeMemberPrice128 = scopeMemberPrice128;
	}

	public String getScopeMemberPrice125() {
		return scopeMemberPrice125;
	}

	public void setScopeMemberPrice125(String scopeMemberPrice125) {
		this.scopeMemberPrice125 = scopeMemberPrice125;
	}

	public String getScopeMemberPrice126() {
		return scopeMemberPrice126;
	}

	public void setScopeMemberPrice126(String scopeMemberPrice126) {
		this.scopeMemberPrice126 = scopeMemberPrice126;
	}

	public String getScopeMemberPrice127() {
		return scopeMemberPrice127;
	}

	public void setScopeMemberPrice127(String scopeMemberPrice127) {
		this.scopeMemberPrice127 = scopeMemberPrice127;
	}

    public String getIsGoodsQuantityFSId() {
        return isGoodsQuantityFSId;
    }

    public void setIsGoodsQuantityFSId(String isGoodsQuantityFSId) {
        this.isGoodsQuantityFSId = isGoodsQuantityFSId;
    }

	public String getPartyRScope() {
		return partyRScope;
	}

	public void setPartyRScope(String partyRScope) {
		this.partyRScope = partyRScope;
	}

	public String getPartyRChangePrice() {
		return partyRChangePrice;
	}

	public void setPartyRChangePrice(String partyRChangePrice) {
		this.partyRChangePrice = partyRChangePrice;
	}

	public Integer getProductBrandType() {
		return productBrandType;
	}

	public void setProductBrandType(Integer productBrandType) {
		this.productBrandType = productBrandType;
	}

	public String getPartyRChangePriceScope() {
		return partyRChangePriceScope;
	}

	public void setPartyRChangePriceScope(String partyRChangePriceScope) {
		this.partyRChangePriceScope = partyRChangePriceScope;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
