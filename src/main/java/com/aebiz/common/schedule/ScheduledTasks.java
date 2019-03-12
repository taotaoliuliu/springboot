package com.aebiz.common.schedule;

import java.text.MessageFormat;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	
	 private int cronCount = 1;
	/*cron：cron表达式，指定任务在特定时间执行;
	fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms;
	fixedDelayString：与fixedDelay含义一样，只是参数类型变为String;
	fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms;
	fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String;
	initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms;
	initialDelayString：与initialDelay的含义一样，只是将参数类型变为String;
	zone：时区，默认为当前时区，一般没有用到。*/
	
	
		//每过6s执行一次
	  	//@Scheduled(cron = "*/6 * * * * ?")
	    public void testCron() {
	      System.out.println(MessageFormat.format("core: 第{0}次执行方法", cronCount++));
	    }

}
