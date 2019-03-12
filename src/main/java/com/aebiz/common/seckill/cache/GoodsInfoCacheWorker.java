package com.aebiz.common.seckill.cache;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.seckill.cache.base.CacheWorker;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.service.ProductMainService;

/**
 * 获取商品信息缓存工作器
 * 
 * @author dingxiangyong 2016年8月26日 上午11:17:38
 */
@Component
public class GoodsInfoCacheWorker extends CacheWorker<ProductMain>
{
	@Autowired
	private ProductMainService mainService;

	@Override
	protected ProductMain getDataWhenNoCache(String uuid)
	{
		return mainService.getById(uuid);
	}

	@Override
	protected String getKey(String uuid)
	{
		String key = MessageFormat.format(CommonConstant.RedisKey.GOODS_INFO_BY_ID, new Object[] { uuid });
		return key;
	}

	@Override
	protected int getExpireSeconds()
	{
		return CommonConstant.RedisKeyExpireSeconds.GOODS_STORE_BY_ID;
	}

}
