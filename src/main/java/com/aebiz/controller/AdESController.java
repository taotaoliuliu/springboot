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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aebiz.common.aop.Servicelock;
import com.aebiz.common.websocket2.WebSocketServer;
import com.aebiz.entity.Ad;
import com.aebiz.kafka.KafkaSender;
import com.aebiz.service.AdEsService;
import com.aebiz.service.AdService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisPool;

/**
 * @date 2016/8/9
 */
@RestController
@RequestMapping("/adES") 
public class AdESController {

    private Logger logger = LoggerFactory.getLogger(AdESController.class);
    @Autowired
    private AdEsService adService;
    
    
    

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public  Ad getAd(@PathVariable String id){
        Ad user = adService.getById(id+"");
        if(user!=null){
        	//jedisPool.getResource().set(id,user.getContent());
        	return user;
        }
     
        
        return null;
    }
    
    
    
    @RequestMapping(value = "/getAd")
    public Map<String, Object> getAd(Ad ad) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ad> ad2 = adService.getAd(ad);
		map.put("data", ad2);
		map.put("size", ad2.size());
		
		return map;
	}
    @RequestMapping(value = "/getAdPage")
    public Map<String, Object> getAdPage(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize,Ad ad) 
	{			
		PageInfo<Ad> mem = adService.getAdPage(pageNum, pageSize, ad);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ad", mem);
		map.put("totalSize", mem.getTotal());
		map.put("totalPage", mem.getPages());
		return map;
	}
    @RequestMapping(value = "/addAd")
    public Map<String,String> addAd(Ad ad){
		Map<String, String> map = new HashMap<String, String>();

    	ad.setUuid(UUID.randomUUID().toString());
    	adService.addAd(ad);
    	return map;
    }
    
    @RequestMapping(value = "/updateAd")
    public Map<String,String> updateAd(Ad ad){
		Map<String, String> map = new HashMap<String, String>();
    	adService.updateAd(ad);
    	return map;
    }
    
    
    
    @RequestMapping(value = "/deleteAd")
    public Map<String,String> deleteAdd(String uuid){
		Map<String, String> map = new HashMap<String, String>();
		
    	adService.deleteAd(uuid);
    	return map;
    }
    
    
}
