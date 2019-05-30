package com.aebiz.conf;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

	private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

	@Bean
	public Executor FrontQueryPoll() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(40);
		executor.setQueueCapacity(700);
		executor.setKeepAliveSeconds(60);
		executor.setThreadNamePrefix("Async-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(false);
		
		//设置此执行器在关闭时应阻止的最大秒数，以便在容器的其余部分继续关闭之前等待剩余任务完成其执行。
		executor.setAwaitTerminationSeconds(2);
			return executor;
	}

	@Bean
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new MyAsyncExceptionHandler();
	}

	/**
	 * 自定义异常处理类
	 * 
	 * @author hry
	 *
	 */
	class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

		@Override
		public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
			log.info("Exception message - " + throwable.getMessage());
			log.info("Method name - " + method.getName());
			for (Object param : obj) {
				log.info("Parameter value - " + param);
			}
		}

	}

}
