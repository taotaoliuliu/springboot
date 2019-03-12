package com.aebiz.entity.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.seckill.model.KillResult;

public class SecKillProduct extends BaseModel {

	private String  name;
	
	private String productUuid;
	
	private String uuid;
	
	private String startTime;
	
	private String endTime;
	
	private double killPrice;
	
	@Transient
	private String productName;
	@Transient
	private double shopPrice;
	@Transient
	private List<String> listUuids =new ArrayList<>();
	@Transient
	private KillResult result;
	
	
	
	
	
	public KillResult getResult() {
		return result;
	}

	public void setResult(KillResult result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}

	public List<String> getListUuids() {
		return listUuids;
	}

	public void setListUuids(List<String> listUuids) {
		this.listUuids = listUuids;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getKillPrice() {
		return killPrice;
	}

	public void setKillPrice(double killPrice) {
		this.killPrice = killPrice;
	}

	private  int version;

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
	
}
