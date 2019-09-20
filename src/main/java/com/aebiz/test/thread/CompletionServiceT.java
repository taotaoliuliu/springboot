package com.aebiz.test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceT {

	public static int nThreads = 10;

	public static List<String> init() {

		List<String> list = new ArrayList<>();
		// 将10数fan转
		for (int i = 0; i < 10000000; i++) {
			list.add("cedddkkkxxx" + i);
		}

		return list;
	}

	public static void main(String[] args) {

			oneThread();
		
		//manyThread();
		
	}

	public static void manyThread() {
		
		long currentTimeMillis = System.currentTimeMillis();

		
		List<String> init = init();
		int size = init.size();

		ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);

		CompletionService<List<String>> completionService = new ExecutorCompletionService<List<String>>(threadPool);
		for (int j = 0; j < nThreads; j++) {

			final int index = j;
			completionService.submit(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {

					/*
					 * System.out.println("########"); //第三个线程睡眠等待 if (index ==
					 * 3) { java.lang.Thread.sleep(3000l); }
					 */
					List<String> list = new ArrayList<>();

					List<String> subList = init.subList(size / nThreads * index, size / nThreads * (index + 1));

					for (String str : subList) {
						String string = new StringBuilder(str).reverse().toString();
						list.add(string);
					}

					return list;
				}
			});
		}
		// threadPool.shutdown();

		List<String> listAll = new ArrayList<>();

		for (int i = 0; i < nThreads; i++) {
			try {
				// System.out.println("线程:"+completionService.take().get()+"
				// 任务执行结束:");
				List<String> list = completionService.take().get();

				listAll.addAll(list);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

		System.out.println(listAll.size());
		System.out.println(listAll.get(22));
		
		long currentTimeMillis2 = System.currentTimeMillis();
		
		System.out.println((currentTimeMillis2-currentTimeMillis)/1000);
	}

	public static void oneThread() {

		
		long currentTimeMillis = System.currentTimeMillis();
		List<String> init = init();

		List<String> listAll = new ArrayList<>();

		for (String str : init) {
			String string = new StringBuilder(str).reverse().toString();
			listAll.add(string);
		}

		System.out.println(listAll.size());
		System.out.println(listAll.get(22));
		
		long currentTimeMillis2 = System.currentTimeMillis();
		
		System.out.println((currentTimeMillis2-currentTimeMillis)/1000);


	}

}
