package com.aebiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.aop.Servicelock;
import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.User;
import com.aebiz.entity.UserRole;
import com.aebiz.mapper.UserMapper;
import com.aebiz.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	UserMapper mapper;

	@Override
	public User getUserByNameAndPassword(String loginname, String password) {
		return mapper.getUserByNameAndPassword(loginname,password);
	}

	@Override
	public User getUserByLoginName(String loginname) {
		
		
		return mapper.getUserByLoginName(loginname);
	}

	@Override
	public void saveUserRole(UserRole ur) {
		 mapper.saveUserRole(ur);

	}

	@Override
	@Servicelock
	public void testAop(String name) {
		System.out.println(name);		
	}




}
