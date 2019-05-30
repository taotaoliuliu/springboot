package com.aebiz.service.impl;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.aebiz.common.bean.Result;
import com.aebiz.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Override
	@Async
	public Future<Result> getMuchData() {
		
		Result result =new Result();
		
		
		result.setData("i love you ...");
		
		try {
			//无论这个地方休息几秒 都要等这个执行完   这个是多线程的 
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(Thread.currentThread().getName());
		
		return  new AsyncResult<Result>(result);
	}

	
	
}
