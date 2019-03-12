package com.aebiz.schedule.service;

import java.util.List;
import java.util.Map;

import com.aebiz.common.base.service.BaseService;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.schedule.entity.ScheduleJobEntity;

/**
 * 定时任务
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface ScheduleJobService extends BaseService<ScheduleJobEntity> {

    /**
     * 根据ID，查询定时任务
     */
    ScheduleJobEntity queryObject(Long jobId);

    /**
     * 查询定时任务列表
     */
    List<ScheduleJobEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务
     */
    void save(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(String[] jobIds);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(String[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(String[] jobIds);

    /**
     * 暂停运行
     */
    void pause(String[] jobIds);

    /**
     * 恢复运行
     */
    void resume(String[] jobIds);
}
