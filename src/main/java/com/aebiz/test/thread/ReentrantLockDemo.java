package com.aebiz.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ReentrantLockDemo {

	
	
	ReentrantLock lock =new ReentrantLock();
	
	Condition newCondition = lock.newCondition();
	
	static volatile int sum = 0;


	@Test
	public void test(){
		
		ThreadPoolExecutor exe =new ThreadPoolExecutor(16, 16, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

		for (int i = 0; i < 10; i++){

		exe.submit(new Runnable() {
			
			@Override
			public void run() {
				
				lock.lock();
				System.out.println("11111"+Thread.currentThread().getName());
				lock.unlock();
			}
		});
	}
		
		
		try {
			Thread.sleep(600*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int sum(){
		
		
		for (int i = 0; i < 100; i++)
			sum += i;
		
		return sum;
	}
	
	
	public static void main(String[] args) {
		int sum = sum();
		System.out.println(sum);
	}
	
}
