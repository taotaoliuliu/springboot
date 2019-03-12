package com.aebiz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aebiz.common.aop.AopTest;
import com.aebiz.common.aop.Servicelock;
import com.aebiz.controller.AdController;
import com.aebiz.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestUser {

	@Autowired
	private UserService userService;
	@Autowired
	private AopTest aopTest;
	@Autowired
	private AdController  adController;
	
	
	@Test
	public void getByParamKey() {
		String name="lsl";
		userService.testAop(name);
		
		String aa="33";
		testLock(aa);
		System.out.println("aaaaa");
	}
	
	@Servicelock
	public void testLock(String thisMy){
		System.out.println("this is my function ");
	}
	
	@Test
	public void testSome2222(){
		String name="lsl";
		aopTest.sayHi(name);
		
	}
	
	@Test
	public void tessss(){
		adController.sayHi();
	}
	
	
}
