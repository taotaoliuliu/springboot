package com.aebiz.common.aop;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AopTestImpl implements AopTest {

	@Override
	@Servicelock
	public void sayHi(String name) {
		System.out.println(name);
	}

}
