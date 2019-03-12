package com.aebiz.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.aebiz.entity.Ad;
import com.alibaba.fastjson.JSON;

@Component
public class KafkaConsumer {

	 @KafkaListener(topics={"addAd33"})
	 public void receiveMessage(String message){
		 Ad parseObject = JSON.parseObject(message, Ad.class);
		 System.out.println(parseObject);
		 
	 }
}
