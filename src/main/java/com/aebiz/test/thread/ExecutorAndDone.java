package com.aebiz.test.thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.springframework.util.DigestUtils;

public class ExecutorAndDone {

	
    private ReentrantLock lock = new ReentrantLock();
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String result;

		@Test
	    public void test() throws InterruptedException {
	        CountDownLatch latch = new CountDownLatch(1000);

	      //  latch.c
	        
	        for (int i = 0; i < 1000; i++) {
	        		
	        	threadPoolExecutor.execute(new Runnable() {
					
					@Override
					public void run() {
						
						try {
			                for (int i = 0; i < 10000; i++) {
			                    // dateTimeFormat.format(new Date());
			                }
			                System.out.println("现在的时间为：" + dateTimeFormat.format(new Date()) + "    " + Thread.currentThread().getName());
			                
			                

			            } catch (Exception e) {
			                e.printStackTrace();
			            } finally {
			                if (latch != null) {
			                    latch.countDown();
			                }
			            }

						
					}
				});
	        	
	        }
	        
	        latch.await();
	        System.out.println("执行完毕了吗！");
		 
		}
		
		
		
		@Test
		public void test2(){
	        for (int i = 0; i < 1000; i++) {

			new Thread(new Runnable() {
				
				@Override
				public void run() {
	                System.out.println("现在的时间为：" + dateTimeFormat.format(new Date()) + "    " + Thread.currentThread().getName());
	                
	                String md5Password = DigestUtils.md5DigestAsHex("8859625".getBytes());
			
				}
			}).start();
		}
		}
		
		/**
		 * 29680

		 */
		@Test
		public void test3(){
			
			
			  long endTime=System.currentTimeMillis();
			for (int i = 0; i < 100000000; i++) {
				String md5Password = DigestUtils.md5DigestAsHex("8859625".getBytes());

			}
			  long endTime2=System.currentTimeMillis();
			  
			  System.out.println(endTime2-endTime);

		}
		
		/**
		 * 18376
		 * @throws InterruptedException
		 */
		@Test
		public void test4() throws InterruptedException{
			
	        CountDownLatch latch = new CountDownLatch(100);

			  long endTime=System.currentTimeMillis();

			  for (int i = 0; i < 100; i++) {
	        		
		        	threadPoolExecutor.execute(new Runnable() {
						
						@Override
						public void run() {
							
							for (int i = 0; i < 1000000; i++) {
								String md5Password = DigestUtils.md5DigestAsHex("8859625".getBytes());

							}

		                    latch.countDown();

						}
					});
		        	
		        }
			  
		        latch.await();

		        long endTime2=System.currentTimeMillis();
			  
			  System.out.println(endTime2-endTime);
		}
		
		
		
		
		/**
		 * 17358
		 * @throws InterruptedException
		 */
		@Test
		public void test5() throws InterruptedException{
			
	        CountDownLatch latch = new CountDownLatch(10);

			  long endTime=System.currentTimeMillis();

			  for (int i = 0; i < 10; i++) {
	        		
		        	threadPoolExecutor.execute(new Runnable() {
						
						@Override
						public void run() {
							
							for (int i = 0; i < 10000000; i++) {
								String md5Password = DigestUtils.md5DigestAsHex("8859625".getBytes());

							}

		                    latch.countDown();

						}
					});
		        	
		        }
			  
		        latch.await();

		        long endTime2=System.currentTimeMillis();
			  
			  System.out.println(endTime2-endTime);
		}
		
		
		
		
		
		
		
		/**
		 * 17358
		 * @throws InterruptedException
		 */
		@Test
		public void test6() throws InterruptedException{
			
	        CountDownLatch latch = new CountDownLatch(50);

			  long endTime=System.currentTimeMillis();

			  for (int i = 0; i < 50; i++) {
	        		
		        	threadPoolExecutor.execute(new Runnable() {
						
						@Override
						public void run() {
							
							for (int i = 0; i < 10000000/5; i++) {
								String md5Password = DigestUtils.md5DigestAsHex("8859625".getBytes());

							}

		                    latch.countDown();

						}
					});
		        	
		        }
			  
		        latch.await();

		        long endTime2=System.currentTimeMillis();
			  
			  System.out.println(endTime2-endTime);
		}
		
		
		
}
