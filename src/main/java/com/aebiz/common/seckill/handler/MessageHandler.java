package com.aebiz.common.seckill.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.exception.BusinessException;
import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.seckill.cache.GoodsRedisStoreCache;
import com.aebiz.common.seckill.cache.MiaoshaSuccessTokenCache;
import com.aebiz.common.seckill.message.MessageTrunk;
import com.aebiz.common.seckill.message.MiaoshaRequestMessage;

@Service
public class MessageHandler extends AbstarctMessageHandler<MiaoshaRequestMessage> {
	private static Logger log = LoggerFactory.getLogger(MessageHandler.class);

	
	public MessageHandler() {
		super(CommonConstant.MESSAGE_TYPE, MiaoshaRequestMessage.class);
	}

	@Autowired
	protected MessageTrunk messageTrunk;
	@Autowired
	protected GoodsRedisStoreCache goodsRedisStoreCache;
	@Autowired
	protected MiaoshaSuccessTokenCache miaoshaSuccessTokenCache;

	@Override
	public void handler(MiaoshaRequestMessage message) {
		
		
		long startTimeN = System.currentTimeMillis();
		long startTime1 = System.currentTimeMillis();
		// 查看请求用户是否在黑名单中
		/*if (userBlackListCache.isIn(message.getMobile()))
		{
			//logger.error(message.getMobile() + "检测为黑名单用户，拒绝抢购");
			return;
		}
		// logger.error("1耗时：" + (System.currentTimeMillis() - startTime));
		long startTime2 = System.currentTimeMillis();
		// 先看抢购是否已经结束了
		if (miaoshaFinishCache.isFinish(message.getProductUuid()))
		{
			//logger.error("抱歉，您来晚了，抢购已经结束了");
			return;
		}*/
		// logger.error("2耗时：" + (System.currentTimeMillis() - startTime));
		long startTime3 = System.currentTimeMillis();
		// 先减redis库存
		if (!goodsRedisStoreCache.decrStore(message.getProductUuid()))
		{
			// 减库存失败
			
			//应该从
			throw new BusinessException("占redis名额失败，等待重试");
		}
		// logger.error("3耗时：" + (System.currentTimeMillis() - startTime));
		long startTime4 = System.currentTimeMillis();
		// 减库存成功：生成下单token，并存入redis供前端获取
		String token = miaoshaSuccessTokenCache.genToken(message.getMobile(), message.getProductUuid());
		// logger.error("4耗时：" + (System.currentTimeMillis() - startTime));
		long startTime5 = System.currentTimeMillis();
		log.info(token);
		
		
		//得到token之后就把删除inhandlelist 
		
	}

	@Override
	public void handlerFail(MiaoshaRequestMessage t) {
		
	}
	
	
	
}
