package com.aebiz.entity;

import java.io.Serializable;

public class TestModel2 implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String age;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestModel2 [age=" + age + "]";
	}
	
	
	
	

	
	
}
