package com.aebiz.queue.jvm;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.aebiz.entity.Ad;

public class Test {

	
	public static void main(String[] args) {
		
		
		int aa=0;

		ThreadPoolExecutor executor =new ThreadPoolExecutor(16, 16, 10l, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(10000));
		
		
		try {
		
		for(int i=0;i<100;i++){
			
			Runnable task = new Runnable() {
				@Override
				public void run() {
						
					String name = Thread.currentThread().getName();
					
					Ad ad =new Ad();
					ad.setName(name+"###########");
					
					try {
						SeckillQueue.getMailQueue().produce(ad);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			};
			executor.execute(task);
		}
		
			while(true){
				//进程内队列
				Ad ad = SeckillQueue.getMailQueue().consume();
				if(ad!=null){
					aa++;
				}
				System.out.println(aa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
