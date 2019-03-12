package com.aebiz.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.exception.BusinessException;
import com.aebiz.entity.Ad;
import com.aebiz.mapper.AdMapper;
import com.aebiz.service.AdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class AdServiceImpl implements AdService{

	
	@Autowired
	private AdMapper mapper;
	@Override
	public PageInfo<Ad> getAdPage(int pageNum, int pageSize , Ad ad) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Ad> list = mapper.getAd(ad);
		PageInfo<Ad> page = new PageInfo<Ad>(list);
		return page;
	}
	@Override
	public void addMem(Ad ad) {
		String uuid = UUID.randomUUID().toString();
		ad.setUuid(uuid);
		mapper.addAd(ad);
	}
	@Override
	public Ad getById(String uuid) {
		return mapper.getById(uuid);
	}
	@Override
	public void updateAd(Ad ad) {
		mapper.updateAd(ad);
		
	}
	@Override
	public void addAd(Ad ad) {
		mapper.addAd(ad);
		
	}
	@Override
	public void deleteAd(String uuid) {
		mapper.deleteAd(uuid);		
	}
	@Override
	public List<Ad> getAd(Ad ad) {
		return mapper.getAd(ad);
	}
	@Override
	public void testError(String name) {
		
		//throw new BusinessException("33","dddddd");
		
	}

}
