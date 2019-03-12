package com.aebiz.common.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.exception.BusinessException;
import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.seckill.cache.MiaoshaHandlingListCache;
import com.aebiz.common.seckill.message.Message;
import com.aebiz.common.seckill.message.MessageTrunk;
import com.aebiz.common.seckill.message.MiaoshaRequestMessage;
import com.aebiz.common.seckill.model.KillResult;
@Service
public class KillService {

	@Autowired
	private MiaoshaHandlingListCache miaoshaHandlingListCache;
	@Autowired
	private MessageTrunk messageTrunk;
	
	public KillResult miaosha(String mobile, String productUuid)
	{
		
		KillResult result =new KillResult();
		
		result.setCode(1);
		
		// 先看抢购是否已经结束了
		/*if (miaoshaFinishCache.isFinish(goodsRandomName))
		{
			throw new BusinessException("您已经提交抢购，正在处理中");
		}*/

		// 先限流
		//goodsBuyCurrentLimiter.doLimit(goodsRandomName, "啊呀，没挤进去");
		
		
		//1 先看抢购是否已经结束了 已下单为准
		

		//2 该用户是否已经得到了token  如果已经得到了 则直接去下单页面   没得到 
		

		//3   判断是否处理中(是否在处理列表中) 如果在 说明正在排队中....
		if (miaoshaHandlingListCache.isInHanleList(mobile, productUuid))
		{
			result.setCode(3);
			//throw new BusinessException("您已经提交过抢购，如果抢购成功请下单，否则耐心等待哦...");
		}
		MiaoshaRequestMessage mrm=new MiaoshaRequestMessage();
		
		mrm.setProductUuid(productUuid);
		mrm.setMobile(mobile);

		// 请求消息推入处理队列，结束
		Message message = new Message(CommonConstant.MESSAGE_TYPE,mrm);
		messageTrunk.put(message);

		// 加入正在处理列表
		miaoshaHandlingListCache.add2HanleList(mobile, productUuid);
		
		
		return result;

	}
}
