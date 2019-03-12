package com.aebiz.common.base.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Transient;

@MappedSuperclass
public class BaseModel implements Serializable{
	private String uuid;
	
	private String createTime;
	
	@Transient
	private String startTime;
	
	@Transient
	private String  endTime;
	
	
	
	
	
	

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public static String genUuid() {
		String str = UUID.randomUUID().toString();
		return str.replaceAll("-", "");
	}
	
}

