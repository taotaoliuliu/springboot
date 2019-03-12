package com.aebiz.common.seckill.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aebiz.common.seckill.cache.GoodsRedisStoreCache;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.service.ProductMainService;
import com.aebiz.service.SecKillProductService;


/**
 * 商品redis库存初始化任务
 * 
 * @category @author xiangyong.ding@weimob.com
 * @since 2017年4月9日 上午11:47:50
 */
@Component
public class GoodsRedisStoreInitTask
{
	@Autowired
	private ProductMainService mainService;
	
	@Autowired
	private SecKillProductService killProductService;

	@Autowired
	private GoodsRedisStoreCache goodsRedisStore;

	/**
	 * 每隔2分钟触发一次
	 */
	 //@Scheduled(cron = "0 0/2 * * * ? ")
	public void doInit()
	{
		System.out.println("db to redis");
		
		//是查询秒杀商品 而不是 全部
		
		SecKillProduct kill =new SecKillProduct();
		List<SecKillProduct> list = killProductService.getByCondition(kill);
		for (SecKillProduct item : list)
		{
			goodsRedisStore.doInit(item);

		}
	}

}
