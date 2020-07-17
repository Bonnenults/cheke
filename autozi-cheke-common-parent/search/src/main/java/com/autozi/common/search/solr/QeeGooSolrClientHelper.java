package com.autozi.common.search.solr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.ApplicationPropertiesUtils;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-3-19 下午3:56
 */
public class QeeGooSolrClientHelper {

	private static Logger logger = LoggerFactory.getLogger(QeeGooSolrClientHelper.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");
	private static String specialOilAreaStoreIds = "1603311432400068,1606241610065346,1607181349300745";

	/**
	 * @Description: 搜索
	 * @author: zhiyun.chen
	 * 2015-6-6上午09:33:16
	 */
    public static SolrQuery initSolrQuery(Page<IndexEntity> page, List<PropertiesFilter> propertiesFilterList, String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 START BUG描述：
        if(propertiesFilterList==null
        		||propertiesFilterList.size()<=0){
        	return null;
        }
        //提前取出非查询条件的filter
        boolean generalQuery = false;
        boolean queryAskPriceGoods = false;
        boolean showDifferentPlatforms = false;//是否查询积压件的商品
		boolean flagshipStore = false ;
        String categories = null;//未登录情况下对商品进行筛选
        String ownerAreaShoppingStoreId = null;//区域商城Id
        String wearingCategory2Id = "";//保养件2级分类ID
        boolean stockByOnly = false ; //只显示有库存的商品
        String wareHouseId = null; //用户绑定的仓库id
        String jyjShowTagStr = "1";	// 积压件是否显示 --@BINBIN.LEE 2017-8-9
        String jyjInvoiceTagStr = "";//积压件是否开票

		//判断是否筛选仅显示FS有货的商品 By QiHaiYang
		boolean FSStockByOnly = false ; //只显示FS有库存的商品
		String FSId = null; //用户绑定的FS仓库id
		
		//b2r相关查询
		String b2rPartyIdVulnerability = "";//易损件小R主体Id
		String b2rPartyIdModel = "";//车型小R主体Id
		String b2rPartyIdTire = "";//轮胎小R主体Id
		String b2rPartyIdTools = "";//工具小R主体Id
		String b2rVulnerabilityOwnerPartyId = "";//易损件小R所属公司
		String b2rModelOwnerPartyId = "";//车型小R所属公司
		String b2rTireOwnerPartyId = "";//轮胎小R所属公司
		String b2rToolsOwnerPartyId = "";//工具小R所属公司
		String b2rVulnerabilityWareHouseId = "";//易损件小R对应的仓库
		String b2rModelWareHouseId = "";//车型件小R对应的仓库
		String b2rTireWareHouseId = "";//轮胎件小R对应的仓库
		String b2rToolsWareHouseId = "";//工具件小R对应的仓库
		boolean isB2BQuery = false;//是否是来自商城后台查询
		String supplierName = "";//供应商名称
		String productBrandType ="";//商品类型
        String goodsScope = ApplicationPropertiesUtils.getGoodsScope();//商品查询的范围 By QiHaiYang
        for (int i = 0; i < propertiesFilterList.size(); i++) {
        	String proName = propertiesFilterList.get(i).getName();
        	Object proValue = propertiesFilterList.get(i).getValue();
            if (proName.equals("generalQuery") && proValue.equals("1")) {
                generalQuery = true;
                propertiesFilterList.remove(i--);
            } else if (proName.equals("queryAskPriceGoods") && proValue.toString().equals("true")) {
            	queryAskPriceGoods = true;
            	propertiesFilterList.remove(i--);
            } else if (proName.equals("ownerAreaShoppingStoreId")) {
            	ownerAreaShoppingStoreId = proValue.toString();
            	propertiesFilterList.remove(i--);
            } else if (proName.equals("goodsScope")) {
                goodsScope = proValue.toString();
                propertiesFilterList.remove(i--);
            }else if(proName.equals("goodsSource")&& null!= proValue && (!proValue.equals("1")) ){
            	showDifferentPlatforms = true;
            	if(proValue.equals("0")){
            		propertiesFilterList.remove(i--);
            	}
            }else if(proName.equals("categories")){
            	categories = proValue.toString();
            	propertiesFilterList.remove(i--);
            }else if(proName.equals("flagshipStoreIds")){
				flagshipStore = true ;
			}else if(proName.equals("wearingCategory2Id")){
				wearingCategory2Id = (proValue == null?null:proValue.toString());
			}else if(proName.equals("stockByOnly")){
				stockByOnly = true;
				wareHouseId = proValue.toString();
				propertiesFilterList.remove(i--);
			}else if(proName.equals("FSStockByOnly")){
				FSStockByOnly = true;
				FSId = proValue.toString();
				propertiesFilterList.remove(i--);
			}else if(proName.equals("jyjShowTag")){	// 重新定义积压件显示方式 显示值为1，向下拼接条件
				if(proValue != null){
					jyjShowTagStr = proValue.toString();
					propertiesFilterList.remove(i--);	// 弃掉
				}
			}else if(proName.equals("jyjInvoiceTag")){	// 是否开票
				if(proValue != null){
					jyjInvoiceTagStr = proValue.toString();
					propertiesFilterList.remove(i--);	// 弃掉
				}
			} else if (proName.equals("b2rPartyIdVulnerability")) {
				b2rPartyIdVulnerability = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdModel")) {
            	b2rPartyIdModel = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdTire")) {
            	b2rPartyIdTire = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdTools")) {
            	b2rPartyIdTools = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rVulnerabilityOwnerPartyId")) {
            	b2rVulnerabilityOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rModelOwnerPartyId")) {
            	b2rModelOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rTireOwnerPartyId")) {
            	b2rTireOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rToolsOwnerPartyId")) {
            	b2rToolsOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rVulnerabilityWareHouseId")) {
            	b2rVulnerabilityWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rModelWareHouseId")) {
            	b2rModelWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rTireWareHouseId")) {
            	b2rTireWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rToolsWareHouseId")) {
            	b2rToolsWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            }else if (proName.equals("isB2BQuery")) {
            	isB2BQuery = (Boolean)proValue;
                propertiesFilterList.remove(i--);
            }else if (proName.equals("supplierName")) {
            	supplierName = proValue.toString();
                propertiesFilterList.remove(i--);
            }else if (proName.equals("productBrandType")) {
            	productBrandType = proValue.toString();
                propertiesFilterList.remove(i--);
            }
        }

		if(FSStockByOnly){
			showDifferentPlatforms = false;//仅看FS有货时不显示jyj商品 By QiHaiYang
		}

        boolean hasLogin = false;//是否登录
		// UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 END   BUG描述：
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            String proName = properties.getName();	// name
            Object proValue = properties.getValue();	// value
            if(PropertiesFilter.Type.EQUAL == type) {
                if(proName.equals("ids")){
                    List<String> ids = (List<String>)proValue;
                    if (ids.size() == 1) {
                        queryStr.append(" AND ").append("id").append(":").append(ids.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i=0; i<ids.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("id").append(":");
                            queryStr.append(ids.get(i));
                        }
                        queryStr.append(")");
                    }
                } else if(proName.equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询
            		Matcher matcher=pattern.matcher(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                    queryStr.append(" AND ").append(proName).append(":");
                    if(matcher.find()){
                        if(proValue.toString().length()==1){
                            queryStr.append("*"+proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append("*"+proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                }else if(proName.equals("carLogoId")|| proName.equals("carBrandId")||proName.equals("carModelId")||proName.equals("carSeriesId")){
                	if (proName.equals("carModelId") && (proValue.equals("1") || proValue.equals("-1"))) {
	                	queryStr.append(" AND ");
	                	queryStr.append(proName).append(":");
	                	queryStr.append("1");
                	} else {
                		queryStr.append(" AND ");
	                	queryStr.append("(").append(proName).append(":");
	                	queryStr.append(proValue);
                        if (!generalQuery){
                            queryStr.append(" OR ").append("carModelId").append(":").append(0);
                        }
                		queryStr.append(")");
                	}
                }else if(proName.equals("oem")){
                    queryStr.append(" AND ").append(proName).append(":").append("*"+proValue.toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                } else if(proName.equals("oemStr")){
                	String[] oemArray = proValue.toString().split(",");
                	if (oemArray.length == 1) {
                		queryStr.append(" AND ").append("oem").append(":").append("*"+proValue.toString().toUpperCase().replaceAll(IndexEntity.excludeRegex, "").replaceAll(" ", "")+"*");
                	} else {
                		queryStr.append(" AND ");
                		queryStr.append("(");
                		for (int i=0; i<oemArray.length; i++) {
                			if (i > 0) {
                				queryStr.append(" OR ");
                			}
                			queryStr.append("oem").append(":");
                			queryStr.append("*"+oemArray[i].toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                		}
                		queryStr.append(")");
                	}
                }else if (proName.equals("goodsBrandNameAndGoodsName") || proName.equals("goodsBrandNameAndCategory2Name")) {
                	queryStr.append(" AND ").append(proName).append(":");
                	queryStr.append(proValue.toString().replaceAll(" ", "").replaceAll(IndexEntity.excludeRegex, ""));
                }else if("memberGradeList".equals(proName) && StringUtils.isNotBlank(ownerAreaShoppingStoreId) && StringUtils.isNotBlank(ownerPartyId)){
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13

						if(showDifferentPlatforms){//保证兼容，pc端才展示积压件商品 
							queryStr.append(" AND (((((");
						}else{
							queryStr.append(" AND ((((");
						}

                	}else if(flagshipStore){
						queryStr.append(" AND (((");
					} else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
						if(showDifferentPlatforms){
							queryStr.append(" AND ((((");
						}else{
							queryStr.append(" AND (((");
						}

					}
                	queryStr.append("(");//多加一个括号，对应结束也多加一个
                	List<Integer> gradeList=(List<Integer>) proValue;
            		int index=0;
            		for (int i = 0; i < gradeList.size(); i++) {
            			if (gradeList.get(i)!=null) {
            				if (index > 0) {
            					queryStr.append(" OR (");
            				}
            				queryStr.append("categoryType").append(":").append(i+1);
            				queryStr.append(" AND ");
            				queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
            				queryStr.append(ownerPartyId + ")");
            				index++;
            			}
            		}
            		queryStr.append(" ) ");
            		queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
                	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
                	if(stockByOnly){
                		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":");
                		queryStr.append(wareHouseId);
                	}
					if(FSStockByOnly){
						queryStr.append(" AND ").append(" isGoodsQuantityFSId ").append(":");
						queryStr.append(FSId);
					}
                	
                	queryStr.append(" ) ");
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		//询价商品目录范围
                    	queryStr.append(" OR ").append("askPriceScope").append(":");
                    	queryStr.append(ownerAreaShoppingStoreId).append(")");
                	}
//                    if(StringUtils.isNotBlank(categories) && categories.equals("2")){//筛选品牌件
//                    	queryStr.append(" AND ").append("brandId:(* NOT 1402201548086417 NOT 595 NOT 1409242210460000) )");
//                    }else if(StringUtils.isNotBlank(categories) && categories.equals("1")){//筛选原厂件
//                    	queryStr.append(" AND ").append("brandId:(1402201548086417 OR 595 OR 1409242210460000))");
//                    }else if(StringUtils.isNotBlank(categories) && categories.equals("3")){//查品牌件和原厂件
//
//                    }
                    
                	if(showDifferentPlatforms){
                		/*@BINBIN.LEE 2017-8-8 添加积压件展示 jyjShowTag --START--*/
                		if(StringUtils.isNotBlank(categories) && categories.equals("1")){      //中驰自营
                			queryStr.append(" AND " ).append(" goodsSource:1").append(" ) " );
                		}else if(StringUtils.isNotBlank(categories) && categories.equals("2")){  //积压件
                			/*queryStr.append(" OR " ).append(" goodsSource:2").append(" ) " ).append(" AND " ).append(" (goodsSource:2 AND jyjShowTag:"+ jyjShowTagStr).append(") ");*/
                			queryStr.append(" OR " ).append(" goodsSource:2").append(" ) " ).append(" AND " ).append(" (goodsSource:2 "); 
                			if(StringUtils.isNotBlank(jyjShowTagStr)){
                				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
                				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);	
                			}
                			if(StringUtils.isNotBlank(jyjInvoiceTagStr) && jyjInvoiceTagStr.equals("1")){//是否开票(不开票的也要展示开票的商品)
                				queryStr.append("  AND jyjInvoiceTag:").append(jyjInvoiceTagStr);
                        	}
                			queryStr.append(")");
                		}else{
                			/*queryStr.append(" OR " ).append(" (goodsSource:2 ").append(" AND jyjShowTag:"+ jyjShowTagStr +" ))" );*/
                			queryStr.append(" OR " ).append(" (goodsSource:2 ");
                			if(StringUtils.isNotBlank(jyjShowTagStr)){
                				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
                				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);
                			}
                			if(StringUtils.isNotBlank(jyjInvoiceTagStr) && jyjInvoiceTagStr.equals("1")){//是否开票(不开票的也要展示开票的商品)
                				queryStr.append("  AND jyjInvoiceTag:").append(jyjInvoiceTagStr);
                        	}
                			queryStr.append(")").append(")");
                		}
                		/*@BINBIN.LEE 2017-8-8 添加积压件展示 jyjShowTag --END--*/
                	}
                	queryStr.append(")");//多加一个括号，对应结束也多加一个
//                	if(showDifferentPlatforms){
//                    	queryStr.append(" OR ").append("(");
//                    	// @Binbin.Lee 对id进行锁定搜索只搜索积压件（含有JYJ前缀的商品） 2017-4-10 --Start--
//                        queryStr.append(" ( ").append(" goodsSource:2 AND jyjProductType:(30 OR 40 OR 50)").append(")");
//                        // @Binbin.Lee 对id进行锁定搜索只搜索积压件（含有JYJ前缀的商品） 2017-4-10 --end--
//                        if(StringUtils.isNotBlank(categories) && categories.equals("2")){//筛选品牌件
//                        	queryStr.append(" AND ").append("jyjProductType:50");
//                        }else if(StringUtils.isNotBlank(categories) && categories.equals("1")){
//                        	queryStr.append(" AND ").append("jyjProductType:(30 OR 40)");
//                        }else if(StringUtils.isNotBlank(categories) && categories.equals("3")){//查品牌件和原厂件
//                        	queryStr.append(" AND ").append("jyjProductType:(30 OR 40 OR 50)");
//                        }
//                    	queryStr.append(" )");
//                    	queryStr.append(")");//需要查积压件，多一个“括号”
//                	}
                	hasLogin = true;
                }else if (proName.equals("brandIdList")) {
                    List<Long> brandIdList = (List<Long>) proValue;
                    if (brandIdList.size() == 1) {
                        queryStr.append(" AND ").append("brandId").append(":").append(brandIdList.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i = 0; i < brandIdList.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("brandId").append(":");
                            queryStr.append(brandIdList.get(i));
                        }
                        queryStr.append(")");
                    }
                }else if (proName.equals("brandId")) {
                	String brandId = proValue.toString();
                	//陕西区域，搜索壳牌、美孚、嘉实多，转到带英文的品牌上
                	queryStr.append(" AND ").append(proName).append(":");
                	if (ownerAreaShoppingStoreId != null && (specialOilAreaStoreIds.contains(ownerAreaShoppingStoreId)) && ("585".equals(brandId) || "586".equals(brandId) || "593".equals(brandId))) {
                		if ("585".equals(brandId)) {//美孚
                			queryStr.append("1509241604271138");
                		}
                		if ("586".equals(brandId)) {//壳牌
                			queryStr.append("1509241604171137");
                		}
                		if ("593".equals(brandId)) {//嘉实多
                			queryStr.append("1509241604421139");
                		}
                	} else {
                		queryStr.append(proValue);
                	}
                }else{
                	queryStr.append(" AND ").append(proName).append(":");
                	queryStr.append(proValue);
                }
            }else if(PropertiesFilter.Type.SORT_ASC == properties.getType()){
                solrQuery.addSort(proName, SolrQuery.ORDER.asc);
            }else if(PropertiesFilter.Type.SORT_DESC == properties.getType()){
                solrQuery.addSort(proName, SolrQuery.ORDER.desc);
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
                if (proName.equals("goodsName") || proName.equals("goodsBrandNameAndGoodsName") || proName.equals("goodsBrandNameAndCategory2Name")) {
                	solrQuery.addFacetField(proName).setFacetMinCount(1).setFacetLimit(30);
                } else {
                	solrQuery.addFacetField(proName).setFacetMinCount(1).setFacetLimit(1000);
                }
            }
        }
        
        //小R下的小B查询条件
        if (StringUtils.isNotBlank(b2rPartyIdVulnerability) || StringUtils.isNotBlank(b2rPartyIdModel) || StringUtils.isNotBlank(b2rPartyIdTire) || StringUtils.isNotBlank(b2rPartyIdTools)) {
        	queryStr.append(" AND (");
        	//小R商品范围条件
        	queryStr.append("(");//开始
        	boolean needAddOr = false;
        	if (StringUtils.isNotBlank(b2rPartyIdVulnerability) && StringUtils.isNotBlank(b2rVulnerabilityOwnerPartyId)) {//易损小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdVulnerability).append(" AND (scopeMemberPrice128:" + b2rVulnerabilityOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdVulnerability + ")").append(" AND productBrandType:10001");
        		if(StringUtils.isNotBlank(b2rVulnerabilityWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rVulnerabilityWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdModel) &&StringUtils.isNotBlank(b2rModelOwnerPartyId)) {//车型小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdModel).append(" AND scopeMemberPrice128:" + b2rModelOwnerPartyId).append(" AND productBrandType:10002");
        		if(StringUtils.isNotBlank(b2rModelWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rModelWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdTire) &&StringUtils.isNotBlank(b2rTireOwnerPartyId)) {//轮胎小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdTire).append(" AND (scopeMemberPrice128:" + b2rTireOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdTire + ")").append(" AND productBrandType:10003");
        		if(StringUtils.isNotBlank(b2rTireWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rTireWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdTools) &&StringUtils.isNotBlank(b2rToolsOwnerPartyId)) {//工具小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdTools).append(" AND (scopeMemberPrice128:" + b2rToolsOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdTools + ")").append(" AND productBrandType:10004");
        		if(StringUtils.isNotBlank(b2rToolsWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rToolsWareHouseId);
            	}
        		
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if(FSStockByOnly){//无人仓
				queryStr.append(" AND ").append(" isGoodsQuantityFSId ").append(":");
				queryStr.append(FSId);
			}
        	queryStr.append(")");//结束
        	
        	//自营或积压
        	if(showDifferentPlatforms){
        		if(StringUtils.isNotBlank(categories) && categories.equals("1")){      //中驰自营
        			queryStr.append(" AND " ).append(" goodsSource:1 ");
        		}else if(StringUtils.isNotBlank(categories) && categories.equals("2")){  //积压件
        			queryStr.append(" AND " ).append(" (goodsSource:2 "); 
        			if(StringUtils.isNotBlank(jyjShowTagStr)){
        				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
        				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);	
        			}
        			queryStr.append(")" );
        		}else {
        			queryStr.append(" OR " ).append(" (goodsSource:2 "); 
        			if(StringUtils.isNotBlank(jyjShowTagStr)){
        				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
        				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);	
        			}
        			queryStr.append(")" );
        		}
        	}
        	
        	queryStr.append(")");
        	hasLogin = true;
        }
        
      //v4.9.1改变商品列表展示逻辑，区分积压件和中驰自营
        if(!hasLogin){
        	/*后台使用solr查询也是未登录，但却不需要限制条件*/
        	if(isB2BQuery){
        		queryStr.append(" AND ");
        		if(StringUtils.isNotBlank(productBrandType)){
            		queryStr.append("productBrandType").append(":").append(productBrandType);
        		}
        		if(StringUtils.isNotBlank(supplierName)){
        			queryStr.append(" AND ");
            		queryStr.append("supplierName").append(":").append(supplierName);
        		}
        		queryStr.append("");
        	}else{
	        	queryStr.append(" AND (");
	
	        	//中驰自营 OR 积压件
	        	if(StringUtils.isNotBlank(categories) && categories.equals("1")){//中驰自营
	            	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 START BUG描述：不登录不能查询这三个品牌及OEM品牌
	            	queryStr.append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
	        		queryStr.append(" AND" ).append(" goodsSource:1");
	        		if (StringUtils.isBlank(ownerPartyId) && StringUtils.isNotBlank(ownerAreaShoppingStoreId)) {
	    	        	queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
	    	        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
	    	        }
	        	} else if(StringUtils.isNotBlank(categories) && categories.equals("2")){//积压件 ps:积压件无 不登录不能查看某些品牌的限制
	        		queryStr.append(" (goodsSource:2 ");
	        		if(StringUtils.isNotBlank(jyjShowTagStr)){
	    				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
	    				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);
	    			}
	        		queryStr.append(")");
	        	}else{//无限定的情况下中驰自营和积压件都查询
	            	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 START BUG描述：不登录不能查询这三个品牌及OEM品牌
	            	queryStr.append("(brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
	            	if (StringUtils.isBlank(ownerPartyId) && StringUtils.isNotBlank(ownerAreaShoppingStoreId)) {
	    	        	queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
	    	        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
	    	        }
	        		queryStr.append(") OR (" ).append(" goodsSource: 2 ");
	        		if(StringUtils.isNotBlank(jyjShowTagStr)){
	    				/*可变更性, 不使用时可对此值设置null, 默认值为1*/
	    				queryStr.append(" AND jyjShowTag:" + jyjShowTagStr);
	    			}
	        		queryStr.append(")");
	        	}
	        	queryStr.append(")");
        	}
        }
        
//        if (!hasLogin) {
//        	queryStr.append(" AND ");
//        	queryStr.append(" (");//中驰和积压件外层括号--开始
//        	//---------------(中驰 AND (原厂件 or 品牌件) AND (brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))-------begin-------
//        	queryStr.append("(");//中驰外层括号--begin
//        	//(中驰 AND (原厂件 or 品牌件) AND (brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))
//        	//中驰
//        	queryStr.append("areaShoppingStoreScope").append(":");
//        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
//
//        	//(原厂件 or 品牌件)
//        	if(StringUtils.isNotBlank(categories) && categories.equals("1")){//原厂件
//        		queryStr.append(" AND" ).append(" brandId:(1402201548086417 OR 595 OR 1409242210460000)");
//        	} else if(StringUtils.isNotBlank(categories) && categories.equals("2")){//品牌件
//        		queryStr.append(" AND" ).append(" brandId:(* NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
//        	}
//        	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 START BUG描述：不登录不能查询这三个品牌及OEM品牌
//        	queryStr.append(" AND ").append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
//        	queryStr.append(")");//中驰外层括号--end
//        	//---------------(中驰 AND (原厂件 or 品牌件) AND (brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))---------end-----
//
//        	//---------------积压件 AND (原厂件 or 品牌件)-------begin-------
//        	if (showDifferentPlatforms) {//需要查积压件
//        		queryStr.append(" OR ");
//        		queryStr.append("(");//积压件外层括号--begin
//        		//积压件 AND (原厂件 or 品牌件)
//        		queryStr.append("(goodsSource:2 AND jyjProductType:(30 OR 40 OR 50))");//积压件
//        		if(StringUtils.isNotBlank(categories) && categories.equals("1")){//原厂件
//	        		queryStr.append(" AND " ).append("jyjProductType:(30 OR 40)");
//	        	} else if(StringUtils.isNotBlank(categories) && categories.equals("2")){//品牌件
//	        		queryStr.append(" AND " ).append("jyjProductType:50");
//	        	}
//        		queryStr.append(")");//积压件外层括号--end
//        	}
//        	//---------------积压件 AND (原厂件 or 品牌件)-------begin-------
//        	queryStr.append(" ) ");//中驰和积压件外层括号--结束
//        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次查询条件为："+solrQueryStr+";直接返回");
        	return null;
        }
        System.out.println("current solrQueryStr is :"+solrQueryStr);
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次查询条件为："+solrQueryStr);
        _logger_solr.info("本次查询条件为："+solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
//        //设置搜索结果返回的字段
        /*
         * 新增--"jyjAreaId","jyjAreaName","jyjProductType","jyjGeneral","jyjApplicableModel", "jyjShoppingPrice","goodsSource","jyjGoodsNameInfo","supplierId"
         */
        solrQuery.setFields("id","goodsName","brandName","goodsStyle","serialNumber","smallImagePath","middleImagePath","largeImagePath",
        		"memberPrice1","memberPrice2","memberPrice3","memberPrice4","memberPrice5","memberPrice6","memberPrice7","memberPrice8","memberPrice9","memberPrice10",
        		"memberPrice11","memberPrice12","memberPrice13","memberPrice14","memberPrice15","memberPrice16","memberPrice17","memberPrice18","memberPrice19","memberPrice20","memberPrice21","memberPrice22",
        		"memberPrice23","memberPrice24","memberPrice25","memberPrice26","memberPrice27","memberPrice28",
        		"memberPrice29","memberPrice30","memberPrice31","memberPrice32","memberPrice33","memberPrice34",
        		"memberPrice35","memberPrice36","memberPrice37","memberPrice38","memberPrice39","memberPrice40","memberPrice41","memberPrice42",
        		"memberPrice43","memberPrice44","memberPrice45","memberPrice46","memberPrice47","memberPrice48","memberPrice49","memberPrice50",
        		"memberPrice51","memberPrice52","memberPrice53","memberPrice54","memberPrice55","memberPrice56","memberPrice57","memberPrice58","memberPrice59","memberPrice60",
        		"memberPrice61","memberPrice62","memberPrice63","memberPrice64","memberPrice65","memberPrice66","memberPrice67","memberPrice68","memberPrice69","memberPrice70",
        		"memberPrice71","memberPrice72","memberPrice73","memberPrice74","memberPrice75","memberPrice76","memberPrice77","memberPrice78","memberPrice79","memberPrice80",
        		"memberPrice81","memberPrice82","memberPrice83","memberPrice84","memberPrice85","memberPrice86","memberPrice87","memberPrice88","memberPrice89","memberPrice90",
        		"memberPrice91","memberPrice92","memberPrice93","memberPrice94","memberPrice95","memberPrice96","memberPrice97","memberPrice98","memberPrice99","memberPrice100",
        		"memberPrice101","memberPrice102","memberPrice103","memberPrice104","memberPrice105","memberPrice106","memberPrice107","memberPrice108","memberPrice109","memberPrice110",
        		"memberPrice111","memberPrice112","memberPrice113","memberPrice114","memberPrice115","memberPrice116","memberPrice117","memberPrice118","memberPrice119","memberPrice120",
        		"memberPrice121","memberPrice122","memberPrice123","memberPrice124","memberPrice125","memberPrice126","memberPrice127","memberPrice128","memberPrice129",
        		"categoryType","partyRChangePrice","productBrandType","supplierName",
        		"oemAlias","promotionTitle","carBrandAndSeries","goodsRemark","goodsNameDisplay","askPriceScope","referencePriceFor4S","category2Id","productId",
        		"jyjAreaId","jyjAreaName","jyjProductType","jyjGeneral","jyjApplicableModel", "jyjShoppingPrice","goodsSource","jyjGoodsNameInfo","supplierId","brandId","jyjGoodsType"
        		);
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }

    /**
     * @Description: 获取商品信息
     * @author: pengfei.wang
     * @date: 2015-09-09
     * @version b2c-3.7
     */
    public static SolrQuery initSolrQuery(List<PropertiesFilter> propertiesFilterList, String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 START BUG描述：
        if(propertiesFilterList==null
                ||propertiesFilterList.size()<=0){
            return null;
        }

        //判断是否需要查询询价商品  zhiyun.chen 2016-05-26
        boolean queryAskPriceGoods = false;
        String ownerAreaShoppingStoreId = null;//区域商城Id
        String goodsScope = ApplicationPropertiesUtils.getGoodsScope();//商品查询的范围 By QiHaiYang
        for (int i = 0; i < propertiesFilterList.size(); i++) {
            if (propertiesFilterList.get(i).getName().equals("queryAskPriceGoods") && propertiesFilterList.get(i).getValue().toString().equals("true")) {
            	queryAskPriceGoods = true;
            	propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("ownerAreaShoppingStoreId")) {
            	ownerAreaShoppingStoreId = propertiesFilterList.get(i).getValue().toString();
            	propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("goodsScope")) {
                goodsScope = propertiesFilterList.get(i).getValue().toString();
                propertiesFilterList.remove(i--);
            }

        }
        if (StringUtils.isBlank(ownerPartyId)) {
        	queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
        }
        boolean hasLogin = false;//是否登录
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("ids")){
                    List<String> ids = (List)properties.getValue();
                    if (ids.size() == 1) {
                        queryStr.append(" AND ").append("id").append(":").append(ids.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i=0; i<ids.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("id").append(":");
                            queryStr.append(ids.get(i));
                        }
                        queryStr.append(")");
                    }
                } else if("memberGradeList".equals(properties.getName()) && StringUtils.isNotBlank(ownerAreaShoppingStoreId) && StringUtils.isNotBlank(ownerPartyId)){
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		queryStr.append(" AND ((((");
                	} else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
                		queryStr.append(" AND (((");
                	}
                	List<Integer> gradeList=(List<Integer>) properties.getValue();
            		int index=0;
            		for (int i = 0; i < gradeList.size(); i++) {
            			if (gradeList.get(i)!=null) {
            				if (index > 0) {
            					queryStr.append(" OR (");
            				}
            				queryStr.append("categoryType").append(":").append(i+1);
            				queryStr.append(" AND ");
            				queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
            				queryStr.append(ownerPartyId + ")");
            				index++;
            			}
            		}
            		queryStr.append(" ) ");
            		queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
                	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
                	queryStr.append(" ) ");
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		//询价商品目录范围
                    	queryStr.append(" OR ").append("askPriceScope").append(":");
                    	queryStr.append(ownerAreaShoppingStoreId).append(")");
                	}
                	hasLogin = true;
                } else{
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    queryStr.append(properties.getValue());
                }
            }
        }
        if (!hasLogin) {//未登录，搜索不到特定品牌下的商品
        	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 START BUG描述：不登录不能查询这三个品牌及OEM品牌
        	queryStr.append(" AND ").append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
        	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 END   BUG描述：
        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次查询条件为："+solrQueryStr+";直接返回");
            return null;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次查询条件为：" + solrQueryStr);
        _logger_solr.info("本次查询条件为：" + solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        return solrQuery;
    }

    /**
     * 排除utf-8以外的字符
     * */
    public static String stripNonCharCodepoints(String input) {
        StringBuilder retval = new StringBuilder();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);

            // Strip all non-characters
            // http://unicode.org/cldr/utility/list-unicodeset.jsp?a=[:Noncharacter_Code_Point=True:]
            // and non-printable control characters except tabulator, new line
            // and carriage return
            if (ch % 0x10000 != 0xffff && // 0xffff - 0x10ffff range step
                    // 0x10000
                    ch % 0x10000 != 0xfffe && // 0xfffe - 0x10fffe range
                    (ch <= 0xfdd0 || ch >= 0xfdef) && // 0xfdd0 - 0xfdef
                    (ch > 0x1F || ch == 0x9 || ch == 0xa || ch == 0xd)) {

                retval.append(ch);
            }
        }

        return retval.toString();
    }


    public static SolrQuery initSolrSuggest(String token) {
    	SolrQuery params = new SolrQuery();
		params.set(CommonParams.QT, "/suggest");
		params.set("q", token);
		params.set("spellcheck.build", "true");
		return params;
    }

    /**
     * MoreLikeThis Search
     * http://localhost:8983/solr/mlt?q=id:10&mlt.fl=keyWord&mlt.mindf=1&mlt.mintf=1&mlt.interestingTerms=details&mlt.match.include=false&rows=5
     * @param id  通过id确定查找此keyWord对应的相似值
     * @return
     */
    public static SolrQuery initMLT(long id){
    	SolrQuery params = new SolrQuery();
		params.set("qt", "/mlt");
		params.set(CommonParams.Q, "id:"+id);
		params.set("mlt.fl", "keyWord");
		params.set("mlt.mindf", "1");
		params.set("mlt.mintf", "1");
		params.set("mlt.interestingTerms", "details");
		params.set("mlt.match.include", "false");
		params.set("rows", "10");
		return params;
    }

    /**
     * @Description: 关键字提示
     * @author: zhiyun.chen
     * 2015-6-6上午09:33:00
     */
    public static SolrQuery initSolrFacetQuery(List<PropertiesFilter> propertiesFilterList,String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        if(propertiesFilterList==null||propertiesFilterList.size()<=0){
        	return solrQuery;
        }
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        //判断是否需要查询询价商品  zhiyun.chen 2016-05-26
        boolean queryAskPriceGoods = false;
        String ownerAreaShoppingStoreId = null;//区域商城Id
        boolean flagshipStore = false ; 
        boolean showDifferentPlatforms = false;
        
        //b2r相关查询
		String b2rPartyIdVulnerability = "";//易损件小R主体Id
		String b2rPartyIdModel = "";//车型小R主体Id
		String b2rPartyIdTire = "";//轮胎小R主体Id
		String b2rPartyIdTools = "";//工具小R主体Id
		String b2rVulnerabilityOwnerPartyId = "";//易损件小R所属公司
		String b2rModelOwnerPartyId = "";//车型小R所属公司
		String b2rTireOwnerPartyId = "";//轮胎小R所属公司
		String b2rToolsOwnerPartyId = "";//工具小R所属公司
		String b2rVulnerabilityWareHouseId = "";//易损件小R对应的仓库
		String b2rModelWareHouseId = "";//车型件小R对应的仓库
		String b2rTireWareHouseId = "";//轮胎件小R对应的仓库
		String b2rToolsWareHouseId = "";//工具件小R对应的仓库
        String goodsScope = ApplicationPropertiesUtils.getGoodsScope();//商品查询的范围 By QiHaiYang
        for (int i = 0; i < propertiesFilterList.size(); i++) {
        	String proName = propertiesFilterList.get(i).getName();
        	Object proValue = propertiesFilterList.get(i).getValue();
            if (proName.equals("queryAskPriceGoods") && proValue.toString().equals("true")) {
            	queryAskPriceGoods = true;
            	propertiesFilterList.remove(i--);
            } else if (proName.equals("ownerAreaShoppingStoreId")) {
            	ownerAreaShoppingStoreId = proValue.toString();
            	propertiesFilterList.remove(i--);
            } else if (proName.equals("goodsScope")) {
                goodsScope = proValue.toString();
                propertiesFilterList.remove(i--);
            }else if(proName.equals("goodsSource")){
            	showDifferentPlatforms = true;
            	propertiesFilterList.remove(i--);
            }else if(proName.equals("flagshipStoreIds")){
				flagshipStore = true ;
			} else if (proName.equals("b2rPartyIdVulnerability")) {
				b2rPartyIdVulnerability = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdModel")) {
            	b2rPartyIdModel = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdTire")) {
            	b2rPartyIdTire = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rPartyIdTools")) {
            	b2rPartyIdTools = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rVulnerabilityOwnerPartyId")) {
            	b2rVulnerabilityOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rModelOwnerPartyId")) {
            	b2rModelOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rTireOwnerPartyId")) {
            	b2rTireOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rToolsOwnerPartyId")) {
            	b2rToolsOwnerPartyId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rVulnerabilityWareHouseId")) {
            	b2rVulnerabilityWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rModelWareHouseId")) {
            	b2rModelWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rTireWareHouseId")) {
            	b2rTireWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            } else if (proName.equals("b2rToolsWareHouseId")) {
            	b2rToolsWareHouseId = proValue.toString();
                propertiesFilterList.remove(i--);
            }
        }
//        if (StringUtils.isBlank(ownerPartyId)) {
//        	queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
//        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
//        }
        boolean hasLogin = false;//是否登录
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询
            		Matcher matcher=pattern.matcher(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if(matcher.find()){
                        if(properties.getValue().toString().length()==1){
                            queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                        queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                } else if("memberGradeList".equals(properties.getName()) && StringUtils.isNotBlank(ownerAreaShoppingStoreId) && StringUtils.isNotBlank(ownerPartyId)){
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
						if(showDifferentPlatforms){//保证兼容，pc端才展示积压件商品
							queryStr.append(" AND (((((");
						}else{
							queryStr.append(" AND ((((");
						}
                	}else if(flagshipStore){
						queryStr.append(" AND (((");
					} else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
						if(showDifferentPlatforms){
							queryStr.append(" AND ((((");
						}else{
							queryStr.append(" AND (((");
						}
					}
                	List<Integer> gradeList=(List<Integer>) properties.getValue();
            		int index=0;
            		for (int i = 0; i < gradeList.size(); i++) {
            			if (gradeList.get(i)!=null) {
            				if (index > 0) {
            					queryStr.append(" OR (");
            				}
            				queryStr.append("categoryType").append(":").append(i+1);
            				queryStr.append(" AND ");
            				queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
            				queryStr.append(ownerPartyId + ")");
            				index++;
            			}
            		}
            		queryStr.append(" ) ");
            		queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
                	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
                	queryStr.append(" ) ");
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		//询价商品目录范围
                    	queryStr.append(" OR ").append("askPriceScope").append(":");
                    	queryStr.append(ownerAreaShoppingStoreId).append(")");

                	}
                	if(showDifferentPlatforms){
                    	queryStr.append(" OR ").append("(");
                    	// @Binbin.Lee 对id进行锁定搜索只搜索积压件（含有JYJ前缀的商品） 2017-4-10 --Start--
                        queryStr.append(" ( ").append(" goodsSource:2").append(")");
                        // @Binbin.Lee 对id进行锁定搜索只搜索积压件（含有JYJ前缀的商品） 2017-4-10 --end--
                    	queryStr.append(" )");
                    	queryStr.append(")");//需要查积压件，多一个“括号”
                	}
                	hasLogin = true;
                } else{
                	queryStr.append(" AND ").append(properties.getName()).append(":");
                	queryStr.append(properties.getValue());
                }
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
                solrQuery.addFacetField(properties.getName()).setFacetMinCount(1).setFacetLimit(10);//每个类，只取相关度最高的10条
            }
        }
        
        //小R下的小B查询条件
        if (StringUtils.isNotBlank(b2rPartyIdVulnerability) || StringUtils.isNotBlank(b2rPartyIdModel) || StringUtils.isNotBlank(b2rPartyIdTire) || StringUtils.isNotBlank(b2rPartyIdTools)) {
        	queryStr.append(" AND (");
        	//小R商品范围条件
        	queryStr.append("(");//开始
        	boolean needAddOr = false;
        	if (StringUtils.isNotBlank(b2rPartyIdVulnerability) && StringUtils.isNotBlank(b2rVulnerabilityOwnerPartyId)) {//易损小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdVulnerability).append(" AND (scopeMemberPrice128:" + b2rVulnerabilityOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdVulnerability + ")").append(" AND productBrandType:10001");
        		if(StringUtils.isNotBlank(b2rVulnerabilityWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rVulnerabilityWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdModel) &&StringUtils.isNotBlank(b2rModelOwnerPartyId)) {//车型小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdModel).append(" AND scopeMemberPrice128:" + b2rModelOwnerPartyId).append(" AND productBrandType:10002");
        		if(StringUtils.isNotBlank(b2rModelWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rModelWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdTire) &&StringUtils.isNotBlank(b2rTireOwnerPartyId)) {//轮胎小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdTire).append(" AND (scopeMemberPrice128:" + b2rTireOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdTire + ")").append(" AND productBrandType:10003");
        		if(StringUtils.isNotBlank(b2rTireWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rTireWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	if (StringUtils.isNotBlank(b2rPartyIdTools) &&StringUtils.isNotBlank(b2rToolsOwnerPartyId)) {//工具小R
        		if (needAddOr) {//需要加“OR”
        			queryStr.append(" OR ");
        		}
        		queryStr.append("(partyRScope:" + b2rPartyIdTools).append(" AND (scopeMemberPrice128:" + b2rToolsOwnerPartyId + " OR partyRChangePriceScope:" + b2rPartyIdTools + ")").append(" AND productBrandType:10004");
        		if(StringUtils.isNotBlank(b2rToolsWareHouseId)){
            		queryStr.append(" AND ").append(" isGoodsQuantityStockId ").append(":").append(b2rToolsWareHouseId);
            	}
        		queryStr.append(")");
        		needAddOr = true;
        	}
        	queryStr.append(")");//结束
        	
        	//自营或积压
        	if(showDifferentPlatforms){
    			queryStr.append(" OR " ).append(" (goodsSource:2 "); 
    			queryStr.append(" AND jyjShowTag:1");	
    			queryStr.append(")" );
        	}
        	
        	queryStr.append(")");
        	hasLogin = true;
        }
        
        if (!hasLogin) {//未登录，搜索不到特定品牌下的商品
        	queryStr.append(" AND ");
        	queryStr.append(" (");//中驰和积压件外层括号--开始
        	//---------------(中驰 AND brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))-------begin-------
        	queryStr.append("(");//中驰外层括号--begin
        	//(中驰 AND  AND (brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))
        	//中驰
        	queryStr.append("areaShoppingStoreScope").append(":");
        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);


        	// UPDATE BY LIHF@QEEGOO 2015-10-23上午8:50:56 START BUG描述：不登录不能查询这三个品牌及OEM品牌
        	queryStr.append(" AND ").append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");
        	queryStr.append(")");//中驰外层括号--end
        	//---------------(中驰 AND  AND brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000))---------end-----

        	//---------------积压件 AND (原厂件 or 品牌件)-------begin-------
        	if (showDifferentPlatforms) {//需要查积压件
        		queryStr.append(" OR ");
        		//积压件 AND (原厂件 or 品牌件)
        		queryStr.append("(goodsSource:2 AND jyjShowTag:1)");//积压件
        	}
        	//---------------积压件 AND (原厂件 or 品牌件)-------begin-------
        	queryStr.append(" ) ");//中驰和积压件外层括号--结束
        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次关键字搜索提示查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次关键字搜索提示查询条件为："+solrQueryStr+";直接返回");
        	return solrQuery;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次关键字搜索提示查询条件为：" + solrQueryStr);
        _logger_solr.info("本次关键字搜索提示查询条件为：" + solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
//        //设置搜索结果返回的字段
        solrQuery.setFields("id");
        solrQuery.setStart(0);
        solrQuery.setRows(0);
        return solrQuery;
    }

    /**
     * @Description: 不区分是否登录，用于品牌、品类、车型查找
     * @author: zhiyun.chen
     * 2015-12-1下午06:51:31
     */
    public static SolrQuery initSolrQueryForNoLogin(Page<IndexEntity> page, List<PropertiesFilter> propertiesFilterList, String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 START BUG描述：
        if(propertiesFilterList==null
        		||propertiesFilterList.size()<=0){
        	return null;
        }
		// UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 END   BUG描述：
        //判断是否需要查询询价商品  zhiyun.chen 2016-05-26
        boolean queryAskPriceGoods = false;
        String ownerAreaShoppingStoreId = null;//区域商城Id
        String goodsScope = ApplicationPropertiesUtils.getGoodsScope();//商品查询的范围 By QiHaiYang
        for (int i = 0; i < propertiesFilterList.size(); i++) {
            if (propertiesFilterList.get(i).getName().equals("queryAskPriceGoods") && propertiesFilterList.get(i).getValue().toString().equals("true")) {
            	queryAskPriceGoods = true;
            	propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("ownerAreaShoppingStoreId")) {
            	ownerAreaShoppingStoreId = propertiesFilterList.get(i).getValue().toString();
            	propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("goodsScope")) {
                goodsScope = propertiesFilterList.get(i).getValue().toString();
                propertiesFilterList.remove(i--);
            }

        }
        if (StringUtils.isBlank(ownerPartyId) && ownerAreaShoppingStoreId != null) {
        	queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
        	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
        }
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询
            		Matcher matcher=pattern.matcher(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if(matcher.find()){
                        if(properties.getValue().toString().length()==1){
                            queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                }else if(properties.getName().equals("carLogoId")|| properties.getName().equals("carBrandId")||properties.getName().equals("carModelId")||properties.getName().equals("carSeriesId")){
                	if (properties.getName().equals("carModelId") && (properties.getValue().equals("1") || properties.getValue().equals("-1"))) {
	                	queryStr.append(" AND ");
	                	queryStr.append(properties.getName()).append(":");
	                	queryStr.append("1");
                	} else {
                		queryStr.append(" AND ");
	                	queryStr.append("(").append(properties.getName()).append(":");
	                	queryStr.append(properties.getValue());
                		queryStr.append(" OR ").append("carModelId").append(":");
                		queryStr.append(0).append(")");
                	}
                }else if(properties.getName().equals("oem")){
                    queryStr.append(" AND ").append(properties.getName()).append(":").append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                }else if(properties.getName().equals("oemStr")){
                	String[] oemArray = properties.getValue().toString().split(",");
                	if (oemArray.length == 1) {
                		queryStr.append(" AND ").append("oem").append(":").append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex, "").replaceAll(" ", "")+"*");
                	} else {
                		queryStr.append(" AND ");
                		queryStr.append("(");
                		for (int i=0; i<oemArray.length; i++) {
                			if (i > 0) {
                				queryStr.append(" OR ");
                			}
                			queryStr.append("oem").append(":");
                			queryStr.append("*"+oemArray[i].toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                		}
                		queryStr.append(")");
                	}
                }else if (properties.getName().equals("goodsBrandNameAndGoodsName") || properties.getName().equals("goodsBrandNameAndCategory2Name")) {
                	queryStr.append(" AND ").append(properties.getName()).append(":");
                	queryStr.append(properties.getValue().toString().replaceAll(" ", "").replaceAll(IndexEntity.excludeRegex, ""));
                }else if("memberGradeList".equals(properties.getName()) && StringUtils.isNotBlank(ownerAreaShoppingStoreId) && StringUtils.isNotBlank(ownerPartyId)){
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		queryStr.append(" AND ((((");
                	} else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
                		queryStr.append(" AND (((");
                	}
                	List<Integer> gradeList=(List<Integer>) properties.getValue();
            		int index=0;
            		for (int i = 0; i < gradeList.size(); i++) {
            			if (gradeList.get(i)!=null) {
            				if (index > 0) {
            					queryStr.append(" OR (");
            				}
            				queryStr.append("categoryType").append(":").append(i+1);
            				queryStr.append(" AND ");
            				queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
            				queryStr.append(ownerPartyId + ")");
            				index++;
            			}
            		}
            		queryStr.append(" ) ");
            		queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
                	queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
                	queryStr.append(" ) ");
                	if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                		//询价商品目录范围
                    	queryStr.append(" OR ").append("askPriceScope").append(":");
                    	queryStr.append(ownerAreaShoppingStoreId).append(")");
                	}
                }else if (properties.getName().equals("brandIdList")) {
                    List<Long> brandIdList = (List<Long>) properties.getValue();
                    if (brandIdList.size() == 1) {
                        queryStr.append(" AND ").append("brandId").append(":").append(brandIdList.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i = 0; i < brandIdList.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("brandId").append(":");
                            queryStr.append(brandIdList.get(i));
                        }
                        queryStr.append(")");
                    }
                } else{
                	queryStr.append(" AND ").append(properties.getName()).append(":");
                	queryStr.append(properties.getValue());
                }

            }else if(PropertiesFilter.Type.SORT_ASC == properties.getType()){
                solrQuery.addSort(properties.getName(), SolrQuery.ORDER.asc);
            }else if(PropertiesFilter.Type.SORT_DESC == properties.getType()){
                solrQuery.addSort(properties.getName(), SolrQuery.ORDER.desc);
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
                if (properties.getName().equals("goodsName") || properties.getName().equals("goodsBrandNameAndGoodsName") || properties.getName().equals("goodsBrandNameAndCategory2Name")) {
                	solrQuery.addFacetField(properties.getName()).setFacetMinCount(1).setFacetLimit(6);
                } else {
                	solrQuery.addFacetField(properties.getName()).setFacetMinCount(1).setFacetLimit(1000);
                }
            }
        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次查询条件为："+solrQueryStr+";直接返回");
        	return null;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次查询条件为："+solrQueryStr);
        _logger_solr.info("本次查询条件为："+solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
//        //设置搜索结果返回的字段
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }

    /**
     * 旗舰店使用的搜索条件拼装 By QiHaiYang
     * @param page
     * @param propertiesFilterList
     * @param ownerPartyId
     * @return
     */
    public static SolrQuery initSolrQueryForFlagshipStore(Page<IndexEntity> page, List<PropertiesFilter> propertiesFilterList, String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 START BUG描述：
        if(propertiesFilterList==null
                ||propertiesFilterList.size()<=0){
            return null;
        }
        //提前取出非查询条件的filter
        boolean generalQuery = false;
        boolean queryAskPriceGoods = false;
        String ownerAreaShoppingStoreId = null;//区域商城Id
        boolean needAreaShippingStore = true;
        String goodsScope = ApplicationPropertiesUtils.getGoodsScope();//商品查询的范围 By QiHaiYang
        for (int i = 0; i < propertiesFilterList.size(); i++) {
            if (propertiesFilterList.get(i).getName().equals("generalQuery") && propertiesFilterList.get(i).getValue().equals("1")) {
                generalQuery = true;
                propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("queryAskPriceGoods") && propertiesFilterList.get(i).getValue().toString().equals("true")) {
                queryAskPriceGoods = true;
                propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("ownerAreaShoppingStoreId")) {
                ownerAreaShoppingStoreId = propertiesFilterList.get(i).getValue().toString();
                propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("needAreaShoppingStoreInfoFlag")) {
                if(propertiesFilterList.get(i).getValue().toString().equals("false")){
                    needAreaShippingStore = false;
                }
                propertiesFilterList.remove(i--);
            } else if (propertiesFilterList.get(i).getName().equals("goodsScope")) {
                goodsScope = propertiesFilterList.get(i).getValue().toString();
                propertiesFilterList.remove(i--);
            }
        }
        boolean hasLogin = false;//是否登录
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 END   BUG描述：
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("ids")){
                    List<String> ids = (List)properties.getValue();
                    if (ids.size() == 1) {
                        queryStr.append(" AND ").append("id").append(":").append(ids.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i=0; i<ids.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("id").append(":");
                            queryStr.append(ids.get(i));
                        }
                        queryStr.append(")");
                    }
                } else if(properties.getName().equals("keyWord")){
                    Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询
                    Matcher matcher=pattern.matcher(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if(matcher.find()){
                        if(properties.getValue().toString().length()==1){
                            queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                        queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                }else if(properties.getName().equals("carLogoId")|| properties.getName().equals("carBrandId")||properties.getName().equals("carModelId")||properties.getName().equals("carSeriesId")){
                    if (properties.getName().equals("carModelId") && (properties.getValue().equals("1") || properties.getValue().equals("-1"))) {
                        queryStr.append(" AND ");
                        queryStr.append(properties.getName()).append(":");
                        queryStr.append("1");
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(").append(properties.getName()).append(":");
                        queryStr.append(properties.getValue());
                        if (!generalQuery){
                            queryStr.append(" OR ").append("carModelId").append(":").append(0);
                        }
                        queryStr.append(")");
                    }
                }else if(properties.getName().equals("oem")){
                    queryStr.append(" AND ").append(properties.getName()).append(":").append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                }else if(properties.getName().equals("oemStr")){
                    String[] oemArray = properties.getValue().toString().split(",");
                    if (oemArray.length == 1) {
                        queryStr.append(" AND ").append("oem").append(":").append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex, "").replaceAll(" ", "")+"*");
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i=0; i<oemArray.length; i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("oem").append(":");
                            queryStr.append("*"+oemArray[i].toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }
                        queryStr.append(")");
                    }
                }else if (properties.getName().equals("goodsBrandNameAndGoodsName") || properties.getName().equals("goodsBrandNameAndCategory2Name")) {
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    queryStr.append(properties.getValue().toString().replaceAll(" ", "").replaceAll(IndexEntity.excludeRegex, ""));
                }else if("memberGradeList".equals(properties.getName()) && StringUtils.isNotBlank(ownerAreaShoppingStoreId) && StringUtils.isNotBlank(ownerPartyId)){
                    if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                        queryStr.append(" AND ((((");
                    } else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
                        queryStr.append(" AND (((");
                    }
                    List<Integer> gradeList=(List<Integer>) properties.getValue();
                    int index=0;
                    for (int i = 0; i < gradeList.size(); i++) {
                        if (gradeList.get(i)!=null) {
                            if (index > 0) {
                                queryStr.append(" OR (");
                            }
                            queryStr.append("categoryType").append(":").append(i+1);
                            queryStr.append(" AND ");
                            queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
                            queryStr.append(ownerPartyId + ")");
                            index++;
                        }
                    }
                    queryStr.append(" ) ");
                    queryStr.append(" AND ").append("areaShoppingStoreScope").append(":");
                    queryStr.append(ownerAreaShoppingStoreId + "@" + goodsScope);
                    queryStr.append(" ) ");
                    if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                        //询价商品目录范围
                        queryStr.append(" OR ").append("askPriceScope").append(":");
                        queryStr.append(ownerAreaShoppingStoreId).append(")");
                    }
                    hasLogin = true;
                }else if("memberGradeList".equals(properties.getName()) && !needAreaShippingStore && StringUtils.isNotBlank(ownerPartyId)){
                    if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                        queryStr.append(" AND ((((");
                    } else {//只查区域商城目录商品  zhiyun.chen 2016-06-13
                        queryStr.append(" AND (((");
                    }
                    List<Integer> gradeList=(List<Integer>) properties.getValue();
                    int index=0;
                    for (int i = 0; i < gradeList.size(); i++) {
                        if (gradeList.get(i)!=null) {
                            if (index > 0) {
                                queryStr.append(" OR (");
                            }
                            queryStr.append("categoryType").append(":").append(i+1);
                            queryStr.append(" AND ");
                            queryStr.append("scopeMemberPrice"+gradeList.get(i)).append(":");
                            queryStr.append(ownerPartyId + ")");
                            index++;
                        }
                    }
                    queryStr.append(" ) ");
                    queryStr.append(" ) ");
                    if (queryAskPriceGoods) {//需要查询价商品  zhiyun.chen 2016-06-13
                        //询价商品目录范围
                        queryStr.append(" OR ").append("askPriceScope").append(":");
                        queryStr.append(ownerAreaShoppingStoreId).append(")");
                    }
                    hasLogin = true;
                }else if (properties.getName().equals("brandIdList")) {
                    List<Long> brandIdList = (List<Long>) properties.getValue();
                    if (brandIdList.size() == 1) {
                        queryStr.append(" AND ").append("brandId").append(":").append(brandIdList.get(0));
                    } else {
                        queryStr.append(" AND ");
                        queryStr.append("(");
                        for (int i = 0; i < brandIdList.size(); i++) {
                            if (i > 0) {
                                queryStr.append(" OR ");
                            }
                            queryStr.append("brandId").append(":");
                            queryStr.append(brandIdList.get(i));
                        }
                        queryStr.append(")");
                    }
                }else if (properties.getName().equals("brandId")) {
                    String brandId = properties.getValue().toString();
                    //陕西区域，搜索壳牌、美孚、嘉实多，转到带英文的品牌上
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if (ownerAreaShoppingStoreId != null && (specialOilAreaStoreIds.contains(ownerAreaShoppingStoreId)) && ("585".equals(brandId) || "586".equals(brandId) || "593".equals(brandId))) {
                        if ("585".equals(brandId)) {//美孚
                            queryStr.append("1509241604271138");
                        }
                        if ("586".equals(brandId)) {//壳牌
                            queryStr.append("1509241604171137");
                        }
                        if ("593".equals(brandId)) {//嘉实多
                            queryStr.append("1509241604421139");
                        }
                    } else {
                        queryStr.append(properties.getValue());
                    }
                }else{
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    queryStr.append(properties.getValue());
                }
            }else if(PropertiesFilter.Type.SORT_ASC == properties.getType()){
                solrQuery.addSort(properties.getName(), SolrQuery.ORDER.asc);
            }else if(PropertiesFilter.Type.SORT_DESC == properties.getType()){
                solrQuery.addSort(properties.getName(), SolrQuery.ORDER.desc);
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
                if (properties.getName().equals("goodsName") || properties.getName().equals("goodsBrandNameAndGoodsName") || properties.getName().equals("goodsBrandNameAndCategory2Name")) {
                    solrQuery.addFacetField(properties.getName()).setFacetMinCount(1).setFacetLimit(30);
                } else {
                    solrQuery.addFacetField(properties.getName()).setFacetMinCount(1).setFacetLimit(1000);
                }
            }
        }

        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次查询条件为："+solrQueryStr+";直接返回");
            return null;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次查询条件为："+solrQueryStr);
        _logger_solr.info("本次查询条件为："+solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
//        //设置搜索结果返回的字段
        solrQuery.setFields("id","goodsName","brandName","goodsStyle","serialNumber","smallImagePath","middleImagePath","largeImagePath",
        		"memberPrice1","memberPrice2","memberPrice3","memberPrice4","memberPrice5","memberPrice6","memberPrice7","memberPrice8","memberPrice9","memberPrice10",
        		"memberPrice11","memberPrice12","memberPrice13","memberPrice14","memberPrice15","memberPrice16","memberPrice17","memberPrice18","memberPrice19","memberPrice20","memberPrice21","memberPrice22",
        		"memberPrice23","memberPrice24","memberPrice25","memberPrice26","memberPrice27","memberPrice28",
        		"memberPrice29","memberPrice30","memberPrice31","memberPrice32","memberPrice33","memberPrice34",
        		"memberPrice35","memberPrice36","memberPrice37","memberPrice38","memberPrice39","memberPrice40","memberPrice41","memberPrice42",
        		"memberPrice43","memberPrice44","memberPrice45","memberPrice46","memberPrice47","memberPrice48","memberPrice49","memberPrice50",
        		"memberPrice51","memberPrice52","memberPrice53","memberPrice54","memberPrice55","memberPrice56","memberPrice57","memberPrice58","memberPrice59","memberPrice60",
        		"memberPrice61","memberPrice62","memberPrice63","memberPrice64","memberPrice65","memberPrice66","memberPrice67","memberPrice68","memberPrice69","memberPrice70",
        		"memberPrice71","memberPrice72","memberPrice73","memberPrice74","memberPrice75","memberPrice76","memberPrice77","memberPrice78","memberPrice79","memberPrice80",
        		"memberPrice81","memberPrice82","memberPrice83","memberPrice84","memberPrice85","memberPrice86","memberPrice87","memberPrice88","memberPrice89","memberPrice90",
        		"memberPrice91","memberPrice92","memberPrice93","memberPrice94","memberPrice95","memberPrice96","memberPrice97","memberPrice98","memberPrice99","memberPrice100",
        		"memberPrice101","memberPrice102","memberPrice103","memberPrice104","memberPrice105","memberPrice106","memberPrice107","memberPrice108","memberPrice109","memberPrice110",
        		"memberPrice111","memberPrice112","memberPrice113","memberPrice114","memberPrice115","memberPrice116","memberPrice117","memberPrice118","memberPrice119","memberPrice120",
        		"memberPrice121","memberPrice122","memberPrice123","memberPrice124","memberPrice125","memberPrice126","memberPrice127","memberPrice128","memberPrice129",
        		"categoryType",
        		"oemAlias","promotionTitle","carBrandAndSeries","goodsRemark","goodsNameDisplay","askPriceScope","referencePriceFor4S","category2Id","productId",
        		"jyjAreaId","jyjAreaName","jyjProductType","jyjGeneral","jyjApplicableModel", "jyjShoppingPrice","goodsSource","jyjGoodsNameInfo","supplierId","brandId","jyjGoodsType"
        		);
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }


}
