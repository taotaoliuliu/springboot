package com.aebiz.schedule.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.aebiz.common.base.mapper.BaseMapper;
import com.aebiz.schedule.entity.ScheduleJobEntity;

/**
 * 定时任务
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年12月1日 下午10:29:57
 */

@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);
}
