package com.aebiz.common.seckill.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aebiz.common.seckill.message.Message;
import com.aebiz.common.seckill.message.MessageTrunk;
import com.aebiz.common.utils.RedisUtil;

import redis.clients.jedis.exceptions.JedisConnectionException;

public abstract class AbstarctMessageHandler<T> implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(MessageHandler.class);

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MessageTrunk messageTrunk;
	private String messageType;
	
	private Class<T>  clazz;
	
	private int retryTimes=3;

	private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

	
	public AbstarctMessageHandler(){
		
	}
	
	public AbstarctMessageHandler(String messageType, Class<T> clazz)
	{
		this.messageType = messageType;
		this.clazz = clazz;
	}
	
	
	@PostConstruct
	public void init(){
		threadPoolExecutor.execute(this);
	}
	
	
	
	
	@Override
	public void run() {
		
		boolean flag=true;
		while(flag){
			try {
				receiveMessage();

				Thread.sleep(2000);
			} catch (Exception e) {
				if(e instanceof JedisConnectionException){
					flag=false;
				}
				e.printStackTrace();
			}
		}
	}

	//处理消息
	public void receiveMessage (){
		
		System.out.println("aaaaaaaaaaaaaaxxxxxxxxxxx");
		
		log.info("receiveMessage");		
		Message message = redisUtil.blpop(messageType, Integer.MAX_VALUE, Message.class);
		
		
		log.info("message:"+message);		

		if(message!=null){
			
			//redis阻塞 没有必要用五个
			//for (int i = 0; i < 5; i++) {
			threadPoolExecutor.submit(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						handler((T)message.getContent());
					} catch (Exception e) {	
						e.printStackTrace();
						
						
						
						if (message.getFailTimes().intValue() >= retryTimes)
						{
							handlerFail((T) message.getContent());
						}
						else
						{
							message.setFailTimes(message.getFailTimes().intValue() + 1);
							// 再次put回消息总线，等待下次重试
							messageTrunk.put(message);
							
						}

					}

					
					
					
				}
			});
			//}
		}
		
		
	}



	public void handler(T t){
		
	}
	
	public void handlerFail(T t){
		
		
	}
	
}
