package com.aebiz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 * <p>
 * testTask为spring bean的名称
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask2")
public class TestTask2 {
    private Logger logger = LoggerFactory.getLogger(getClass());


    public void getList(String params) {
    	
        System.out.println("22222222233333");

    }


    public void getList2() {
        logger.info("我是不带参数的test2方法，正在被执行");
        System.out.println("222222222");
    }
}
