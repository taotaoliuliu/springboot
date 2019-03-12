package com.aebiz.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestModel implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String mobile;
	
	private Map<String,Integer> skuMap=new HashMap<>();
		

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "TestModel [name=" + name + ", mobile=" + mobile + "]";
	}

	public Map<String, Integer> getSkuMap() {
		return skuMap;
	}

	public void setSkuMap(Map<String, Integer> skuMap) {
		this.skuMap = skuMap;
	}

	
	
	
	
}
