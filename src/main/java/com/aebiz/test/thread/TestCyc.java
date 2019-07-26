package com.aebiz.test.thread;

import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

public class TestCyc {

	public static void main(String[] args) {
		
		
		testCicyle();
		
	}

	
	
	public static  void testCicyle(){
		// 创建同步屏障对象，并制定需要等待的线程个数 和 打开屏障时需要执行的任务
		final CyclicBarrier barrier = new CyclicBarrier(3,new Runnable(){
		    public void run(){
		        //当所有线程准备完毕后触发此任务
		    	
		    	System.out.println("main=========");
		    }
		});

		// 启动三条线程
		for( int i=0; i<3; i++ ){
		    new Thread( new Runnable(){
		        public void run(){
		            // 等待，（每执行一次barrier.await，同步屏障数量-1，直到为0时，打开屏障）
		            try {
		            	Thread.sleep(4000);
		            	
		            	barrier.await();
		            	System.out.println("2222222");

						
						//Thread.sleep(2000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		            // 任务
		           // 任务代码……
		        }

			
		    } ).start();
		}
		
	}
}
