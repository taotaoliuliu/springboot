package com.aebiz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.aebiz.entity.Ad;
import com.aebiz.service.AdRedisService;
import com.github.pagehelper.PageInfo;

/**
 * 这种只适合@field 标记的情况
 * @author 55401
 *
 */
@Service
public class AdRedisServiceImpl implements AdRedisService{
	public static final String AD_UUIDS="ad_uuids";
	
	@Resource
	 RedisTemplate<String,Ad> redisTemplate;
	 
	 
	 @Resource(name = "redisTemplate")
	 ZSetOperations<String, String> zSetOperations;

	@Override
	public PageInfo<Ad> getAdPage(int pageNum, int pageSize , Ad ad) {
		
		System.out.println(redisTemplate);
		Long zCard = zSetOperations.zCard(AD_UUIDS);
		
			int start=(pageNum-1)*pageSize;
			
			int end = pageNum * pageSize - 1;
			Set<String> reverseRangeByScore = zSetOperations.reverseRange(AD_UUIDS, start, end);
			
		//	Set<TypedTuple<String>> reverseRangeByScoreWithScores = zSetOperations.reverseRangeByScoreWithScores(AD_UUIDS, pageNum, pageSize);
		
		List<Ad> listAd =new ArrayList<Ad>();
		
		for(String uuid:reverseRangeByScore){
			
			String adKey="ad_"+uuid;	

			Ad ad2 = redisTemplate.boundValueOps(adKey).get();
			listAd.add(ad2);
		}
		
		PageInfo<Ad> page=new PageInfo<>();
		
		page.setList(listAd);
		page.setTotal(zCard);
		
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		return page;
	}
	@Override
	public Ad getById(String uuid) {
		
		String adKey="ad_"+uuid;	

		Ad ad = redisTemplate.boundValueOps(adKey).get();
		
		return ad;
	}
	@Override
	public void updateAd(Ad ad) {
		
		String adKey="ad_"+ad.getUuid();	

		redisTemplate.boundValueOps(adKey).set(ad);
	
		
	}
	@Override
	public void addAd(Ad ad) {
		String uuid = UUID.randomUUID().toString();
		
		ad.setUuid(uuid);
		
		String adKey="ad_"+uuid;	
		redisTemplate.boundValueOps(adKey).set(ad);
		
	//	stringRedisTemplate.boundZSetOps(AD_UUIDS).add(uuid, new Date().getTime());//得分 一般用时间
		
		zSetOperations.add(AD_UUIDS, uuid, new Date().getTime());

		
		
	}
	@Override
	public void deleteAd(String uuid) {
		
		String adKey="ad_"+uuid;	
		
		redisTemplate.delete(adKey);
		
		zSetOperations.remove(AD_UUIDS, uuid);
	}
	@Override
	public List<Ad> getAd(Ad ad) {
		
		List<Ad> range = redisTemplate.boundListOps("zhansan").range(1, 2);
		
		return range;
	}

}
