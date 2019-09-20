package com.aebiz.conf;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.aebiz.common.exception.MyExceptionHandler;
import com.aebiz.common.filter.XssFilter;
import com.aebiz.common.interceptor.MaliciousRequestInterceptor;
import com.aebiz.common.listener.AuthorityListener;

import java.util.EventListener;
import java.util.List;

import javax.annotation.Resource;

/**
 * 向mvc中添加自定义组件
 * Created by BlueT on 2017/3/9.
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
   
    /**
   	 * 拦截器
   	 */
   	@Override
   	public void addInterceptors(InterceptorRegistry registry) {
   		//registry.addInterceptor(new LoginRequestInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login")
   			//	.excludePathPatterns("/admin/logout");
   		//registry.addInterceptor(new MaliciousRequestInterceptor()).addPathPatterns("/**");
   		super.addInterceptors(registry);
   	}
   	
   	
	/**
	 * 监听器
	 * @return
	 */
	@Bean
	public ServletListenerRegistrationBean<EventListener> getDemoListener() {
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new AuthorityListener());
		return registrationBean;
	}

	/**
	 * 过滤器
	 * @return
	 */
	/*@Bean
	public FilterRegistrationBean filterRegistration() {
		//FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
		// filter只能配置"/*","/**"无法识别
		//registration.addUrlPatterns("/*");
		//return registration;
		return null;
	}*/
	
	
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
      //  exceptionResolvers.add(0, new MyExceptionHandler());
     ///   exceptionResolvers.add(new MyExceptionHandler());
    }
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }

}
