package com.rubicon.application.services;

import java.util.List;

import com.rubicon.application.dtos.OrderRequest;
import com.rubicon.application.dtos.OrderResponse;
import com.rubicon.application.entites.Orders;

public interface OrderService {

	public Orders createOrder(OrderRequest orderRequest);
	
	public String cancelRequest(String uuid);
	
	public OrderResponse viewRequest(String uuid) ;

	public List<Orders> viewAllRequests(String farmid);
}
