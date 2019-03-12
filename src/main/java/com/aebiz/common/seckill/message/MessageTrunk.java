package com.aebiz.common.seckill.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.utils.RedisUtil;

/**
 * 消息总线
 */
@Service
public class MessageTrunk {
	
	@Autowired
	private RedisUtil redisUtil;
	/**
	 * 推送消息
	 * 
	 * @param message
	 */
	public void put(Message message)
	{
		redisUtil.rpush(message.getKey().toString(), message, 0);
	}
	
	
}
