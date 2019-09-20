package com.aebiz.test.initchain;

public class InitProductOrder implements InitAllOrder {

	@Override
	public void doInit(OrderModel order, MyOrderChain chain) {
		//dosomething
		
		System.out.println("product");
		//继续执行下面的 
		
		chain.doFilter(order);
		
	}

}
