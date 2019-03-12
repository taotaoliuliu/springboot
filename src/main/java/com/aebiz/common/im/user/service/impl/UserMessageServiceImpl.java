package com.aebiz.common.im.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.im.user.dao.UserMessageDao;
import com.aebiz.common.im.user.model.UserMessageEntity;
import com.aebiz.common.im.user.service.UserMessageService;

@Service("userMessageServiceImpl")
public class UserMessageServiceImpl implements UserMessageService {
	@Autowired
	private UserMessageDao userMessageDao;
	
	@Override
	public UserMessageEntity queryObject(Long id){
		return userMessageDao.queryObject(id);
	}
	
	@Override
	public List<UserMessageEntity> queryList(Map<String, Object> map){
		return userMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(UserMessageEntity userMessage){
		userMessageDao.save(userMessage);
	}
	
	@Override
	public int update(UserMessageEntity userMessage){
		userMessageDao.update(userMessage);
		return 0;
	}
	
	@Override
	public int delete(Long id){
		userMessageDao.delete(id+"");
		return 0;
	}
	
	@Override
	public int deleteBatch(Long[] ids){
	//	return userMessageDao.deleteBatch(ids);
		
		return 0;
	}

	@Override
	public List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageList(map);
	}

	@Override
	public int getHistoryMessageCount(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageCount(map);
	}

	@Override
	public List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map) {
		 List<UserMessageEntity> result = userMessageDao.getOfflineMessageList(map);
		 //更新状态为已读状态
		 userMessageDao.updatemsgstate(map);
		 return result;
	}
	
}
