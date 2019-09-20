package com.aebiz.test.initchain;

public class Test {

	
	
	public static void main(String[] args) {
		
		
		MyOrderChain chain=new MyOrderChainImpl();
		OrderModel order =new OrderModel();
		chain.doFilter(order);
		
	}
}
