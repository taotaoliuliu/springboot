package com.aebiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aebiz.common.base.mapper.BaseMapper;
import com.aebiz.entity.User;
import com.aebiz.entity.UserRole;
@Mapper
public interface UserMapper extends BaseMapper<User> {

	User getUserByNameAndPassword(@Param(value = "loginname") String loginname, @Param(value = "password")String password);

	User getUserByLoginName(String loginname);

	void saveUserRole(UserRole ur);

}
