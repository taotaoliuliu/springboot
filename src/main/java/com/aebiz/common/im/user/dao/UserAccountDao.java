package com.aebiz.common.im.user.dao;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.im.user.model.UserAccountEntity;

/**
 * 用户帐号
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */

@Mapper
public interface UserAccountDao extends BaseDao<UserAccountEntity> {
	public UserAccountEntity queryObjectByAccount(Map<String, Object> map);
}
