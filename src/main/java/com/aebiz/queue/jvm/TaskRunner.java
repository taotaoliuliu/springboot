package com.aebiz.queue.jvm;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.aebiz.entity.Ad;
/**
 * 消费秒杀队列
 * 创建者 科帮网
 * 创建时间	2018年4月3日
 */
@Component
public class TaskRunner implements ApplicationRunner{
	
/*	@Autowired
	private ISeckillService seckillService;*/
	
	@Override
    public void run(ApplicationArguments var) throws Exception{
		while(true){
			//进程内队列
			Ad kill = SeckillQueue.getMailQueue().consume();
			if(kill!=null){
				//seckillService.startSeckil(kill.getSeckillId(), kill.getUserId());
			}
		}
    }
}