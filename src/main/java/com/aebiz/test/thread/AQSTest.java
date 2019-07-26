package com.aebiz.test.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;



/**
 * 条件锁
 * @author 55401
 *
 */
public class AQSTest {

	private static Sync sync;

	static class Sync extends AbstractQueuedSynchronizer {

		// future status
		private final int done = 1;
		private final int pending = 0;

		@Override
		protected boolean tryAcquire(int arg) {
			System.out.println("tryAcquire===" + arg);
			System.out.println(getState());

			return getState() == done;
		}

		@Override
		protected boolean tryRelease(int arg) {
			System.out.println("tryRelease===" + arg);

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
		sync = new Sync();

		System.out.println("##############");

		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("1111");

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sync.release(1); // 唤起主线程 让主线程继续执行
			}
		}).start();

		sync.acquire(-1); // 返回false 线程挂起 没有办法往下执行##########

		System.out.println(sync.isDone() + "33333333333");

		System.out.println("bbbbbbb");

	}

}
