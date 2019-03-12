package com.aebiz.common.bean;

import java.io.Serializable;

public class Result implements Serializable {
	public static final String UNKNOWN_ERRORCODE = "000001";
	public static final String UN_LOGIN_ERRORCODE = "000002";
	public static final String PAGE_NOT_FOUND_ERRORCODE = "000404";
	public static final String PAGE_ERROR_FOUND_ERRORCODE = "000500";
	protected static final long serialVersionUID = 5325521982124983937L;
	
	public static final String SUCCESS = "1";
	public static final String FAIL = "2";

	private String code;
	private String data;
	/**
	 * 消息
	 */
	private String retMessage = "";
	
	private String retStatus="1";

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return this.data;
	}



	public String getRetMessage() {
		return retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public String getRetStatus() {
		return retStatus;
	}

	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}