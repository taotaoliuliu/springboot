package com.aebiz.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "moveRules")
public class MoveRules {

	private SrcDataSource srcDataSource;
	
	
	private TargetDataSource targetDataSource;


	public SrcDataSource getSrcDataSource() {
		return srcDataSource;
	}


	public void setSrcDataSource(SrcDataSource srcDataSource) {
		this.srcDataSource = srcDataSource;
	}


	public TargetDataSource getTargetDataSource() {
		return targetDataSource;
	}


	public void setTargetDataSource(TargetDataSource targetDataSource) {
		this.targetDataSource = targetDataSource;
	}


	@Override
	public String toString() {
		return "MoveRules [srcDataSource=" + srcDataSource + ", targetDataSource=" + targetDataSource + "]";
	}
	
	
	
	
	
}
