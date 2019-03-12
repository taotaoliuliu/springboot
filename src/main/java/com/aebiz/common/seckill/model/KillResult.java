package com.aebiz.common.seckill.model;

import java.util.HashMap;
import java.util.Map;

public class KillResult {
	
	// 1 秒杀开始
	// 2 当前排队人数过多 请稍后再试
	// 3 排队中 
	// 4成功
	// 5 失败
	
	public static final int KILL_START=1;
	public static final int KILL_TOMANY=2;
	public static final int KILL_WAITING=3;
	public static final int KILL_SUCCESS=4;
	public static final int KILL_FAIL=5;
	
	private static Map<Integer,String> status =new HashMap<>();
	
	static {
		status.put(KILL_START,"秒杀开始");
		status.put(KILL_TOMANY,"当前排队人数过多 请稍后再试");
		status.put(KILL_WAITING,"排队中");
		status.put(KILL_SUCCESS,"成功");
		status.put(KILL_FAIL,"失败");
	}
	
	private int code;
	
	private String message;
	
	private String mobile;
	
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return status.get(this.code);
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
