package com.aebiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.decoration.Component;
import com.aebiz.entity.decoration.Template;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.mapper.ComponentMapper;
import com.aebiz.mapper.ProductMainMapper;
import com.aebiz.mapper.TemplateMapper;
import com.aebiz.service.ComponentService;
import com.aebiz.service.ProductMainService;
import com.aebiz.service.TemplateService;
@Service
public class ComponentServiceImpl extends BaseServiceImpl<Component> implements ComponentService{
	
	
	private ComponentMapper mapper;
	@Autowired
	public void setMapper(ComponentMapper mapper) {
		
		System.out.println(this.mapper+"#################");
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
}
