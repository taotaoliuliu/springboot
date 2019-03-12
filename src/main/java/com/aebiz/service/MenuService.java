package com.aebiz.service;

import java.util.List;

import com.aebiz.common.base.service.BaseService;
import com.aebiz.entity.Menu;
import com.aebiz.entity.User;

public interface MenuService extends BaseService<Menu> {

	List<Menu> getListByUser(User user);

	List<Menu> getUrlWithNoButton(Object object);

	List<Menu> getListByUserWithNoButton(User user);


}
