package com.aebiz.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class SystemConfig {

	
	
	/**
	 * 检测恶意用户，多少秒被出现多少次请求
	 */
	@Value("${user_black_times}")
	private int userBlackTimes;

	/**
	 * 检测恶意用户，多少秒被出现多少次请求
	 */
	@Value("${user_black_seconds}")
	private int userBlackSeconds;

	public int getUserBlackTimes() {
		return userBlackTimes;
	}

	public void setUserBlackTimes(int userBlackTimes) {
		this.userBlackTimes = userBlackTimes;
	}

	public int getUserBlackSeconds() {
		return userBlackSeconds;
	}

	public void setUserBlackSeconds(int userBlackSeconds) {
		this.userBlackSeconds = userBlackSeconds;
	}
	
	
	
	
	
}
