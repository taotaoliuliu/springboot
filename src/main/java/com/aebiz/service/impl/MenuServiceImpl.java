package com.aebiz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.Menu;
import com.aebiz.entity.User;
import com.aebiz.mapper.MenuMapper;
import com.aebiz.mapper.SecKillProductMapper;
import com.aebiz.service.MenuService;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService{
	@Autowired
	private MenuMapper mapper;
	
	@Autowired
	public void setMapper(MenuMapper mapper) {
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
	
	@Override
	public List<Menu> getListByUser(User user) {
		List<Menu> levelOne =new ArrayList<>();
		
		
		List<Menu> listByUser = mapper.getListByUser(user);
		//设置以及菜单
		for(Menu menu:listByUser){
			if("1".equals(menu.getLevel())){
				levelOne.add(menu);
				
				setOneLevelChild(listByUser,menu);
			}
		}
		return levelOne;
	}
	private void setOneLevelChild(List<Menu> listByUser, Menu menu) {
		List<Menu> leveltwo=new ArrayList<>();

		for(Menu mu:listByUser){
			if("2".equals(mu.getLevel())&&mu.getFatherid().equals(menu.getPowerid())){
				leveltwo.add(mu);
				
				setTwoLevelChild(listByUser,mu);
			}
		}
		menu.setListChild(leveltwo);
	}



	private void setTwoLevelChild(List<Menu> listByUser, Menu menu) {
		List<Menu> levelThree=new ArrayList<>();

		for(Menu muThree:listByUser){
			if("3".equals(muThree.getLevel())&&muThree.getFatherid().equals(menu.getPowerid())){
				levelThree.add(muThree);
				
			}
		}
		menu.setListChild(levelThree);
		
	}



	@Override
	public List<Menu> getUrlWithNoButton(Object object) {
		return mapper.getUrlWithNoButton(object);
	}
	@Override
	public List<Menu> getListByUserWithNoButton(User user) {
		return mapper.getListByUserWithNoButton(user);
	}

}
