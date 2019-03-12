package com.aebiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aebiz.common.aop.Servicelock;
import com.aebiz.common.websocket2.WebSocketServer;
import com.aebiz.entity.Ad;
import com.aebiz.kafka.KafkaSender;
import com.aebiz.service.AdService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisPool;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/main") 
public class MainController {

    
    @RequestMapping(value = "/toMain")
    public String testError(String name){
    	
    //	int aa=1/0;

    	return "main";
    }
    
    
  
    
    
    
    
    
    
    
    

}
