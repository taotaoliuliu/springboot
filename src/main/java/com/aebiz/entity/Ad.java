package com.aebiz.entity;

import java.io.Serializable;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.exception.MalciousException;

/**
 * 用户实体类
 * @date 2016/8/12
 */
public class Ad extends BaseModel{
	
	/**
	 * 商品skuUuid
	 */
	private String skuUuid;
	
	
	/**
	 * 广告名称
	 */
	private String name;
	
	/**
	 * 广告内容
	 */
	private String content;


	

	public String getSkuUuid() {
		return skuUuid;
	}


	public void setSkuUuid(String skuUuid) {
		this.skuUuid = skuUuid;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "Ad [skuUuid=" + skuUuid + ", name=" + name + ", content=" + content + "]";
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("aaa");
		
	save();


	}
	
	
	public static void save() throws MalciousException{
		System.out.println("22222");
		throw new MalciousException(207, "您的请求过于频繁");


	}
	
	
}
