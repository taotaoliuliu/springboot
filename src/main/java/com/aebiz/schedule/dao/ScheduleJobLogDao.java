package com.aebiz.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.base.mapper.BaseMapper;
import com.aebiz.schedule.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {

}
