package com.aebiz.common.seckill.cache;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.utils.RedisUtil;

/**
 * 秒杀正在处理请求列表
 * 
 */
@Component
public class MiaoshaHandlingListCache
{
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 增加到处理列表
	 */
	public void add2HanleList(String mobile, String productUuid)
	{
		redisUtil.hset(getKey(productUuid), mobile, mobile);
	}

	/**
	 * 增加到处理列表

	 */
	public void removeFromHanleList(String mobile, String productUuid)
	{
		redisUtil.hdel(getKey(productUuid), mobile);
	}

	/**
	 * 是否在正在处理列表中
	 */
	public boolean isInHanleList(String mobile, String productUuid)
	{
		return redisUtil.hget(getKey(productUuid), mobile, String.class) != null;
	}

	private String getKey(String productUuid)
	{
		
		String key = MessageFormat.format(CommonConstant.RedisKey.MIAOSHA_HANDLE_LIST,
				new Object[] { productUuid });
		return key;
	}

}
