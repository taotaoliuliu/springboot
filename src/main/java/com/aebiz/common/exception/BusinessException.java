package com.aebiz.common.exception;

/**
 * 请求频繁异常
 * @author CZH
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -3992962807763740333L;
	private String errorCode;
	private String errorInfo;

	public BusinessException() {
	}

	public BusinessException(String errorCode, String errorInfo) {
		this.errorCode = errorCode;
		this.errorInfo = errorInfo;
	}

	public BusinessException(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return this.errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
