package com.aebiz.test.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**最好用 CyclicBarrier  线程组等待到状态 然后一起执行
 * @author 55401
 *
 */
public class TestBingFa {
	
	
	
	public static int count=10;

	
	public static void main(String[] args) {
		testStart();
		
	}
	
	
	//Executo
	
	public static void testStart(){
		
		CountDownLatch countDownLatch =new CountDownLatch(1);
		
		//countDownLatch.

		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(count);
		
		
		
		for(int i=0;i<count;i++){
			Runnable runable =new Runnable(
					) {
				
				@Override
				public void run() {
					
					try {
						countDownLatch.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName());
					
				}
			};
			
			newFixedThreadPool.execute(runable);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		countDownLatch.countDown();

		
	}
	
	
	
	
	
}
