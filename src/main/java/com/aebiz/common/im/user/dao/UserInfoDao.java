package com.aebiz.common.im.user.dao;
import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.im.user.model.UserInfoEntity;

/**
 * 用户信息表
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
@Mapper
public interface UserInfoDao extends BaseDao<UserInfoEntity> {
	
}
