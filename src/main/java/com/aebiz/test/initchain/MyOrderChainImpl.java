package com.aebiz.test.initchain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class MyOrderChainImpl implements MyOrderChain {
	
	
	List<InitAllOrder> list =new ArrayList<>();
	
	public void  initData(){
		InitAllOrder order =new InitCustomerOrder();
		
		list.add(order);
		
		InitAllOrder order2 =new InitProductOrder();
		
		list.add(order2);
	}
	
	private int index = 0;



	@Override
	public void doFilter(OrderModel OrderModel) {
		
		if(CollectionUtils.isEmpty(list)){
			initData();
		}
		
		
		/*OrderModel OrderModel =new OrderModel();
		for(InitAllOrder all:list){
			all.doInit(OrderModel, this);
		}*/
		
		if(list.size()==index){
			return ;
		}
		
		InitAllOrder initAllOrder = list.get(index);
		
		index++;
		initAllOrder.doInit(OrderModel, this);
		
	}

	
	
}
