package com.aebiz.common.seckill.cache;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.seckill.cache.base.CurrentLimiter;
import com.aebiz.common.utils.RedisUtil;
import com.aebiz.service.ProductMainService;

/**
 * 商品购买限流器
 * 
 * @category 商品购买限流器
 * @author xiangyong.ding@weimob.com
 * @since 2017年3月16日 下午1:54:05
 */
@Component
public class GoodsBuyCurrentLimiter extends CurrentLimiter<String>
{
	@Autowired
	private ProductMainService mainService;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	protected String getLimiterName(String uuid)
	{
		String key = MessageFormat.format(CommonConstant.RedisKey.GOODS_STORE_BY_ID, new Object[] { uuid });
		return key;
	}

	@Override
	protected int getLimit(String uuid)
	{
		return mainService.getById(uuid).getStore()
				* CommonConstant.CurrentLimitMultiple.GOODS_BUY;
	}

	@Override
	protected int getCurrentLimit()
	{
		return redisUtil.llen(CommonConstant.RedisKey.MESSAGE_TYPE).intValue();
	}

}
