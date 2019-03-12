package com.aebiz.common.seckill.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.seckill.cache.MiaoshaHandlingListCache;
import com.aebiz.common.seckill.cache.MiaoshaSuccessTokenCache;
import com.aebiz.common.seckill.model.KillResult;
import com.aebiz.common.seckill.service.KillService;
import com.aebiz.common.utils.DateUtil;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.service.SecKillProductService;
@Controller
@RequestMapping(value = "/kill")
public class KillController {
	@Autowired
	private KillService killService;
	
	@Autowired
	private SecKillProductService myService;
	@Autowired
	private MiaoshaSuccessTokenCache miaoshaSuccessTokenCache;
	
	@Autowired
	private MiaoshaHandlingListCache miaoshaHandlingListCache;
	
	@RequestMapping(value = "/time/now")
	@ResponseBody
	public Long time()
	{
		return System.currentTimeMillis();
	}
	
	@RequestMapping(value = "/miaosha")
	@ResponseBody
	public KillResult miaosha(String mobile, String productUuid)
	{
		
		//秒杀成功
		
		//秒杀失败
		
		//排队中
		KillResult result=killService.miaosha(mobile, productUuid);

		// 为什么要返回mobile，为了方便jmeter测试
		return result;
	}
	@RequestMapping(value = "/getResult")
	@ResponseBody
	public KillResult getResult(String mobile,String productUuid){
		
		KillResult result =new KillResult();
		//result 不能放全局内存  内存会爆
		String queryToken = miaoshaSuccessTokenCache.queryToken(mobile, productUuid);

		if(StringUtils.isNotEmpty(queryToken)){
			result.setCode(KillResult.KILL_SUCCESS);
		}
		return result;
	}
	@RequestMapping("/toKillDetail")
	public String toKillDetail(){
		return "front/detail";

	}
	
	
	@RequestMapping(value = "/{goodsId}/detail")
	@ResponseBody
	public SecKillProduct detail(@PathVariable String goodsId,String mobile)
	{
		mobile="13261242590";
		SecKillProduct product = myService.getById(goodsId);
		
		KillResult result =new KillResult();
		result.setCode(1);
		// 1 开始秒杀
    	// 2 当前排队人数过多 请稍后再试
    	// 3 排队中 
    	
    	// 4成功
    	// 5 失败
		if(product!=null){
			Date startTime = DateUtil.convertDateFromTimeStr(product.getStartTime());
			
			Date endTime = DateUtil.convertDateFromTimeStr(product.getEndTime());
			
			Date now=new Date();
			//并且缓存的isFinish 没有结束
			if(startTime.getTime()<now.getTime()&&now.getTime()<endTime.getTime()){
				String queryToken = miaoshaSuccessTokenCache.queryToken(mobile, product.getProductUuid());
				
				if(StringUtils.isNotBlank(queryToken)){
					result.setCode(4);
				}
				else {
					
					boolean inHanleList = miaoshaHandlingListCache.isInHanleList(mobile, product.getProductUuid());
					if(inHanleList){
						result.setCode(3);

					}
				}
				
			}
			
		}
		
		product.setResult(result);
		return product;

	}
	
	
	
	
	
	
}
