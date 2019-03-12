package com.aebiz.service;

import com.aebiz.common.base.service.BaseService;
import com.aebiz.entity.User;
import com.aebiz.entity.UserRole;

public interface UserService extends BaseService<User> {

	User getUserByNameAndPassword(String name_Login, String password);

	User getUserByLoginName(String loginname);

	void saveUserRole(UserRole ur);

	void testAop(String name);
}
