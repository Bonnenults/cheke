package com.autozi.cheke.web.user.vo;


import com.autozi.cheke.util.web.BaseView;

public class RoleView extends BaseView {
	private String name;
    private String description;
    private Integer status;
    private String systemRolesStr;// 权限列表【ROLE_XXX1,ROLE_XXX2】
    private Long partyId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSystemRolesStr() {
		return systemRolesStr;
	}
	public void setSystemRolesStr(String systemRolesStr) {
		this.systemRolesStr = systemRolesStr;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
    
    
}
