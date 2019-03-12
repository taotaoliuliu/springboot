package com.aebiz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aebiz.common.utils.RedisUtil;
import com.aebiz.entity.product.ProductMain;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestKafka2 {
	@Autowired
	private RedisUtil  redisUtil;
	
	

	
	@Test
	public void tessss() throws InterruptedException{
		
		ProductMain aa=new ProductMain();
		aa.setStore(233);
		aa.setName("没有11111");
		
		redisUtil.set("testlsllsl1111", aa.getStore());
		
		redisUtil.set("testlsllsl1112222", aa);
		
		ProductMain productMain = redisUtil.get("testlsllsl1112222", ProductMain.class);
		
		System.out.println(productMain.getName());
		

	}
	
	@Test
	public void tessss2() throws InterruptedException{
		
		ProductMain aa=new ProductMain();
		
		Integer integer = redisUtil.get("testlsl8888", Integer.class);
		
		System.out.println(integer);


	}
}
