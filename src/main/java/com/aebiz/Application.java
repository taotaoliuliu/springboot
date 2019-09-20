	package com.aebiz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.aebiz.conf.FilterForBaseframework;

/**
 * 默认启动类
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})

@EnableScheduling
@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = { FilterForBaseframework.class }))
//@MapperScan(basePackages = "com.aebiz.common.im.user.dao")

//@ImportResource(locations= {"classpath:applicationContext-tx.xml","classpath:applicationContext-elasticsearch.xml"})
@ImportResource(locations= {"classpath:platform-scheduler.xml","classpath:spring-im-config.xml"})
@EnableAsync



public class Application
{
	
	//注意disruptor 会阻塞test 测试  FilterForBaseframework过滤掉
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
   
  
    
   
}
