package com.rubicon.application.common;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rubicon.application.entites.Orders;
import com.rubicon.application.repositories.OrderRepository;

@Configuration
@EnableScheduling
public class Schedule {
	
	Logger logger = LoggerFactory.getLogger(Schedule.class);
	
	@Autowired
	private OrderRepository orderRepository;

	@Scheduled(fixedDelayString = "PT${spring.application.interval}M")
	public void scheduleTask() {
		List<Orders> allOrders = orderRepository.findAll();
		LocalDateTime currentTime = LocalDateTime.now();
		allOrders.stream().forEach(order ->{
			LocalDateTime startDateTime = order.getStartDateTime();
			long l=Long.parseLong(order.getDuration());
			LocalDateTime endDateTime = order.getStartDateTime().plusHours(l);
			if((endDateTime.isBefore(currentTime)||endDateTime.isEqual(currentTime))
					&& order.getStatus().trim().equalsIgnoreCase(Constants.INPROGRESS_STATUS)) {
				order.setStatus(Constants.DELIVERED_STATUS);
				orderRepository.save(order);
				logger.info("Water delivery to farm: "+order.getFarmId()+" stopped.");
			}
			if((startDateTime.isBefore(currentTime)||startDateTime.isEqual(currentTime))
					&& order.getStatus().trim().equalsIgnoreCase(Constants.REQUESTED_STATUS)) {
				order.setStatus(Constants.INPROGRESS_STATUS);
				orderRepository.save(order);
				logger.info("Water delivery to farm: "+order.getFarmId()+" started.");
			}
			
		});
	}
}
