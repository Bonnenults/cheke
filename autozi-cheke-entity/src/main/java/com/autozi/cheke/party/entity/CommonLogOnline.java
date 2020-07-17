/**
 * 
 */
package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 *  用于记录所有的表中某个属性的修改历史，功能类似操作日志
 * 
 * @author haocheng
 *
 */
public class CommonLogOnline extends IdEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8925408953763452296L;
	private String sourceClass;
	/**订单日志**/
	public static String SOURCE_CLASS_ORDER="order";
	/**品牌日志**/
	public static String SOURCE_CLASS_BRAND="com.b2bex.goods.entity.Brand";
	/**
	 * 新增日志类型：平台商品审核驳回
	 * 不确定之前是否有此定义
	 */
	public static String SOURCE_CLASS_REJECT_GOODS="chain_reject_goods";
	/**商品日志**/
	public static String SOURCE_CLASS_GOODS="com.b2bex.goods.entity.Brand";
	
	private String sourceId;
	
	private String attribute;
	private String oldValue;
	private String newValue;  //如果newValue为-1则该条流水为订单与wms相关
    public static final String NEW_VALUE_ORDER_WMS="-1";
	
	private String attribute2;
	private String oldValue2;
	private String newValue2;
	
	private String attribute3;
	private String oldValue3;
	private String newValue3;
	
	private long userId;
	private long partyId;  //user 的冗余，以便提供同一个属性被不同主体修改的查询
	
	private Date changeDate;	
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public long getPartyId() {
		return partyId;
	}
	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getOldValue2() {
		return oldValue2;
	}
	public void setOldValue2(String oldValue2) {
		this.oldValue2 = oldValue2;
	}
	public String getNewValue2() {
		return newValue2;
	}
	public void setNewValue2(String newValue2) {
		this.newValue2 = newValue2;
	}
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	public String getOldValue3() {
		return oldValue3;
	}
	public void setOldValue3(String oldValue3) {
		this.oldValue3 = oldValue3;
	}
	public String getNewValue3() {
		return newValue3;
	}
	public void setNewValue3(String newValue3) {
		this.newValue3 = newValue3;
	}
	public String getSourceClass() {
		return sourceClass;
	}
	public void setSourceClass(String sourceClass) {
		this.sourceClass = sourceClass;
	}
}
