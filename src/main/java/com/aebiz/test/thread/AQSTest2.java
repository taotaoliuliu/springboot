package com.aebiz.test.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import org.junit.Test;


public class AQSTest2 {

	private static  Sync sync;

	 static class Sync extends AbstractQueuedSynchronizer{
		

	     private static final long serialVersionUID = 1L;

	        //future status
	        private final int done = 1;
	        private final int pending = 0;

	        
	     // 当状态为0的时候获取锁   false 挂起
	        protected boolean tryAcquire(int acquires) {
	        	System.out.println("tryAcquire====");
	        	System.out.println("getState()=="+getState());

	            return getState() == pending ? true : false;
	        }

	        protected boolean tryRelease(int releases) {
	        	
	        	System.out.println("tryRelease====");
	        	
	        	System.out.println("getState()=="+getState());

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
	 
	 
	 @Test
	 public void test () throws InterruptedException{
		 sync=new Sync();
		 sync.acquire(1); //如果成功 线程没有挂起    //
		
		
		//Thread.sleep(5000);
		//sync.release(1);
		System.out.println("main====rel------------");
		
		boolean done = sync.isDone();
		
		System.out.println(done);
			
/*			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("222222222******");
					Sync	sync=new Sync();
					sync.acquire(-1);

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sync.release(1);

					
					System.out.println("333333333******");

					
				}
			}).start();*/
			
			
			
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					Sync sync=new Sync();
					System.out.println("222222222====");
					
					sync.acquire(-1);
					
					
					
					
					sync.release(1);

					System.out.println("333333333====");

					
				}
			}).start();*/
			
			
	/*		Thread.sleep(2000);

			System.out.println("main====");

			sync2.acquire(-1);
			
			
			System.out.println("main====");


			Thread.sleep(4000);
			
			sync2.release(1);	
			
			
			System.out.println("main====rel------------");*/


		 
	 }
	  	
	
	
	public static void main(String[] args) throws InterruptedException {

	}
	
}
