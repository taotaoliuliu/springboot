package com.aebiz.entity;

import java.io.Serializable;

import com.aebiz.common.base.model.BaseModel;

/**
 * 用户实体类
 * @date 2016/8/12
 */
public class User extends BaseModel{
    private int id;
    private String loginName;
    private String username;
    private String password;
	private String locked;
	
	
    

    public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
