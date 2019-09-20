package com.aebiz.controller.decoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.aop.Servicelock;
import com.aebiz.common.base.cotroller.BaseController;
import com.aebiz.common.base.service.BaseService;
import com.aebiz.common.websocket2.WebSocketServer;
import com.aebiz.entity.Ad;
import com.aebiz.entity.decoration.Template;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.service.ProductMainService;
import com.aebiz.service.TemplateService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisPool;
import scala.reflect.internal.Trees.Super;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/template") 
public class TemplateController extends BaseController<Template> {
	


	@Autowired
	private TemplateService myService;
	
	private String templatePath="productMain";
	
	@PostConstruct
	public void init(){
		init(myService,templatePath);
	}
	

	private Logger logger = LoggerFactory.getLogger(TemplateController.class);
    

    

  
    
    
    
    
    
    
    
    

}
