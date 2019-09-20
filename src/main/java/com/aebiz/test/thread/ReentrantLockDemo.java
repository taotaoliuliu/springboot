package com.aebiz.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ReentrantLockDemo {

	
	


	//Condition newCondition = lock.newCondition();
	
	//static volatile int sum = 0;
	
	public static void main(String[] args) {
		test();
	}
	

	
	public static void test(){
		
		
		ReentrantLock lock =new ReentrantLock();
		ReentrantLock lock2 =new ReentrantLock();
		//newCondition.
		
		ThreadPoolExecutor exe =new ThreadPoolExecutor(16, 16, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

		for (int i = 0; i < 10; i++){

		exe.submit(new Runnable() {
			
			@Override
			public void run() {
				
				lock.lock();
					String aa="11";
					runDemo(aa);
				lock.unlock();
			}
		});
	}
		
		String aa="22";
		
		lock.lock();
		runDemo(aa);
		lock.unlock();
		
		
		
		
		
		
		
		try {
			Thread.sleep(600*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected static void runDemo(String aa) {
		
		System.out.println(aa);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
}
