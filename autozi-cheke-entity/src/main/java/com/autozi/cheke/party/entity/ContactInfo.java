package com.autozi.cheke.party.entity;

import com.autozi.common.dal.format.Column;
import com.autozi.common.dal.entity.IdEntity;
import com.autozi.common.dal.format.persistence;
/**
 * 企业联系人
 * @author LITONGKE
 *
 */
public class ContactInfo extends IdEntity{
	@Column(ColumnType=persistence.TEMPORARY)
	private static final long serialVersionUID = 1134631258013349032L;
	
	private String legalName;//法人/厂长/总经理
	private String legalPhone;//电话
	private String cfoName;//财务总监
	private String cfoPhone;//电话
	private String ctoName;//技术经理
	private String ctoPhone;//电话
	
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLegalPhone() {
		return legalPhone;
	}
	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}
	public String getCfoName() {
		return cfoName;
	}
	public void setCfoName(String cfoName) {
		this.cfoName = cfoName;
	}
	public String getCfoPhone() {
		return cfoPhone;
	}
	public void setCfoPhone(String cfoPhone) {
		this.cfoPhone = cfoPhone;
	}
	public String getCtoName() {
		return ctoName;
	}
	public void setCtoName(String ctoName) {
		this.ctoName = ctoName;
	}
	public String getCtoPhone() {
		return ctoPhone;
	}
	public void setCtoPhone(String ctoPhone) {
		this.ctoPhone = ctoPhone;
	}
	
	
}
