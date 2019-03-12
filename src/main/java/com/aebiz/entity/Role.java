package com.aebiz.entity;

import com.aebiz.common.base.model.BaseModel;

public class Role  extends BaseModel{

	
	
	private String id;
	
	private String name;
	
	private int flag;
	
	private String menus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	
	
	
	
}
