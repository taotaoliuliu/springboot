package com.aebiz.test.thread;

import java.util.concurrent.Semaphore;

public class TestSemaphore {

	
	public static void main(String[] args) {
		sema();
	}
	
	
	
	public static void sema(){
		
		// 创建信号量对象，并给予3个资源
		Semaphore semaphore = new Semaphore(3);

		// 开启10条线程
		for ( int i=0; i<10; i++ ) {
			
			 new Thread(new Runnable() {
				
				@Override
				public void run() {
					   try {
						// 获取资源，若此时资源被用光，则阻塞，直到有线程归还资源
						semaphore.acquire();
						// 任务代码
						Thread.sleep(4000);  //模拟执行的成勋代码
						System.out.println(Thread.currentThread().getName());
						
						// 释放资源
						semaphore.release();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}
	
}
