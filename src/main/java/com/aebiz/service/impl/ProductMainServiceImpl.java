package com.aebiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.mapper.ProductMainMapper;
import com.aebiz.service.ProductMainService;
@Service
public class ProductMainServiceImpl extends BaseServiceImpl<ProductMain> implements ProductMainService{
	
	
	private ProductMainMapper mapper;
	@Autowired
	public void setMapper(ProductMainMapper mapper) {
		
		System.out.println(this.mapper+"#################");
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
}
