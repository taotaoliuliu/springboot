package com.aebiz.entity;

import java.util.List;

public class SrcDataSource {

	
	private List<String> querysql;
	
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	public List<String> getQuerysql() {
		return querysql;
	}
	public void setQuerysql(List<String> querysql) {
		this.querysql = querysql;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "SrcDataSource [querysql=" + querysql + ", driverClassName=" + driverClassName + ", url=" + url
				+ ", username=" + username + ", password=" + password + "]";
	}
	
	
	
	
}
