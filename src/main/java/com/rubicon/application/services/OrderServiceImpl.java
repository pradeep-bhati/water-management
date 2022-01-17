package com.rubicon.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubicon.application.common.ApplicationProperties;
import com.rubicon.application.common.Constants;
import com.rubicon.application.dtos.OrderRequest;
import com.rubicon.application.dtos.OrderResponse;
import com.rubicon.application.entites.Orders;
import com.rubicon.application.exceptions.OrderConflictException;
import com.rubicon.application.exceptions.ResourceNotFoundException;
import com.rubicon.application.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public Orders createOrder(OrderRequest orderRequest) {
		//check for future date starts
		LocalDateTime currentDateTime = LocalDateTime.now();
		long noOfHours = Long.parseLong(applicationProperties.getTimespan());
		LocalDateTime futureDateTime = currentDateTime.plusHours(noOfHours);
		boolean isFutureDate = false;
		isFutureDate = orderRequest.getDateTime().isAfter(futureDateTime);
		if(!isFutureDate) {
			throw new OrderConflictException("Request date and time should be " +noOfHours+ " hours ahead of current time.");
		}
		boolean timeConflict = false;
		Orders order = new Orders();
		order.setFarmId(orderRequest.getFarmid());
		order.setStartDateTime(orderRequest.getDateTime());
		order.setDuration(orderRequest.getDuration());
		order.setStatus("requested");
		List<Orders> allRequestsofFarm = orderRepository.findByfarmId(orderRequest.getFarmid());
		timeConflict = allRequestsofFarm.stream().anyMatch(request->{
			long l=Long.parseLong(request.getDuration());
			LocalDateTime endDateTime = request.getStartDateTime().plusHours(l);
			LocalDateTime startDateTime = request.getStartDateTime();
			boolean isAfterStartTime = false;
			boolean isBeforeEndTime = false;
			boolean isEqualtoStartTime = false;
			boolean isEqualtoEndTime = false;
			if((request.getStatus().trim().equalsIgnoreCase(Constants.INPROGRESS_STATUS))||
			  (request.getStatus().trim().equalsIgnoreCase(Constants.REQUESTED_STATUS))) {
			 isAfterStartTime = orderRequest.getDateTime().isAfter(startDateTime);
			 isBeforeEndTime = orderRequest.getDateTime().isBefore(endDateTime);
			 isEqualtoStartTime = orderRequest.getDateTime().isEqual(startDateTime);
			 isEqualtoEndTime = orderRequest.getDateTime().isEqual(endDateTime); }
			 return (isAfterStartTime && isBeforeEndTime)||(isEqualtoStartTime)||(isEqualtoEndTime);
		});
		if(timeConflict) {
			throw new OrderConflictException("A request for this farm-id already exist in same time span.");
		}
		return orderRepository.save(order);
	}

	@Override
	public String cancelRequest(String uuid) {
		
		Optional<Orders> requestedOrder = orderRepository.findById(UUID.fromString(uuid));
		String cancellationStatus=null;
		requestedOrder.orElseThrow(()-> new ResourceNotFoundException("Request-id :"+uuid+ " not present."));
		if(requestedOrder.isPresent()) { 
				if(requestedOrder.get().getStatus().trim().equalsIgnoreCase(Constants.CANCELLED_STATUS)){
					cancellationStatus = Constants.CANCELLATION_MSG_FOR_CANCELLED;}
				if(requestedOrder.get().getStatus().trim().equalsIgnoreCase(Constants.INPROGRESS_STATUS)){
					cancellationStatus = Constants.CANCELLATION_MSG_FOR_IN_PROGRESS;}
				if(requestedOrder.get().getStatus().trim().equalsIgnoreCase(Constants.DELIVERED_STATUS)){
					cancellationStatus = Constants.CANCELLATION_MSG_FOR_DELIVERED;}
				if(requestedOrder.get().getStatus().trim().equalsIgnoreCase(Constants.REQUESTED_STATUS)){
					Orders updatedOrder = requestedOrder.get();
					updatedOrder.setStatus(Constants.CANCELLED_STATUS);
					orderRepository.save(updatedOrder);
					cancellationStatus = Constants.CANCELLATION_SUCCESSFULL_MSG;}
		}
		return cancellationStatus;
	}
	
	@Override
	public OrderResponse viewRequest(String uuid) {
		
		Optional<Orders> requestedOrder = orderRepository.findById(UUID.fromString(uuid));
		requestedOrder.orElseThrow(()-> new ResourceNotFoundException("Request-id :"+uuid+ " not present."));
		 return requestedOrder.map(request -> {
			 OrderResponse orderResponse = new OrderResponse();
			 orderResponse.setRequestId(request.getRequestId());
			 orderResponse.setFarmId(request.getFarmId());
			 orderResponse.setStartDateTime(request.getStartDateTime());
			 orderResponse.setDuration(request.getDuration());
			 orderResponse.setStatus(request.getStatus());
			 return orderResponse;
		 }).orElseThrow(()-> new ResourceNotFoundException("Request-id :"+uuid+ " not present."));
		
	}
	
	@Override
	public List<Orders> viewAllRequests(String farmId) {
		List<Orders> allRequestsofFarm = orderRepository.findByfarmId(farmId);
		if(allRequestsofFarm.isEmpty()) {
			throw new ResourceNotFoundException("Farm Id :"+farmId+ " not present.");
		}
		return allRequestsofFarm;
	}

}
