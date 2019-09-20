package com.aebiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.serializer.RedisSerializer;
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
import com.aebiz.entity.MoveRules;
import com.aebiz.entity.Person;
import com.aebiz.kafka.KafkaSender;
import com.aebiz.service.AdService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.JedisPool;

/**
 * @date 2016/8/9
 */
@RestController
@RequestMapping("/ad") 
@Api(value="广告管理xxxx",description="xxxxxxxxx")
public class AdController {

    private Logger logger = LoggerFactory.getLogger(AdController.class);
    @Autowired
    private AdService adService;
   @Autowired
   private KafkaSender kafka;
    
    @Autowired
    private JedisPool jedisPool;
    
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    Person person;
    @Autowired
    MoveRules moveRules;
    
    
   /* @Autowired
    RedisTemplate<String,Ad> redisTemplate2;*/
    
    @Servicelock
    public void sayHi(){
    	System.out.println("2222");
    }
    @ResponseBody
    @RequestMapping(value = "/pool")
    public String pool(){
    	
    	Ad ad =new Ad();
    	ad.setContent("刘胜利");
    	ad.setName("广告1111111111");
    	
    	
   
    	
        	
    	Set<String> keys = redisTemplate.keys("*");
    	
    	
    	redisTemplate.boundValueOps("lsl").set("liushengli");
    	
      //  SortQueryBuilder<String> builder = SortQueryBuilder.sort(key);
        
      //  builder.

      //  List<String> cks = redisTemplate.sort(builder.build());

    	
    //	redisTemplate.boundValueOps("lsl").
    	
    
    
    	redisTemplate.opsForValue().set("lslm", JSON.toJSONString(ad));
    	
    	String string2 = redisTemplate.opsForValue().get("lslm");
    	
    	
    	Ad parseObject = JSON.parseObject(string2, Ad.class);
    	
    	System.out.println(parseObject);
    	
    	System.out.println(keys);
    	
    	
    	String string = redisTemplate.boundValueOps("lsl").get();
    	
    	System.out.println(string);
    	
    	
    	
    	return jedisPool+"";
    }
    
    
    

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  Ad getAd(@PathVariable String id){
        Ad user = adService.getById(id+"");
        if(user!=null){
        	//jedisPool.getResource().set(id,user.getContent());
        	return user;
        }
     
        
        return null;
    }
    
    
    
    @ResponseBody
    @RequestMapping(value = "/getAd")
    public Map<String, Object> getAd(Ad ad) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ad> ad2 = adService.getAd(ad);
		map.put("data", ad2);
		map.put("size", ad2.size());
		
		return map;
	}
    @ResponseBody
    @RequestMapping(value = "/getAdPage")
    @ApiOperation(value="获取广告列表", notes="获取图书列表")
    public Map<String, Object> getAdPage(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize,Ad ad) 
	{			
		PageInfo<Ad> mem = adService.getAdPage(pageNum, pageSize, ad);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ad", mem);
		map.put("totalSize", mem.getTotal());
		map.put("totalPage", mem.getPages());
		
		logger.error("22222222");

		return map;
	}
    @ResponseBody
    @RequestMapping(value = "/addAd")
    public Map<String,String> addAd(Ad ad){
    	
    	System.out.println("222222222222222");
		Map<String, String> map = new HashMap<String, String>();

    	ad.setUuid(UUID.randomUUID().toString());
    	adService.addAd(ad);
    	return map;
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateAd")
    public Map<String,String> updateAd(Ad ad){
		Map<String, String> map = new HashMap<String, String>();
    	adService.updateAd(ad);
    	return map;
    }
    
    
    
    @ResponseBody
    @RequestMapping(value = "/deleteAd")
    public Map<String,String> deleteAdd(String uuid){
		Map<String, String> map = new HashMap<String, String>();
		
    	adService.deleteAd(uuid);
    	return map;
    }
    
    @ResponseBody
    @RequestMapping(value = "/addAdTOKafKa")
    public Map<String,String> addAdTOKafKa(String uuid){
		Map<String, String> map = new HashMap<String, String>();
		
        Ad ad = adService.getById(uuid);
        
        if(ad!=null){
        	kafka.sendChannelMess("addAd", JSON.toJSONString(ad));
        	map.put("rsp", "success");

        }
        else{
        	map.put("rsp", "fail");
        }

		
		
		
    	return map;
    }
    
    @ResponseBody
    @RequestMapping(value = "/sendMsg")
    public String sendMsg(){
    	
		WebSocketServer.sendInfo("秒杀成功","1" );//推送给前台

    	return "aaaa";
    }
    
    @ResponseBody
    @RequestMapping(value = "/testError")
    public String testError(String name){
    	
    	adService.testError(name);
    	int aa=1/0;

    	return null;
    }
    
    @RequestMapping(value = "/testyml")
    public void test(){    	
    	System.out.println(person);
    	System.out.println(moveRules);
    }
    
    
  
    
    
    
    
    
    
    
    

}
