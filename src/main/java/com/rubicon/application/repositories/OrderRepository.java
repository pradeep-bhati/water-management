package com.rubicon.application.repositories;

import org.springframework.stereotype.Repository;

import com.rubicon.application.entites.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface OrderRepository extends JpaRepository<Orders,UUID> {
	
	public List<Orders> findByfarmId(String farmId);

}
