	package com.aebiz;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aebiz.conf.FilterForBaseframework;

/**
 * 默认启动类
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = { FilterForBaseframework.class }))

//@ImportResource(locations= {"classpath:applicationContext-tx.xml","classpath:applicationContext-elasticsearch.xml"})
@ImportResource(locations= {"classpath:platform-scheduler.xml","classpath:spring-im-config.xml"})
public class Application
{
	
	//注意disruptor 会阻塞test 测试  FilterForBaseframework过滤掉
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
   
  
    
   
}
