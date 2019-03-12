package com.aebiz.entity.product;

import java.util.ArrayList;
import java.util.List;

import com.aebiz.common.base.model.BaseModel;

public class ProductMain extends BaseModel {

		
	private int store;
	
	
	private String name;
	
	
	private double shopPrice;
	
	private String  categoryUuid;
	
	
	private List<String> listUuids =new ArrayList<>();
	
	
	
	
	public List<String> getListUuids() {
		return listUuids;
	}

	public void setListUuids(List<String> listUuids) {
		this.listUuids = listUuids;
	}

	
	public double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}


	/**
	 * 随机名称，该名称只有在抢购开始后才生成
	 */
	private String randomName;


	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryUuid() {
		return categoryUuid;
	}

	public void setCategoryUuid(String categoryUuid) {
		this.categoryUuid = categoryUuid;
	}

	public String getRandomName() {
		return randomName;
	}

	public void setRandomName(String randomName) {
		this.randomName = randomName;
	}
	
	
	
	
	
	
	
}