package com.rubicon.application.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rubicon.application.common.ApplicationProperties;
import com.rubicon.application.dtos.OrderRequest;
import com.rubicon.application.dtos.OrderResponse;
import com.rubicon.application.entites.Orders;
import com.rubicon.application.services.OrderService;
import com.rubicon.application.validators.ValidUUID;

@RestController
@Validated
public class OrderController {
	
	 Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ApplicationProperties app;
	
	@PostMapping("create/request")
	public ResponseEntity<?> createRequest(@RequestBody @Valid OrderRequest orderRequest) {
		Orders createdOrder = orderService.createOrder(orderRequest);
		logger.info("Request created for farm-id :"+orderRequest.getFarmid());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}
	
	@GetMapping("/cancel/request/{requestid}")
	public ResponseEntity<?> cancelRequest(@PathVariable @ValidUUID String requestid) {
		String cancelMessage = orderService.cancelRequest(requestid);
		logger.info("Order cancelled for request-id :"+requestid);
		return ResponseEntity.status(HttpStatus.OK).body(cancelMessage);
	}

	@GetMapping("/view/request/{requestid}")
	public ResponseEntity<?> viewRequest(@PathVariable @ValidUUID String requestid) {
		OrderResponse orderResponse = orderService.viewRequest(requestid);
		return ResponseEntity.status(HttpStatus.OK).body(orderResponse); 
	}
	
	@GetMapping("/view/allrequests/{farmid}")
	public ResponseEntity<?> viewAllRequests(@PathVariable 
		@Pattern(regexp = "^(?!00000000)[0-9]{8}$", message = "Please enter valid 8 digit numeric farm-id.") String farmid) {
		List<Orders> allRequestsofFarm = orderService.viewAllRequests(farmid);
		return ResponseEntity.status(HttpStatus.OK).body(allRequestsofFarm); 
	}
}
