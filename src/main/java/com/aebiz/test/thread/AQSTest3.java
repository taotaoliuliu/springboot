package com.aebiz.test.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;


public class AQSTest3 {

    private static Sync sync;

	static class Sync extends AbstractQueuedSynchronizer{
		

		 
        //future status
        private final int done = 1;
        private final int pending = 0;

		@Override
		protected boolean tryAcquire(int arg) {
			
			System.out.println(getState()+"tryAcquire11111");
				
			boolean flag=	getState() == done;
			
			System.out.println(getState()+"tryAcquire222222");

			return flag;
		}

		@Override
		protected boolean tryRelease(int arg) {
			System.out.println(getState());

			 if (getState() == pending) {
	                if (compareAndSetState(pending, done)) {
	                    return true;
	                }
	            }
	            return false;	
	            
		
		}
		
		 public boolean isDone() {
	            getState();
	            return getState() == done;
	        }

		
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		sync=new Sync();
		//sync.acquire(-1);
		
		
		
		/**
		 * 条件锁
		 */
		final ReentrantLock lock =new ReentrantLock();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("222222222******");
				
				lock.lock();
				System.out.println("333333333******");

				
			}
		}).start();
		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("222222222====");
				
				lock.lock();
				System.out.println("333333333====");

				
			}
		}).start();
		
		
		Thread.sleep(2000);
		
		//lock.unlock();

		System.out.println("main====");			
	
		System.out.println("bbbbbbb");

	}
	
}
