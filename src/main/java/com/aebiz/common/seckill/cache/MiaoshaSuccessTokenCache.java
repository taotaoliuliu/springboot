package com.aebiz.common.seckill.cache;

import java.text.MessageFormat;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.aebiz.common.seckill.CommonConstant;
import com.aebiz.common.utils.RedisUtil;

@Component
public class MiaoshaSuccessTokenCache {
	
	 @Autowired
	 private RedisUtil redisUtil;
	
	public String  genToken(String mobile , String productUuid){
		String key = getKey(mobile, productUuid);
		String token = getToken();
		redisUtil.set(key + token, System.currentTimeMillis());
		return token;
	}
	
	
	
	
	
	

	protected String getKey(String mobile, String goodsRandomName)
	{
		String key = MessageFormat.format(CommonConstant.RedisKey.MIAOSHA_SUCCESS_TOKEN,
				new Object[] { mobile, goodsRandomName });
		return key;
	}







	public String getToken() {

		return UUID.randomUUID().toString();
	}







	public String queryToken(String mobile, String productUuid) {
		Set<String> keys = redisUtil.keys(getKey(mobile, productUuid) + "*");
		if (!CollectionUtils.isEmpty(keys))
		{
			String key = keys.iterator().next();

			return key.substring(key.lastIndexOf("_") + 1);
		}
		return StringUtils.EMPTY;

		
	}

}
