package com.aebiz.common.seckill.message;

import java.io.Serializable;

/**
 * 请求
 * @author ThinkPad
 *
 */
public class MiaoshaRequestMessage implements Serializable {

	
	
	
	private String mobile;
	
	private String productUuid;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}
	
	
	
	
}
