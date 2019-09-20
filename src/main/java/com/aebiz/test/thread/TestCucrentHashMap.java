package com.aebiz.test.thread;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 就是把Map分成了N个Segment 每个Segment有一把锁
 * @author 55401
 *
 */
public class TestCucrentHashMap {

//	private static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
	
	
	private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();


	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				map.put(3, 33);

			}
		}, "Thread1").start();
		;

		new Thread(new Runnable() {

			@Override
			public void run() {

				map.put(4, 44);
				

			}
		}, "Thread2").start();
		;

		new Thread(new Runnable() {

			@Override
			public void run() {

				map.put(7, 77);

			}
		}, "Thread3").start();
		;

		System.out.println(map);
	}
}
