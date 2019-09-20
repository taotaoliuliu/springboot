package com.aebiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aebiz.common.aop.Servicelock;
import com.aebiz.common.bean.Result;
import com.aebiz.common.websocket2.WebSocketServer;
import com.aebiz.entity.Ad;
import com.aebiz.entity.TestModel;
import com.aebiz.entity.TestModel2;
import com.aebiz.kafka.KafkaSender;
import com.aebiz.service.AdService;
import com.aebiz.service.TestService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisPool;

/**
 * @date 2016/8/9
 */

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;

	// Failed to instantiate [java.util.List]: Specified class is an interface

	// org.springframework.beans.BeanInstantiationException: Failed to
	// instantiate [java.util.List]: Specified class is an interface

	@RequestMapping(value = "/getList")
	public String getList(List<String> names, String mobile) {
		System.out.println(names);
		System.out.println(mobile);

		return "";
	}

	/**
	 * 如果 @RequestParam("names") 里面不加[] 页面传过来的js必须是个字符串用逗号分隔
	 * 
	 * 对应页面 E:\new\Spring-Boot-Demo\src\main\resources\static\html\test\add.html
	 * 里面的
	 * 
	 * @param names
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/getList2")
	public String getList2(@RequestParam("names") List<String> names, String mobile) {
		System.out.println(names);
		System.out.println(mobile);
		return "";
	}

	/**
	 * 如果 @RequestParam("names") 里面不加[] 页面传过来的js必须是个字符串用逗号分隔
	 * 
	 * 对应页面 E:\new\Spring-Boot-Demo\src\main\resources\static\html\test\add.html
	 * 里面的
	 * 
	 * @param names
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/getList22")
	public String getList22(@RequestParam("names[]") List<String> names, String mobile) {
		System.out.println(names);
		System.out.println(mobile);
		return "";
	}

	@RequestMapping(value = "/getList3")
	public String getList3(TestModel model) {

		System.out.println(model);
		return "";
	}

	
	
	
	@RequestMapping(value = "/getList4")
	public String getList4(@RequestBody TestModel model) {
		
		//ReentrantLock
		System.out.println(model);
		System.out.println(model.getSkuMap());
		return JSON.toJSONString(model);
	}

	@RequestMapping(value = "/getList5")
	public String getList5(@RequestBody List<TestModel> models, String promotionUuid) {

		System.out.println(models);
		System.out.println(promotionUuid);

		return "";
	}
	
	
	@RequestMapping(value = "/testAsync")
	public Result testAsync(){
		
		Future<Result> muchData = testService.getMuchData();
		
		
		System.out.println("DDDDDDDDDDDDDD");
		
		Result result =new Result();
		try {
			 result = muchData.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	/**
	 * {}  占位符 
	 * @param name
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/getList6/{name}/{mobile}")
	public String getList6(@PathVariable String name,@PathVariable String mobile) {

		System.out.println(name);
		System.out.println(mobile);
		return "";
	}

}
