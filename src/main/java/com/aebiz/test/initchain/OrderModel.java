package com.aebiz.test.initchain;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {

	
	
	public List orderMain =new ArrayList<>();
	
	
	public List orderDetail= new ArrayList<>();


	public List getOrderMain() {
		return orderMain;
	}


	public void setOrderMain(List orderMain) {
		this.orderMain = orderMain;
	}


	public List getOrderDetail() {
		return orderDetail;
	}


	public void setOrderDetail(List orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	
	
	
	
}
