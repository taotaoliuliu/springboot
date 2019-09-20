package com.aebiz.conf;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.aebiz.common.exception.MyExceptionHandler;
import com.aebiz.common.filter.XssFilter;
import com.aebiz.common.interceptor.MaliciousRequestInterceptor;
import com.aebiz.common.listener.AuthorityListener;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

//@Configuration
//@EnableWebMvc
//@ComponentScan("com.aebiz")
//@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
  /*  @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".html");
        viewResolver.setViewClass(JstlView.class);

        return viewResolver;
    }*/

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
        registry.addResourceHandler("/static/**").addResourceLocations( "/static/");

        super.addResourceHandlers(registry);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/converter").setViewName("/converter");
        super.addViewControllers(registry);
    }

    /**
     * 配置自定义的HttpMessageConverter 的Bean ，在Spring MVC 里注册HttpMessageConverter有两个方法：
     * 1、configureMessageConverters ：重载会覆盖掉Spring MVC 默认注册的多个HttpMessageConverter
     * 2、extendMessageConverters ：仅添加一个自定义的HttpMessageConverter ，不覆盖默认注册的HttpMessageConverter
     * 在这里重写extendMessageConverters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    	super.extendMessageConverters(converters);
    	converters.add(marshallingHttpMessageConverter());

    }
    @Bean
    public FastJsonHttpMessageConverter marshallingHttpMessageConverter(){
    	FastJsonHttpMessageConverter marshallingHttpMessageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_XML);
        mediaTypes.add(MediaType.APPLICATION_XML);
      //  XStreamMarshaller xStreamMarshaller=new XStreamMarshaller();
        marshallingHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
      //  marshallingHttpMessageConverter.setMarshaller(xStreamMarshaller);
       // marshallingHttpMessageConverter.setUnmarshaller(xStreamMarshaller);
        return marshallingHttpMessageConverter;
      }
    
    
    

    @Bean
    public MyMessageConverter converter() {
        return new MyMessageConverter();
    }
    
    
    
    
    
    /**
	 * 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new LoginRequestInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login")
			//	.excludePathPatterns("/admin/logout");
		registry.addInterceptor(new MaliciousRequestInterceptor()).addPathPatterns("/**");
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
	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(null);
		// filter只能配置"/*","/**"无法识别
		registration.addUrlPatterns("/admin/*");
		return registration;
	}
	
	
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
        exceptionResolvers.add(0, new MyExceptionHandler());
     ///   exceptionResolvers.add(new MyExceptionHandler());
    }

}
