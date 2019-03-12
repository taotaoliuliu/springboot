package com.aebiz.schedule.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.schedule.dao.ScheduleJobLogDao;
import com.aebiz.schedule.entity.ScheduleJobLogEntity;
import com.aebiz.schedule.service.ScheduleJobLogService;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return scheduleJobLogDao.getById(jobId+"");
	}

	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
		
		
		
		return scheduleJobLogDao.getByCondition(null);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		
		//scheduleJobLogDao.
		//return scheduleJobLogDao.queryTotal(map);
		
		return 0;
	}

	@Override
	public void save(ScheduleJobLogEntity log) {
		scheduleJobLogDao.add(log);
	}

}
