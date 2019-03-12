package com.aebiz.common.im.user.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.im.user.model.ImFriendUserData;
import com.aebiz.common.im.user.model.UserDepartmentEntity;

/**
 * 部门
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
@Mapper
public interface UserDepartmentDao extends BaseDao<UserDepartmentEntity> {
	
	public List<ImFriendUserData> queryGroupAndUser(); 
}
