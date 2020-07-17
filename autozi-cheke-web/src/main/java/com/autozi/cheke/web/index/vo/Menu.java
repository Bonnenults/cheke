package com.autozi.cheke.web.index.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {
    public static final String PREFIX = "ROLE_";
    public static final int TYPE_MENU = 1;
    public static final int TYPE_AUTHORITY = 1 << 1;

    public static final int TYPE_ALL = TYPE_MENU | TYPE_AUTHORITY;

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 菜单名称
     */
    private String text;
    /**
     * 菜单链接
     */
    private String url;

    /**
     * 能力
     * @see com.b2bex.party.entity.Configuration
     */
    /**
     * 父ID
     */
    private int parentId;
    /**
     * 菜单子集合
     */
    private List<Menu> items = new ArrayList<Menu>();
    /**
     * 系统角色
     */
    private String systemRole;
    /**
     * 需要使用HTTPS访问
     */
    private boolean sslRequired = false;
    /**
     * 图标
     */
    private String icon;
    /**
     * 设置角色时显示的名称（可为空），如果为空则设置角色时显示text
     */
    private String roleDisplayText;

    private int type;

    public Menu(int id, String text, String url, int parentId) {
        this(id, text, null, url, parentId, PREFIX + id);
    }

    private Menu(int id, String text, String roleDisplayText, String url, int parentId, String systemRole) {
        this(id, text, roleDisplayText, url, parentId, systemRole, TYPE_ALL);
    }

    public Menu(int id, String text, String url, int parentId, int type) {
        this(id, text, null, url, parentId, PREFIX + id, type);
    }

    public Menu(int id, String text, String roleDisplayText, String url, int parentId,int type) {
    	this(id, text, roleDisplayText, url, parentId, PREFIX + id, type);
    }

    public Menu(int id, String text, String roleDisplayText, String url, int parentId, String systemRole, int type) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.parentId = parentId;
        this.systemRole = systemRole;
        this.icon = "icon" + id;
        this.type = type;
        if (roleDisplayText != null) {
        	this.roleDisplayText = roleDisplayText;
        } else {
        	this.roleDisplayText = text;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getItems() {
        return items;
    }

    public void setItems(List<Menu> items) {
        this.items = items;
    }

    public String getSystemRole() {
        return systemRole;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isSslRequired() {
        return sslRequired;
    }

    public void setSslRequired(boolean sslRequired) {
        this.sslRequired = sslRequired;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean checkType(int type) {
//        return type == (type & this.type);
    	return type == this.type;
    }

    public Menu copy() {
        return new Menu(id, text, roleDisplayText, url, parentId, systemRole, type);
    }

	public String getRoleDisplayText() {
		return roleDisplayText;
	}

	public void setRoleDisplayText(String roleDisplayText) {
		this.roleDisplayText = roleDisplayText;
	}

}
