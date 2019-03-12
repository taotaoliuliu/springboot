package com.aebiz.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.aebiz.common.seckill.handler.MessageHandler;

/**
 * 权限监听器
 * @author LSL
 */
//@WebListener(与ServletComponentScan注解一起使用;现在用另一种方式,交由WebConfig统一管理配置)
public class AuthorityListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(MessageHandler.class);

	@Autowired  
    ApplicationContext context;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		//加载自动注入Autowired
		//WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
		//OperationService operationService = context.getBean(OperationService.class);
		//operationService.initAuthority();
		System.out.println("初始化监听器......");
		log.info("aaaaaaaaaaaaaaaaa");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("销毁监听器.....");
	}
}
