package com.aebiz.schedule.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.base.service.BaseService;
import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.common.utils.DateUtil;
import com.aebiz.mapper.SecKillProductMapper;
import com.aebiz.schedule.dao.ScheduleJobDao;
import com.aebiz.schedule.entity.ScheduleJobEntity;
import com.aebiz.schedule.service.ScheduleJobService;
import com.aebiz.schedule.utils.ScheduleUtils;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;
	@Autowired
	private ScheduleJobDao mapper;
	
	@Autowired
	public void setMapper(ScheduleJobDao mapper) {
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = mapper.getByCondition(null);
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
	
	
	
	
	
	@Override
	public void add(ScheduleJobEntity t) {
		t.setJobId(BaseModel.genUuid());
		
		t.setStatus(0);
		super.add(t);
        ScheduleUtils.createScheduleJob(scheduler, t);

		
	}



	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		//return schedulerJobDao.queryObject(jobId);
		
		return null;
	}

	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		//return schedulerJobDao.queryList(map);
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		//return schedulerJobDao.queryTotal(map);
		return 0;
	}

	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(DateUtil.getCurDateTime());
		scheduleJob.setStatus(0);
		mapper.add(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                
        mapper.update(scheduleJob);
    }

	@Override
	@Transactional
    public void deleteBatch(String[] jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	//schedulerJobDao.deleteBatch(jobIds);
	}

	@Override
    public int updateBatch(String[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return mapper.updateBatch(map);
    }
    
	@Override
	@Transactional
    public void run(String[] jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.run(scheduler, getById(jobId+""));
    	}
    }

	@Override
	@Transactional
    public void pause(String[] jobIds) {
        for(String jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	//updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

	@Override
	@Transactional
    public void resume(String[] jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	//updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }
    
}
