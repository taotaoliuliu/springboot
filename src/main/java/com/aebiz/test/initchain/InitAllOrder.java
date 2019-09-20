package com.aebiz.test.initchain;

public interface InitAllOrder {

	
	//chain.doFilter(request, response);
	
	
	
	public void doInit(OrderModel order,MyOrderChain chain);
}
