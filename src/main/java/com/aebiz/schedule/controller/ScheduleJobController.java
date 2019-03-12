package com.aebiz.schedule.controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aebiz.common.base.cotroller.BaseController;
import com.aebiz.common.bean.Result;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.schedule.entity.ScheduleJobEntity;
import com.aebiz.schedule.service.ScheduleJobService;
import com.aebiz.service.SecKillProductService;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * 定时任务
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月28日 下午2:16:40
 */

@Controller
@RequestMapping("/sys/schedule")
public class ScheduleJobController extends BaseController<ScheduleJobEntity> {
    @Autowired
    private ScheduleJobService myService;
    
  
	
	private String templatePath="job";
	
	@PostConstruct
	public void init(){
		init(myService,templatePath);
	}
    
    

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        /*Query query = new Query(params);
        List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
        int total = scheduleJobService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);*/
        
        Result result =new Result();
        return result ;
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public Result info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = myService.queryObject(jobId);

          Result result =new Result();
        return result ;
    }

    /**
     * 保存定时任务
     */
    @RequestMapping("/save")
    public Result save(@RequestBody ScheduleJobEntity scheduleJob) {

    	myService.save(scheduleJob);

        Result result =new Result();
        return result ;
    }

    /**
     * 修改定时任务
     */
    @RequestMapping("/update")
    public Result update(@RequestBody ScheduleJobEntity scheduleJob) {

    	myService.update(scheduleJob);

        Result result =new Result();
        return result ;
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] jobIds) {
    	myService.deleteBatch(jobIds);

        Result result =new Result();
        return result; 
    }

    /**
     * 立即执行任务 @RequestBody  只能是post
     */
    @RequestMapping(value="/run")
    @ResponseBody
    public Result run(@RequestBody String[] jobIds) {
    	myService.run(jobIds);

        Result result =new Result();
        return result; 
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping("/pause")
    public Result
    pause(@RequestBody String[] jobIds) {
    	myService.pause(jobIds);

        Result result =new Result();
        return result; 
        
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
    public Result resume(@RequestBody String[] jobIds) {
    	myService.resume(jobIds);

        Result result =new Result();
        return result;
    }

}
