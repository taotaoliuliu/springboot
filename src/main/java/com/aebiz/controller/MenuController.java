package com.aebiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.base.cotroller.BaseController;
import com.aebiz.entity.Menu;
import com.aebiz.entity.User;
import com.aebiz.service.MenuService;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/sys/menu") 
public class MenuController extends BaseController<Menu> {

  
	@Autowired
	private MenuService myService;
	
	private String templatePath="menu";
	
	@PostConstruct
	public void init(){
		init(myService,templatePath);
	}
 
	
	@RequestMapping("/user")
	@ResponseBody
	public Map<String,List<Menu>> getMyListMenu(){
		Map<String,List<Menu>> map =new HashMap<>();
		
		User user =new User();
		
		List<Menu> menuList = myService.getListByUser(user);
		
		map.put("menuList", menuList);
		return map;
	}
    

}
