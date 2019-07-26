package com.aebiz.service;

import java.util.List;

import com.aebiz.entity.Ad;
import com.github.pagehelper.PageInfo;

public interface AdRedisService {

	
	public PageInfo<Ad> getAdPage(int pageNum, int pageSize,Ad ad);

	public Ad getById(String uuid);
	
	public void updateAd(Ad ad);
	
	public void addAd(Ad ad);
	
	public void deleteAd(String uuid);
	
	public List<Ad> getAd(Ad ad);

}
