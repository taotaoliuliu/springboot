package com.aebiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.decoration.Template;
import com.aebiz.entity.decoration.TemplatePage;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.mapper.ProductMainMapper;
import com.aebiz.mapper.TemplateMapper;
import com.aebiz.mapper.TemplatePageMapper;
import com.aebiz.service.ProductMainService;
import com.aebiz.service.TemplatePageService;
import com.aebiz.service.TemplateService;
@Service
public class TemplatePageServiceImpl extends BaseServiceImpl<TemplatePage> implements TemplatePageService{
	
	
	private TemplatePageMapper mapper;
	@Autowired
	public void setMapper(TemplatePageMapper mapper) {
		
		System.out.println(this.mapper+"#################");
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
}
