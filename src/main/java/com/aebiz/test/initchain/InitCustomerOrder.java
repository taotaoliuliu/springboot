package com.aebiz.test.initchain;

public class InitCustomerOrder implements InitAllOrder {

	@Override
	public void doInit(OrderModel order, MyOrderChain chain) {
		//dosomething
		
		System.out.println("cusomter#########");
		//继续执行下面的 
		
		chain.doFilter(order);
		
	}

}
