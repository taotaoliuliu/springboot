package com.aebiz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aebiz.entity.Ad;
import com.aebiz.kafka.KafkaSender;
import com.alibaba.fastjson.JSON;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestKafka {
	@Autowired
	private KafkaSender  send;
	
	

	
	@Test
	public void tessss() throws InterruptedException{
		for (int i=0;i<10;i++){
			Thread.sleep(2000);
			Ad ad =new Ad();
			ad.setContent("this is ad !"+i);
			ad.setUuid("lsl001");
			System.out.println(ad);
			send.sendChannelMess("access-log", JSON.toJSONString(ad));
		}
		
	}
	
	
}
