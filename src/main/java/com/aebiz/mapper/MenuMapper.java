package com.aebiz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.base.mapper.BaseMapper;
import com.aebiz.entity.Menu;
import com.aebiz.entity.User;
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	List<Menu> getListByUser(User user);

	List<Menu> getUrlWithNoButton(Object object);

	List<Menu> getListByUserWithNoButton(User user);


}
