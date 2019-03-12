package com.aebiz.common.zookeeper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

	static ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 16, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(10000));

	
	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {

			Runnable task = new Runnable() {
				@Override
				public void run() {


					try {
						ZooKeeperSession.getInstance().acquireDistributedLock("myId222");
						System.out.println("执行代码。。。。。。。。。");
						ZooKeeperSession.getInstance().releaseDistributedLock("myId222");
						
						System.out.println("释放锁。。。。。。。。。");
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			executor.execute(task);
		}
		
	}
	
	@org.junit.Test
	public void aa() {

		for (int i = 0; i < 100; i++) {

			Runnable task = new Runnable() {
				@Override
				public void run() {


					try {
						ZooKeeperSession.getInstance().acquireDistributedLock("myId222");
						System.out.println("@@@@");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			executor.execute(task);
		}

	}

}
