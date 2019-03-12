package com.aebiz.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.base.cotroller.BaseController;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.service.SecKillProductService;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/secKillProductMain") 
public class SecKillProductController extends BaseController<SecKillProduct> {
	


	@Autowired
	private SecKillProductService myService;
	
	private String templatePath="secKillProduct";
	
	@PostConstruct
	public void init(){
		init(myService,templatePath);
	}

	
}
