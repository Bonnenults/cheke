package com.autozi.cheke.user.entity;

import org.apache.commons.lang.StringUtils;

import com.autozi.common.dal.entity.IdEntity;


public class Role extends IdEntity {
    private static final long serialVersionUID = -5822516868712998458L;


    public static int STATUS_NORMAL = 1;
    public static int STATUS_DEL = -1;

    private String name;
    private String description;
    private Integer status;
    private Long partyId;

    private String systemRolesStr;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }


    public String getSystemRolesStr() {
        return systemRolesStr;
    }

    public void setSystemRolesStr(String systemRolesStr) {
        this.systemRolesStr = systemRolesStr;
    }

    public String[] getSystemRoles() {
        if (this.systemRolesStr != null) {
            return this.systemRolesStr.split(",");
        } else {
            return new String[0];
        }
    }

    public void setSystemRoles(String[] roles) {
        if (roles != null) {
            this.systemRolesStr = StringUtils.join(roles, ",");
        } else {
            this.systemRolesStr = null;
        }
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Role) {
            Role another = (Role) obj;
            long anotherId = another.getId() != null ? another.getId() : 0;
            long thisId = this.getId() != null ? this.getId() : 0;
            return anotherId == thisId;
        } else {
            return false;
        }
    }
}
