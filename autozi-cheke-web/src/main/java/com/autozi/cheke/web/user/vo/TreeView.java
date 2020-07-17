package com.autozi.cheke.web.user.vo;

import java.util.List;
import java.util.Map;

public class TreeView {
	 public static final String OPEN_STATE = "open";
	    public static final String OPEN_CLOSED = "closed";

	    private Long id;
	    private String text = "";
	    private boolean checked;
	    private String state = OPEN_CLOSED;
	    private List<TreeView> children;
	    private Long parentId;
	    private int areaLevel;

	    public TreeView() {
	    }


	    public TreeView(Long id, String text, boolean checked, String state) {
	        super();
	        this.id = id;
	        this.text = text;
	        this.checked = checked;
	        this.state = state;
	    }


	    public TreeView(Long id, String text, boolean checked, String state,
						List<TreeView> children) {
	        super();
	        this.id = id;
	        this.text = text;
	        this.checked = checked;
	        this.state = state;
	        this.children = children;
	    }


	    public TreeView(Long id, String text, boolean checked, String state,
						Map<String, Object> attributes) {
	        super();
	        this.id = id;
	        this.text = text;
	        this.checked = checked;
	        this.state = state;
	        this.attributes = attributes;
	    }


	    private Map<String, Object> attributes;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    public boolean isChecked() {
	        return checked;
	    }

	    public void setChecked(boolean checked) {
	        this.checked = checked;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public List<TreeView> getChildren() {
	        return children;
	    }

	    public void setChildren(List<TreeView> children) {
	        this.children = children;
	    }

	    public void setAttributes(Map<String, Object> attributes) {
	        this.attributes = attributes;
	    }

	    public Map<String, Object> getAttributes() {
	        return attributes;
	    }


	    public Long getParentId() {
	        return parentId;
	    }


	    public void setParentId(Long parentId) {
	        this.parentId = parentId;
	    }


		public int getAreaLevel() {
			return areaLevel;
		}


		public void setAreaLevel(int areaLevel) {
			this.areaLevel = areaLevel;
		}
}
