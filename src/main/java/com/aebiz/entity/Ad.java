package com.aebiz.entity;

import java.io.Serializable;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.exception.MalciousException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户实体类
 * @date 2016/8/12
 */

@ApiModel(value = "广告实体类")
public class Ad extends BaseModel{
	
	public static final String INDEX_NAME="ad";
	
	public static final String TYPE_NAME="ad";

	
	/**
	 * 商品skuUuid
	 */
	private String skuUuid;
	
	
	/**
	 * 广告名称
	 */
	@ApiModelProperty(value = "广告名称")
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
